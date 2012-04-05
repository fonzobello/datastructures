package edu.ncsu.csc.mvta.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.derekandbritt.koko.configuration.DataDefinition;
import com.derekandbritt.koko.events.DataInstance;

import android.content.Context;
import android.location.LocationManager;
import android.view.View;
import edu.ncsu.csc.mvta.data.Answer;
import edu.ncsu.csc.mvta.data.Exam;
import edu.ncsu.csc.mvta.data.Question;
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

    private ExamService examService;
    private QuestionService questionService;
    private VTAgent vtAgent;
    
    private ProbabilisticLookup probabilisticLookup;
    
    public VirtualTA(ExamService examService, QuestionService questionService) {
        this.examService = examService;
        this.questionService = questionService;
        this.probabilisticLookup = new ProbabilisticLookup();
    }
    
    /**
     * Called by the exam service when it needs the next practice question to
     * present the user.
     * 
     * @return the question selected by the virtual TA
     */
    public Question nextQuestion() {
    	
    	/**
    	 * Category 1: Exam Data
    	 * 
    	 * 1) Look at the past three exams
    	 * 2) If past exams were lacking in one content area, then increase the probability of those questions
    	 * 3) If past exams were lacking at one grade level, then increase probability of asking question from that grade level
    	 */
    	
    	List<Exam> previousExams = examService.getPreviousExams(3);
    	
        for(Exam exam : previousExams) {

       		this.probabilisticLookup.increaseContentArea(examService.getWorstContentArea(exam), 0.10);
       		
       		this.probabilisticLookup.increaseGrade(examService.getWorstGrade(exam), 0.10);
        		
        }
    	
    	/**
    	 * Category 2: Environmental Data
    	 * 
    	 * 1) Determine the geographical location of the user.  Check this location against preset locations for study:
    	 *    If the user is at a predefined study location, increase the probability of hard questions
    	 *    If the user is traveling, assume they are bored and give them more challenging questions
    	 *    If they are not in any of the above situations, assume they are in a distracting environment and give them easy questions
    	 */
    	
        
        
    	/**
    	 * Category 3: Emotional Data
    	 * 
    	 * 1) Generate a new question based on categories 1 and 2
    	 * 2) Ask Koko if the user will become too frustrated, or too bored if we ask the question
    	 * 3) If the answer is yes, then generate a new question, if not then present the question to the user.
    	 * 
    	 * To avoid infinite loops, max out the number of times a new question is generated to 10
    	 */
    	
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
}
