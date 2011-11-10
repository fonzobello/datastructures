import java.io.BufferedReader;

import java.io.IOException;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

public class Main {
	
	private static final int APPLICATION_NUMBER = 0;
	
	private static final int TIER_ONE_SERVER_NUMBER = 1;
	
	private static final int TIER_TWO_SERVER_NUMBER = 2;
	
	private static final int TIER_THREE_SERVER_NUMBER = 3;

	public static void main(String[] args) {
		
		Dictionary<Integer, ApplicationNode> applications = new LinkedBinarySearchTree<Integer, ApplicationNode>();
		
		BufferedReader myBufferedReader;
		
		String command = null;
		
		try {
		
			myBufferedReader = new BufferedReader(new FileReader(new File(args[0])));
				
			while ((command = myBufferedReader.readLine()) != null) {
				
				command = command.replaceAll("\\s", "");
				
				command = command.replaceAll("Application_(\\d*);Serverno(\\d*)_in_tier_1;Serverno(\\d*)_in_tier_2;Serverno(\\d*)(.*)", "$1;$2;$3;$4");
				
				String[] parts = command.split(";");
				
				ApplicationNode application = null;
				
				Entry<Integer, ApplicationNode> entry = applications.find(Integer.parseInt(parts[APPLICATION_NUMBER]));
				
				if (entry == null) {
					
					application = new ApplicationNode();
					
					applications.insert(Integer.parseInt(parts[APPLICATION_NUMBER]), application);
				
				} else application = entry.getValue();
				
				application.insert(Integer.parseInt(parts[TIER_ONE_SERVER_NUMBER]), 1);
				
				application.insert(Integer.parseInt(parts[TIER_TWO_SERVER_NUMBER]), 2);
				
				application.insert(Integer.parseInt(parts[TIER_THREE_SERVER_NUMBER]), 3);
				
			}
		
			ApplicationNode application = null;
			
			Entry<Integer, ApplicationNode> entry = applications.find(Integer.parseInt(args[1].replaceAll("Application_", "")));

			if (entry == null) {
				
				System.out.println("Application does not exists.");
			
			} else application = entry.getValue();
			
			if (application != null) System.out.println(application);
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();

		}
		
	}
	
}
