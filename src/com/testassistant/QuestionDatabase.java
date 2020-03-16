package com.testassistant;
/*
 * QuestionDatabase.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Class for application questions
 */


import java.io.File; 
import java.util.Scanner; 
import java.util.ArrayList;
import java.util.Collections;
//import java.util.Arrays;

public class QuestionDatabase { 
    private ArrayList<Question> questions; 
    private ArrayList<KQuestion> kQuestions; 
    private ArrayList<TQuestion> tQuestions; 
    private ArrayList<AQuestion> aQuestions; 
    private ArrayList<CQuestion> cQuestions; 
    private File source;
    private String databaseName; 
    
//    public QuestionDatabase() throws Exception{
//        private String path = System.getProperty("user.dir") + "Questions" + "QuestionsInput.txt";
//        private File newFile = new File(path);
//        this.questions = this.scanFile(newFile); 
//        this.kQuestions = new ArrayList<KQuestion>();  
//        this.tQuestions = new ArrayList<TQuestion>();  
//        this.aQuestions = new ArrayList<AQuestion>();  
//        this.cQuestions = new ArrayList<CQuestion>();  
//        sortQuestions();
//    }
    
    
    public QuestionDatabase(File source) throws Exception{ 
    	this.databaseName = source.getName(); 
        if(!source.getName().equals("QuestionInput.txt")) {
            this.source = new File(source + "/Unit Questions.txt");
        }else{
            this.source = new File(source + "");
        }
        this.questions = this.scanFile(this.source); 
        this.kQuestions = new ArrayList<KQuestion>();
        this.tQuestions = new ArrayList<TQuestion>();
        this.aQuestions = new ArrayList<AQuestion>();
        this.cQuestions = new ArrayList<CQuestion>();
        sortQuestions();
    }

    public File getSource() {
        return this.source;
    }
    
    public String getDatabaseName() { 
    	return this.databaseName; 
    }
    
    public ArrayList<Question> getQuestions(){ 
        return this.questions; 
    }
    
    public ArrayList<KQuestion> getKQuestions(){ 
        return this.kQuestions; 
    }
    
