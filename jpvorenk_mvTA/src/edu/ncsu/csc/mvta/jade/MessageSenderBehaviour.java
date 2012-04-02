package edu.ncsu.csc.mvta.jade;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;



public class MessageSenderBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	private ACLMessage message;
	
	public MessageSenderBehaviour(ACLMessage message) {
		this.message = message;
	}
	
	public void action() {
		myAgent.send(message);
	}

}
