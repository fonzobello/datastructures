package edu.ncsu.csc.mvta.service;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import com.derekandbritt.koko.client.json.KokoEndpoint;
import com.derekandbritt.koko.configuration.ConfigurationUtil;
import com.derekandbritt.koko.configuration.DataDefinition;
import com.derekandbritt.koko.configuration.DataType;
import com.derekandbritt.koko.emotion.EmotionType;

import android.location.Address;
import android.location.Location;
import android.util.Log;
import android.view.View;
import edu.ncsu.csc.mvta.data.Answer;
import edu.ncsu.csc.mvta.data.Exam;
import edu.ncsu.csc.mvta.data.Question;
import edu.ncsu.csc.mvta.data.Question.ContentArea;
import edu.ncsu.csc.mvta.data.Question.Grade;
import edu.ncsu.csc.mvta.jade.VTAgent;

/**
 * The lifecycle of the vTA is as follows:
 * 
 * 1) Instantiated by the exam service when the exam service is created
 * 2) Exam service requests a question from the vTA -- getNextQuestion()
 * 3) The question is presented to the user and the user answers
 * 4) The vTA is given the answer and asked if stats should be displayed -- displayStatistics(...)
 * 5) The vTA is given the chance to present feedback from the user -- setupConfigurableContent(...)
 * 6) The vTA is given the chance to collect feedback from the user -- receiveFeedback(...)
 * 7) Step 2-6 is repeated until all practice questions have been given and answered
 *
 */
public class VirtualTA {

	private static final String TAG = "VirtualTA";
	
    private ExamService examService;
    private QuestionService questionService;
    private VTAgent vtAgent;
    
    private ProbabilisticLookup probabilisticLookup;
    
    private List<Address> studyLocations;
    
    public VirtualTA(ExamService examService, QuestionService questionService) {
        this.examService = examService;
        this.questionService = questionService;
        this.probabilisticLookup = new ProbabilisticLookup();
        this.studyLocations = new ArrayList<Address>();
        
        addStudyLocation("D.H. Hill", 35.787500, -78.66950);
        addStudyLocation("Centennial EBII", 35.772000, -78.67400);
        
        Log.v(TAG, "Setting Default Difficulty: " + this.probabilisticLookup.printDificultyDistribution());
        Log.v(TAG, "Setting Default Grade: " + this.probabilisticLookup.printGradeDistribution());
        Log.v(TAG, "Setting Default Content: " + this.probabilisticLookup.printContentAreaDistribution());
        
    }
    
    /**
     * Called by the exam service when it needs the next practice question to
     * present the user.
     * 
     * @return the question selected by the virtual TA
     */
    public Question nextQuestion() {
        
        Question toReturn = questionService.randomQuestion(probabilisticLookup.getGrade(), probabilisticLookup.getDifficulty(), probabilisticLookup.getContentArea());
        
        return toReturn;
    }
    
    /**
     * Used to determine if the national statistics should be displayed to the
     * user when showing the user both their answer and the correct answer to
     * the question that was last returned by getNextQuestion().
     * 
     * @param answer to the last question posed by the virtual TA
     * @return true if the statistics are to be displayed to the user; false otherwise
     */
    public boolean displayStatistics(Answer answer) {
        return true;
    }
    
    /**
     * Called prior to displaying the correct answer to allow the vTA to setup an
     * UI widgets needed to gather user feedback
     * 
     * @param parentView the view that contains all content defined in vta_answer.xml
     */
    public void setupConfigurableContent(View parentView) {
        
    }
    
    /**
     * This method is used to gather any feedback left by the user before they 
     * proceed to the next question.
     * 
     * Called after setupConfigurableContent(...) and before getNextQuestion().
     * 
     * @param parentView the view that contains all content defined in vta_answer.xml
     */
    public void receiveFeedback(View parentView) {
 
    	
    }
    
