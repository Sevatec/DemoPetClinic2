package org.springframework.samples.petclinic.web;


/**
 * 
 * @author CMcCarthy
 * This class exists solely as a means of generating failures for 
 * CI Demo tools to catch and display.
 * 
 * Each method will cause a PMD error due to not being used anywhere in the code.
 */
public class DemoFailures {
	//Error - Build - Will cause the build to fail by compile error.	
	/*public int failBuild(){
		String oops = "";
		
		return oops;
	}*/
	
	//Error - FindBugs - Normal Priority
	/*public void finalize(){
		System.out.println("TEST!");
	}
	
	//Error - FindBugs - Low Priority
	public int modFail(){
		int numerator = 10;
		return numerator % 1;
	}
	
	//Error - PMD
	public void pmdUnusedVars(){
		String string1 = "";
		char char1 = 'a';
		double double1 = 1.25;
	}
	
	//Error - PMD
	public int pmdUnusedParameters(String string1, char char1){
		int twelve = 10+2;
		
		return twelve;
	}*/
}
