package edu.ncsu.csc.mvta.service;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import edu.ncsu.csc.mvta.data.Answer;
import edu.ncsu.csc.mvta.data.Exam;
import edu.ncsu.csc.mvta.data.Question;
import edu.ncsu.csc.mvta.data.Question.Difficulty;
import edu.ncsu.csc.mvta.data.Question.Grade;
import edu.ncsu.csc.mvta.jade.VTAgent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class ExamService extends Service {

    private static final String LOG_TAG = "ExamService";
    
    public static final int T0TAL_QUESTION_COUNT = 15;
    public static final int TA_QUESTION_COUNT = 10;
    
    private final IBinder mBinder = new LocalBinder();
    
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    
    private QuestionService questionService = new QuestionService();
    private VirtualTA virtualTA = new VirtualTA(this, questionService);
    private VTAgent vtAgetnt;
    
    private Exam activeExam;
    
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        public ExamService getService() {
            return ExamService.this;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @Override
    public void onCreate() { }

    public void initialize(Context context) {
        // setup DB
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        
        // if there is an active exam then fetch it
        final String query = "SELECT * FROM exam ORDER BY id DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        
        int isCompleteIndex = cursor.getColumnIndex("isComplete");
        
        if(cursor.moveToFirst() && cursor.getInt(isCompleteIndex) == 0) {
            int examIndex = cursor.getColumnIndex("exam");
            byte[] blob = cursor.getBlob(examIndex);
            
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                ObjectInputStream ois = new ObjectInputStream(bais);
                activeExam = (Exam)ois.readObject();                  
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to read the exam object from the database", e);
            }
        }
        
        cursor.close();

    }
    
    @Override
    public void onDestroy() {
        // if there is an active exam then store it
        if(activeExam != null)
            writeExam(activeExam, false);
        
        // tear down DB
        dbHelper.close();
    }
    
    private void computeExamScores(Exam exam) {
        
        for(int x = TA_QUESTION_COUNT; x < exam.answers.length; x++) {
            Answer answer = exam.answers[x];
            Question question = questionService.getQuestion(answer.questionId);
            exam.finalScore += (answer.answer.equals(question.answer)) ? 1 : 0;
            exam.averageScore += question.avgCorrect;
        }
        
        int examQuestionCount = exam.answers.length - TA_QUESTION_COUNT;
        exam.finalScore = exam.finalScore / examQuestionCount;
        exam.averageScore = exam.averageScore / examQuestionCount;        
    }
    
    private void writeExam(Exam exam, boolean isComplete) {
        
        if(isComplete) {
            computeExamScores(exam);
            exam.completionTime = new Date();
        }
        
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(exam);
            
            final String insertExam = "INSERT INTO exam(isComplete, exam) VALUES(?,?)";
            Object[] arguments = {isComplete ? 1 : 0, baos.toByteArray()};
            
            db.execSQL(insertExam, arguments);
            
            oos.close();
            baos.close();
            
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to write an exam to the database", e);
        }
    }
    
    public Question getNextQuestion() {
        
    	//Sending message to all other agents
    	//vtAgetnt.sendMessageToAllAgents("Hello All", "ACCEPT-PROPOSAL");
    	
        if(activeExam == null)
            activeExam = new Exam();
        
        if(activeExam.currentQuestion < TA_QUESTION_COUNT)
            return virtualTA.nextQuestion();
        else
            return generateTestQuestion();
    }
    
    public Exam getActiveExam() {
        return activeExam;
    }
        
    public VirtualTA getVirtualTA() {
        return virtualTA;
    }
    
    public void answerQuestion(Answer answer) {
        
        if(activeExam == null)
            throw new RuntimeException("You cannot answer a question without an exam");
        
        activeExam.addAnswer(answer);
        
        if(activeExam.isComplete()) {
            writeExam(activeExam, true);
            activeExam = null;
        }
    }
    
    public boolean isTestingQuestion() {
        
        if(activeExam == null)
            return false;
        
        if(activeExam.currentQuestion < TA_QUESTION_COUNT)
            return false;
        else
            return true;
    }
    
    public void quitExam() {
        activeExam = null;
    }
    
    
    private Question generateTestQuestion() {
        // randomly select the grade level to pull the question from
        Grade grade = (Math.random() < 0.5) ? Grade.GRADE_08 : Grade.GRADE_12;
        
        // randomly select the difficulty level
        Difficulty difficulty;
        double randomNumber = Math.random();
        if(randomNumber < 1.0/3.0)
            difficulty = Difficulty.EASY;
        else if(randomNumber < 2.0/3.0)
            difficulty = Difficulty.MEDIUM;
        else
            difficulty = Difficulty.HARD;
        
        // request the appropriate question type and make sure it is unused
        Question question = questionService.randomQuestion(grade, difficulty);
        if(isQuestionUsed(question.id))
            return generateTestQuestion();
        else
            return question; 
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, "examDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String createExam = "CREATE TABLE exam(" +
                                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                        "isComplete BOOLEAN NOT NULL, " +
                                        "exam BLOB NOT NULL)";
            db.execSQL(createExam);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
        
    }
    

/* The following methods are OK to be used by the virtualTA */    
    
    public List<Exam> getPreviousExams(int count) {
        
        ArrayList<Exam> exams = new ArrayList<Exam>();
        
        final String query = "SELECT * FROM exam WHERE isComplete == 1 " +
        		             "ORDER BY id DESC LIMIT " + count;
        Cursor cursor = db.rawQuery(query, null);
        
        int examIndex = cursor.getColumnIndex("exam");
        while(cursor.moveToNext()) {
            
            byte[] blob = cursor.getBlob(examIndex);
            
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                ObjectInputStream ois = new ObjectInputStream(bais);
                exams.add((Exam)ois.readObject());                  
            } catch (Exception e) {
                Log.e(LOG_TAG, "Unable to read the exam object from the database", e);
            }
        }
        
        cursor.close();
        return exams;
    }

    public boolean isActiveExam() {
        return activeExam != null;
    }
    
    public int getQuestionNumber() {
        
        if(activeExam == null)
            return 0;
        
        return activeExam.currentQuestion + 1;
    }
    
    public boolean isQuestionUsed(int questionId) {
        
        if(activeExam == null)
            return false;
        
        for(int x = 0; x < activeExam.currentQuestion; x++) {
            if(questionId == activeExam.answers[x].questionId)
                return true;
        }
        
        return false;
    }

	public void setVTAgetnt(VTAgent vtAgetnt) {
		this.vtAgetnt = vtAgetnt;
	}

	public VTAgent getVTAgetnt() {
		return vtAgetnt;
	}
	
    /* START STUDENT CODE */
    
    /**
     * Called when ActiveExam.onCreate() is called;
     * Used to gather test history when a new exam is started;
     */
	public void onActiveExam() {
		virtualTA.initialize();
	}
	
	public void setAmbiguousQuestion(int questionId) {
		
		this.questionService.getQuestion(questionId).setAmbiguous(true);
	}
	
	public Integer getLowestQuestionScore() {
		
		List<Double> totalAttempts = new ArrayList<Double>();
		List<Double> correctAttempts = new ArrayList<Double>();

		Double lowestQuestionScore = 1.0;
		Integer lowestQuestionID = -1;
		
		List<Exam> previousExams = getPreviousExams(3);
    	
    	if (previousExams.size() == 0) Log.v(LOG_TAG, "No previous exams to analyze");
    	
        for(Exam exam : previousExams) {
        	
        	for (Answer answer: exam.answers ) {
        		
        		if (answer.answer == questionService.getQuestion(answer.questionId).answer) {

        			if (correctAttempts.get(answer.questionId) == null) correctAttempts.add(answer.questionId, 1.0);
        			else correctAttempts.add(answer.questionId, correctAttempts.get(answer.questionId) + 1.0);
        			
        		}
        		
    			if (totalAttempts.get(answer.questionId) == null) totalAttempts.add(answer.questionId, 1.0);
    			else totalAttempts.add(answer.questionId, totalAttempts.get(answer.questionId) + 1.0);
        		
        	}
        	
        }
        
        for(Question question: questionService.questions) {
        	
        	Double numberCorrectAttempts = correctAttempts.get(question.id);
        	Double numberTotalAttempts = totalAttempts.get(question.id);
        	
        	if (numberCorrectAttempts == null) numberTotalAttempts = 0.0;
        	if (numberTotalAttempts != null) {
        	
        		if (lowestQuestionScore > (numberCorrectAttempts / numberTotalAttempts)) {
        			
        			lowestQuestionScore = (numberCorrectAttempts / numberTotalAttempts);
        			lowestQuestionID = question.id;
        		}
        		
        	}
        	
        }
        
        return lowestQuestionID;
        
	}
	
    public Question.ContentArea getWorstContentArea(Exam exam) {
    	
    	HashMap<Question.ContentArea,Double> count = new HashMap<Question.ContentArea,Double>();
    	
    	HashMap<Question.ContentArea,Double> correct = new HashMap<Question.ContentArea,Double>();
    	
    	for(Question.ContentArea contentArea : Question.ContentArea.values()) {
    		
    		count.put(contentArea, 0.0);
    		
    		correct.put(contentArea, 0.0);
    	}
    	
        for(Answer answer : exam.answers) {

    		Question question = questionService.getQuestion(answer.questionId);
    		
    		count.put(question.contentArea, count.get(question.contentArea)  + 1.0);
    		
    		if (question.answer.equals(answer.answer)) correct.put(question.contentArea, correct.get(question.contentArea) + 1.0);

        }
        
        double lowestScore = 0.0;
        
        Question.ContentArea weakestContentArea = Question.ContentArea.values()[0];
        
        for(Question.ContentArea contentArea : Question.ContentArea.values()) {
        	
        	if ((correct.get(contentArea) / count.get(contentArea)) < lowestScore) {
        		
        		lowestScore = (correct.get(contentArea) / count.get(contentArea));
        		
        		weakestContentArea = contentArea;
        		
        	}
        }
    	
    	return weakestContentArea;
    	
    }
    
    public Question.Grade getWorstGrade(Exam exam) {
    	
    	HashMap<Question.Grade,Double> count = new HashMap<Question.Grade,Double>();
    	
    	HashMap<Question.Grade,Double> correct = new HashMap<Question.Grade,Double>();
    	
    	for(Question.Grade grade : Question.Grade.values()) {
    		
    		count.put(grade, 0.0);
    		
    		correct.put(grade, 0.0);
    	}
    	
        for(Answer answer : exam.answers) {

    		Question question = questionService.getQuestion(answer.questionId);
    		
    		count.put(question.gradeLevel, count.get(question.gradeLevel) + 1.0);
    		
    		if (question.answer.equals(answer.answer)) correct.put(question.gradeLevel, count.get(question.gradeLevel) + 1.0);

        }
        
        double lowestScore = 0.0;
        
        Question.Grade weakestGrade = Question.Grade.values()[0];
        
        for(Question.Grade grade : Question.Grade.values()) {
        	
        	if ((correct.get(grade) / count.get(grade)) < lowestScore) {
        		
        		lowestScore = (correct.get(grade) / count.get(grade));
        		
        		weakestGrade = grade;
        		
        	}
        }
    	
    	return weakestGrade;
    	
    }
    
    public Location getExamLocation() {
    	
    	LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    	
    	Location lastKnownLocation = null;
    	
    	if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER ))
    	{
        	lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	}
    	
    	if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) && lastKnownLocation == null)
    	{
        	lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	}
    	
    	if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER ) && lastKnownLocation == null)
    	{
        	lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    	}

    	return lastKnownLocation;
    	
    }
    
    /* END STUDENT CODE */
    
}