    public boolean editMCText(Question question, String newText, String choiceText){ 
        if(question instanceof KQuestion){ //make this for instance of T,C,A questions also
            for(int questionIndex = 0; questionIndex < kQuestions.size(); questionIndex++){ 
                if(kQuestions.get(questionIndex) == question){ 
                	System.out.println(choiceText);
                	System.out.println(kQuestions.get(questionIndex).getChoices() + "CHOICES");
                    int index = kQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    System.out.println(index + " THIS IS THE INDEX IN THE MC ARRAY which needs switching");
                    kQuestions.get(questionIndex).setChoiceText(index,newText);

                    return true;
                }
            }
        }else if(question instanceof TQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(tQuestions.get(questionIndex) == question){ 
                    int index = tQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    tQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }else if(question instanceof AQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(aQuestions.get(questionIndex) == question){ 
                    int index = aQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    aQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }else if(question instanceof CQuestion) {
            for(int questionIndex = 0; questionIndex < tQuestions.size(); questionIndex++){ 
                if(cQuestions.get(questionIndex) == question){ 
                    int index = cQuestions.get(questionIndex).getChoiceIndex(choiceText);
                    cQuestions.get(questionIndex).setChoiceText(index,newText);
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean setText(String newText,Question question) {
        int questionIndex = 0; 
        for(int index = 0; index < questions.size(); index++){ 
            if(questions.get(index) == question){ 
                questionIndex = index; 
            }
        }
        questions.get(questionIndex).setText(newText);
        
        if(question instanceof KQuestion) {
            int kQuestionIndex = 0; 
            for(int index = 0; index < kQuestions.size(); index++){ 
                if(kQuestions.get(index) == question){ 
                    kQuestionIndex = index; 
                }
            }
            kQuestions.get(kQuestionIndex).setText(newText);
            return true;
        }else if(question instanceof TQuestion) {
            int tQuestionIndex = 0; 
            for(int index = 0; index < tQuestions.size(); index++){ 
                if(tQuestions.get(index) == question){ 
                    tQuestionIndex = index; 
                }
            }
            tQuestions.get(tQuestionIndex).setText(newText);
            return true;
        }else if(question instanceof AQuestion) {
            int aQuestionIndex = 0; 
            for(int index = 0; index < aQuestions.size(); index++){ 
                if(aQuestions.get(index) == question){ 
                    aQuestionIndex = index; 
                }
            }
            aQuestions.get(aQuestionIndex).setText(newText);
            return true;
        }else if(question instanceof CQuestion) {
            int cQuestionIndex = 0; 
            for(int index = 0; index < cQuestions.size(); index++){ 
                if(cQuestions.get(index) == question){ 
                    cQuestionIndex = index; 
                }
            }
            cQuestions.get(cQuestionIndex).setText(newText);
            return true;
        }
        return false;
    }
    
    public boolean removeQuestion(Question question){ 
        if(question instanceof KQuestion) { 
            if(questions.remove(question) && kQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof TQuestion) {
            if(questions.remove(question) && tQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof AQuestion) {
            if(questions.remove(question) && aQuestions.remove(question)){ 
                return true;
            }
        }else if(question instanceof CQuestion) {
            if(questions.remove(question) && cQuestions.remove(question)){ 
                return true;
            }
            
        }
        return false;
        
        
    }
    
    
    public ArrayList<TQuestion> getTQuestions(){ 
        return this.tQuestions; 
    }
    
    public ArrayList<AQuestion> getAQuestions(){ 
        return this.aQuestions; 
    }
    
    public ArrayList<CQuestion> getCQuestions(){ 
        return this.cQuestions; 
    }
    
    public void sortQuestions(){ 
        for(int item = 0;item < this.questions.size();item++) {
            if(this.questions.get(item) instanceof KQuestion) {
                this.kQuestions.add(((KQuestion)this.questions.get(item)));
            }else if(this.questions.get(item) instanceof TQuestion) {
                this.tQuestions.add(((TQuestion)this.questions.get(item)));
            }else if(this.questions.get(item) instanceof AQuestion) {
                this.aQuestions.add(((AQuestion)this.questions.get(item)));
            }else if(this.questions.get(item) instanceof CQuestion) {
                this.cQuestions.add(((CQuestion)this.questions.get(item)));
            }
        }
        Collections.sort(this.kQuestions);
        Collections.sort(this.tQuestions);
        Collections.sort(this.aQuestions);
        Collections.sort(this.cQuestions);
    }
    
    public boolean addQuestion(Question question) {
        if(question instanceof KQuestion) { 
            if(questions.add(question) && kQuestions.add((KQuestion)question)){ 
                return true;
            }
        }else if(question instanceof TQuestion) {
            if(questions.add(question) && tQuestions.add((TQuestion)question)){ 
                return true;
            }
        }else if(question instanceof AQuestion) {
            if(questions.add(question) && aQuestions.add((AQuestion)question)){ 
                return true;
            }
        }else if(question instanceof CQuestion) {
            if(questions.add(question) && cQuestions.add((CQuestion)question)){ 
                return true;
            }
            
        }
        return false;
        
        
    }
    
    private ArrayList<Question> scanFile(File unitFile) throws Exception{
    	System.out.println(unitFile.getAbsolutePath());
        Scanner fileReader = new Scanner(unitFile); 
        ArrayList<Question> questionDatabase = new ArrayList<Question>();
        
        //constants 
        final int KNOWLEDGE_QUESTION = 1;
        final int THINKING_QUESTION = 2;
        final int APPLICATION_QUESTION = 3;
        final int COMMUNICATION_QUESTION = 4;
        
        while(fileReader.hasNext()){ 
            String currentLine = fileReader.nextLine();
            String questionParam = currentLine.substring(0,currentLine.indexOf("[")); 
            String questionText = ""; 
            int numMarks; 
            int indexForMark = 0;
            int questionToCreate = 0; 
            boolean multipleChoice = false;
            String[] choices = {}; 
            ArrayList<String> questionChoices = new ArrayList<String>();
            
            //searching for question type 
            if(questionParam.indexOf("K") != -1){ 
                questionToCreate = KNOWLEDGE_QUESTION; 
                indexForMark = questionParam.indexOf("K") + 1;
            }else if(questionParam.indexOf("T") != -1){ 
                questionToCreate = THINKING_QUESTION; 
                indexForMark = questionParam.indexOf("T") + 1;
            }else if(questionParam.indexOf("A") != -1){ 
                questionToCreate = APPLICATION_QUESTION; 
                indexForMark = questionParam.indexOf("A") +1;
            }else if(questionParam.indexOf("C") != -1){ 
                questionToCreate = COMMUNICATION_QUESTION; 
                indexForMark = questionParam.indexOf("C") +1;
            }
            
            //get num of marks
            numMarks = Character.getNumericValue(questionParam.charAt(indexForMark));
            
            //checking for multipe choice
            if(questionParam.indexOf("M") != -1){
                String multipleChoices = currentLine.substring(currentLine.indexOf("(")+1,currentLine.indexOf(")"));
                choices = multipleChoices.split(",");
                Collections.addAll(questionChoices,choices);
                multipleChoice = true; 
            }
            
            //getting question text
            if(currentLine.indexOf("]") != -1){ 
                questionText = currentLine.substring(currentLine.indexOf("[")+1, currentLine.indexOf("]")); 
            }
            while(currentLine.indexOf("]") == -1){ 
                questionText = questionText + currentLine.substring(currentLine.indexOf("[")+1,currentLine.indexOf("]")); 
            }
            
            //create the question 
            if(questionToCreate == KNOWLEDGE_QUESTION){ 
                if(multipleChoice == true){ 
                    KQuestion newQuestion = new KQuestion(questionText,numMarks,questionChoices);
                    questionDatabase.add(newQuestion);
                }else{
                    KQuestion newQuestion = new KQuestion(questionText,numMarks);
                    questionDatabase.add(newQuestion);
                }
            }
            
            if(questionToCreate == THINKING_QUESTION){ 
                if(multipleChoice == true){ 
                    TQuestion newQuestion = new TQuestion(questionText,numMarks,questionChoices);
                    questionDatabase.add(newQuestion);
                }else{
                    TQuestion newQuestion = new TQuestion(questionText,numMarks);
                    questionDatabase.add(newQuestion);
                }
            }
            
            if(questionToCreate == APPLICATION_QUESTION){ 
                if(multipleChoice == true){ 
                    AQuestion newQuestion = new AQuestion(questionText,numMarks,questionChoices);
                    questionDatabase.add(newQuestion);
                }else{
                    AQuestion newQuestion = new AQuestion(questionText,numMarks);
                    questionDatabase.add(newQuestion);
                }
            }
            
            if(questionToCreate == COMMUNICATION_QUESTION){ 
                if(multipleChoice == true){ 
                    CQuestion newQuestion = new CQuestion(questionText,numMarks,questionChoices);
                    questionDatabase.add(newQuestion);
                }else{
                	
                    CQuestion newQuestion = new CQuestion(questionText,numMarks);
                    System.out.println(newQuestion.getText());
                    questionDatabase.add(newQuestion);
                }
            }
            
            
        }
        fileReader.close();
        return questionDatabase; 
    }
    
    @Override
    public String toString() {
        return "KNOWLEDGE QUESTIONS " + this.kQuestions + "\nTHINKING QUESTIONS "+ this.tQuestions +
            "\nAPPLICATION QUESTIONS "+ this.aQuestions + "\nCOMMUNICATION QUESTIONS " + this.cQuestions;
    }
    
}