	public void initialize() {

    	/**
    	 * Category 1: Exam Data
    	 * 
    	 * 1) Look at the past three exams
    	 * 2) If past exams were lacking in one content area, then increase the probability of those questions
    	 * 3) If past exams were lacking at one grade level, then increase probability of asking question from that grade level
    	 */
    	
    	List<Exam> previousExams = examService.getPreviousExams(3);
    	
    	if (previousExams.size() == 0) Log.v(TAG, "No previous exams to analyze");
    	
        for(Exam exam : previousExams) {

        	ContentArea contentArea = examService.getWorstContentArea(exam);
        	Grade grade = examService.getWorstGrade(exam);
        	
        	Log.v(TAG, "Based exam taken " + exam.completionTime + ", " + contentArea + " needs to be increased");
       		this.probabilisticLookup.increaseContentArea(contentArea, 0.10);
       		Log.v(TAG, "New Content Distribution: " + this.probabilisticLookup.printContentAreaDistribution());
       		
       		Log.v(TAG, "Based exam taken " + exam.completionTime + ", " + grade + " needs to be increased");
       		this.probabilisticLookup.increaseGrade(grade, 0.10);
       		Log.v(TAG, "New Grade Distribution: " + this.probabilisticLookup.printGradeDistribution());
        		
        }
        
    	/**
    	 * Category 2: Environmental Data
    	 * 
    	 * 1) Determine the geographical location of the user.  Check this location against preset locations for study:
    	 *    If the user is at a predefined study location, increase the probability of hard questions
    	 *    If not, assume they are in a distracting environment and give them easy questions
    	 */
        
        if (isWithinStudyLocation(this.examService.getExamLocation())) {
        	Log.v(TAG, "Based on study location, difficulty needs to be increased");
        	this.probabilisticLookup.increaseDifficulty(Question.Difficulty.HARD, 0.10);
        } else {
        	Log.v(TAG, "Based on study location, difficulty needs to be decreased");
        	this.probabilisticLookup.increaseDifficulty(Question.Difficulty.EASY, 0.10);
        }
    	Log.v(TAG, "New Difficulty Distribution: " + this.probabilisticLookup.printGradeDistribution());
        
    	/**
    	 * Category 3: Emotional Data
    	 * 
    	 * 1) Initialize the KokoEndpoint here
    	 */
    	
    	KokoEndpoint koko = createKokoEndpoint();
    	
	}
    
    public void addStudyLocation(String name, double latitude, double longitude) {
    	
    	Address address = new Address(null);
    	address.setFeatureName(name);
    	address.setLatitude(latitude);
    	address.setLongitude(longitude);
    	studyLocations.add(address);
    	
    }
    
    public boolean isWithinStudyLocation(Location location) {
    	
    	if (location == null) {
    		Log.v(TAG, "User Location: " + location);
    		return false;	
    	}
    	Log.v(TAG, "User Location: " + location.getLatitude() + ", " + location.getLongitude());
    	
    	for (Address address : studyLocations) {
    		if (Math.abs((address.getLatitude() - location.getLatitude())) < 0.001) {
    			if (Math.abs((address.getLongitude() - location.getLongitude())) < 0.001) {
    				Log.v(TAG, "User at Study Location: " + address.getFeatureName());
    				return true;
    			}
    		}
    	}
    	Log.v(TAG, "User Not At Predefined Study Location");
    	return false;
    }
    
    /**
     * This method registers your application with Koko by providing Koko
     * with all the necessary information.  The appID is directly tied to a specific
     * set of emotionTypes and dataDefinitions.  If you update either of those
     * sets then you will need to provide a new appID.
     * 
     * @return a KokoEndpoint configured for a specific application
     */
    private KokoEndpoint createKokoEndpoint() {
        
        HashSet<EmotionType> emotionTypes = new HashSet<EmotionType>();
        ArrayList<DataDefinition> dataDefinitions = new ArrayList<DataDefinition>();
        
        emotionTypes.add(EmotionType.LIKE);
        emotionTypes.add(EmotionType.DISLIKE);
        
        dataDefinitions.add(ConfigurationUtil.createEnumDataDefinition("weather", "cold,hot,foggy"));
        dataDefinitions.add(new DataDefinition("highScore", DataType.DOUBLE));
        dataDefinitions.add(new DataDefinition("attemptsToday", DataType.INT));
        dataDefinitions.add(new DataDefinition("timeToRespond", DataType.LONG));
        dataDefinitions.add(new DataDefinition("birthday", DataType.DATE));
        
        return new KokoEndpoint("vTA_jpvorenk_test", emotionTypes, dataDefinitions);

    }
    
}
