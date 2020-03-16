package com.testassistant;
/* 
 * KQuestion.java
 * January 2nd, 2020 
 * Nathan and Jason 
 * Version 1.0
 */ 

import java.util.ArrayList;

public class KQuestion extends Question{ 
    public KQuestion(String text, int numMarks){ 
        super(text,numMarks); 
    }
    
    public KQuestion(String text, int numMarks, ArrayList<String> choices){ 
        super(text,numMarks,choices); 
    }
    
}