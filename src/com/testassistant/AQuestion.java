package com.testassistant;
/*
 * AQuestion.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Class for application questions
 */

import java.util.ArrayList;

public class AQuestion extends Question {
    //not mc 
    AQuestion(String t,int n) {
        super(t,n);
    }
    
    //mc 
    AQuestion(String t,int n,ArrayList<String> c) {
        super(t,n,c);
    }
    
}