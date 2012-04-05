package edu.ncsu.csc.mvta.service;

import java.util.HashMap;

import edu.ncsu.csc.mvta.data.Question;
import edu.ncsu.csc.mvta.data.Question.ContentArea;
import edu.ncsu.csc.mvta.data.Question.Difficulty;
import edu.ncsu.csc.mvta.data.Question.Grade;

public class ProbabilisticLookup {

	private HashMap<Question.Difficulty, Double> _Difficulty;
	private HashMap<Question.Grade, Double> _Grade;
	private HashMap<Question.ContentArea, Double> _ContentArea;
	
	public ProbabilisticLookup() {
		
		_Difficulty = new HashMap<Question.Difficulty, Double>();
		_Grade = new HashMap<Question.Grade, Double>();
		_ContentArea = new HashMap<Question.ContentArea, Double>();
		
		Double difficulty =  1.0 / Question.Difficulty.values().length;
		
        for(Question.Difficulty d : Question.Difficulty.values()) {
        	_Difficulty.put(d, difficulty);
        }
        
		Double grade =  1.0 / Question.Grade.values().length;
		
        for(Question.Grade d : Question.Grade.values()) {
        	_Grade.put(d, grade);
        }
        
		Double contentArea =  1.0 / Question.ContentArea.values().length;
		
        for(Question.ContentArea d : Question.ContentArea.values()) {
        	_ContentArea.put(d, contentArea);
        }
		
	}
	
	public void increaseDifficulty(Question.Difficulty difficulty, double percentage) {
		
		if (_Difficulty.get(difficulty) + percentage > 1.0) {
			
			if (_Difficulty.get(difficulty) == 1.0) return;
			
			percentage = 1.0 - _Difficulty.get(difficulty);

		}
		
		Double currentPercentage = _Difficulty.get(difficulty);
		
		Double remainingPercentage = 1.0 - currentPercentage;
		
		Double remainingPercentageAfterIncrease = 1.0 - (currentPercentage + percentage);
		
        for(Question.Difficulty d : Question.Difficulty.values()) {
        	
        	if (d != difficulty) _Difficulty.put(d, remainingPercentageAfterIncrease * (currentPercentage / remainingPercentage));

        }

        _Difficulty.put(difficulty, (currentPercentage + percentage));
        
	}
	
	public void increaseGrade(Question.Grade grade, double percentage) {
		
		if (_Grade.get(grade) + percentage > 1.0) {
			
			if (_Grade.get(grade) == 1.0) return;
			
			percentage = 1.0 - _Grade.get(grade);

		}
		
		Double currentPercentage = _Grade.get(grade);
		
		Double remainingPercentage = 1.0 - currentPercentage;
		
		Double remainingPercentageAfterIncrease = 1.0 - (currentPercentage + percentage);
		
        for(Question.Grade g : Question.Grade.values()) {
        	
        	if (g != grade) _Grade.put(g, remainingPercentageAfterIncrease * (currentPercentage / remainingPercentage));

        }

        _Grade.put(grade, (currentPercentage + percentage));
        
	}
	
	public void increaseContentArea(Question.ContentArea contentArea, double percentage) {
		
		if (_ContentArea.get(contentArea) + percentage > 1.0) {
			
			if (_ContentArea.get(contentArea) == 1.0) return;
			
			percentage = 1.0 - _ContentArea.get(contentArea);

		}
		
		Double currentPercentage = _ContentArea.get(contentArea);
		
		Double remainingPercentage = 1.0 - currentPercentage;
		
		Double remainingPercentageAfterIncrease = 1.0 - (currentPercentage + percentage);
		
        for(Question.ContentArea g : Question.ContentArea.values()) {
        	
        	if (g != contentArea) _ContentArea.put(g, remainingPercentageAfterIncrease * (currentPercentage / remainingPercentage));

        }

        _ContentArea.put(contentArea, (currentPercentage + percentage));
        
	}
	
	public Question.Difficulty getDifficulty() {
		
		Double range = 0.0;
		
		Double value = Math.random();
		
        for(Question.Difficulty d : Question.Difficulty.values()) {
        	
        	range = range + _Difficulty.get(d);
        	
        	if (value <= range) return d;

        }
        
        return null;
        
	}
	
	public Question.Grade getGrade() {
		
		Double range = 0.0;
		
		Double value = Math.random();
		
        for(Question.Grade g : Question.Grade.values()) {
        	
        	range = range + _Grade.get(g);
        	
        	if (value <= range) return g;

        }
        
        return null;
        
	}
	
	public Question.ContentArea getContentArea() {
		
		Double range = 0.0;
		
		Double value = Math.random();
		
        for(Question.ContentArea a : Question.ContentArea.values()) {
        	
        	range = range + _ContentArea.get(a);
        	
        	if (value <= range) return a;

        }
        
        return null;
        
	}
	
	public String toString() {
		
		String toReturn = "";
		
        for(Question.Difficulty d : Question.Difficulty.values()) {
        	
        	toReturn = toReturn + d + ": " + _Difficulty.get(d) + "; ";

        }
		
        for(Question.Grade g : Question.Grade.values()) {
        	
        	toReturn = toReturn + g + ": " + _Grade.get(g) + "; ";

        }
		
        for(Question.ContentArea a : Question.ContentArea.values()) {
        	
        	toReturn = toReturn + a + ": " + _ContentArea.get(a) + "; ";

        }
        
        return toReturn;
	}
	
}
