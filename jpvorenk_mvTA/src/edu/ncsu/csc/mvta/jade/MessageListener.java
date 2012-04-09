package edu.ncsu.csc.mvta.jade;


import edu.ncsu.csc.mvta.service.ExamService;
import jade.lang.acl.ACLMessage;
import android.util.Log;


public class MessageListener implements ACLMessageListener {

	private static final String TAG = "MessageListener";
	
	private ExamService examService;
	
	public MessageListener(ExamService examService) {
		
		this.examService = examService;
		
	}
	
	public void onMessageReceived(ACLMessage message) {
		
		Log.i(TAG," Message has received: " + message.getContent() + " from: " + message.getSender());
		
		/* START STUDENT CODE */
	    
	        // if requesting average scores send it
			examService.getVTAgetnt().sendMessage(examService.getLowestQuestionScore().toString(), "send", message.getSender());
		
			// if receiving average scores save it
			
			examService.setAmbiguousQuestion(Integer.parseInt(message.getContent()));
	    
	    /* END STUDENT CODE */
	}

}
	
