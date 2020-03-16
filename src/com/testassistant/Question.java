/* 
 * Question.java
 * January 2nd, 2020 
 * Nathan and Jason 
 * Version 1.0
 */ 
package com.testassistant;

import java.util.ArrayList;

public class Question implements Comparable<Question>{ 
    private String text; 
    private int numMarks; 
    private boolean isMCQuestion; 
    private ArrayList<String> choices;
      
//**********************************************************************************
    //constructor for non MC Question 
    public Question(String text, int numMarks){ 
        this.isMCQuestion = false; 
        this.text = text; 
        this.numMarks = numMarks; 
        
    }
    
    //constructor for MC Question 
    public Question(String text, int numMarks, ArrayList<String> choices){ 
        this.isMCQuestion = true; 
        this.text = text; 
        this.numMarks = numMarks; 
        this.choices = choices;
    }
      
//**********************************************************************************
    public int getNumMarks(){ 
        return this.numMarks; 
    }
    
    public void setNumMarks(int newMark) {
        this.numMarks = newMark;
    }
    
    public boolean isMultipleChoice(){ 
        return this.isMCQuestion; 
    }
    
    public String getMultipleChoiceLetter() {
        if(this.isMCQuestion) {
            return "M";
        }else{
            return " ";
        }
    }
    
    public String getText(){ 
        return this.text; 
    }
    
    public ArrayList<String> getChoices(){ 
        return this.choices; 
    }
    
    public int getChoiceIndex(String choiceText){
    	
        for(int choiceNum = 0; choiceNum < choices.size(); choiceNum++){ 
            if(choices.get(choiceNum).equals(choiceText)){ 
                return choiceNum; 
            }
        }
        return -1;
    }
    
    public void removeChoice(int index){ 
        this.choices.remove(index);
    }
    public String getChoice(int index){ 
        return this.choices.get(index);
    }
    public void setChoiceText(int index, String newText){ 
    	System.out.println(choices + "b4");
        this.choices.set(index,newText); 
        System.out.println(choices + "after");
    }
    
    public void setText(String newText) {
        this.text = newText;
    }
    
    public String returnMCChoices(){
        String mcText = "";
        if(this.isMCQuestion == true){ 
            for(int questionNumber = 0; questionNumber < this.choices.size(); questionNumber++) {
                mcText = mcText + (questionNumber+1) + ". " + choices.get(questionNumber) + "<br/>";
            }
        }
        System.out.println(mcText);
        return mcText; 
    }
    
    @Override
    public String toString() {
        if(this.isMCQuestion) {
            String returnText = "(";
            for(String s : choices) {
                returnText += s + ",";
            }
            returnText = returnText.substring(0,returnText.length()-1);
            returnText += ")";
            return "[" + this.text + "]"  + returnText;
        }

        return "[" + this.text + "]";
    }
    
    public String pdfFormat(int questionNum) {
    	if(this.isMCQuestion) {
    		final char A_ASCII_VALUE = 97;
    		String choiceString = "\n";
    		for(int choice = 0; choice < choices.size();choice++) {
    			choiceString += Character.toString(A_ASCII_VALUE + choice) + ") " + choices.get(choice) + "\n";
    		}
    		if(this instanceof KQuestion) { 
    			return questionNum + ". " + this.text + " (K:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof CQuestion) { 
    			return questionNum + ". " + this.text + " (C:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof AQuestion) { 
    			return questionNum + ". " + this.text + " (A:" + this.numMarks + ")" + choiceString;
    		}else if(this instanceof TQuestion) { 
    			return questionNum + ". " + this.text + " (T:" + this.numMarks + ")" + choiceString;
    		}else { 
    			return "";
    		}
    	}else {
    		if(this instanceof KQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (K:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof CQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (C:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof AQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (A:" + this.numMarks + ")" + "\n\n";
    		}else if(this instanceof TQuestion) { 
    			return "\n" + questionNum + ". " + this.text + " (T:" + this.numMarks + ")" + "\n\n";
    		}else { 
    			return "";
    		}
    	}
    }
    
    @Override 
    public int compareTo(Question other){ 
        if(this.numMarks > other.numMarks){ 
            return 1;
        }else if  (this.numMarks < other.numMarks){ 
            return -1; 
        }else{
            return 0; 
        }
    }
          
//**********************************************************************************
}