package com.testassistant;
/* 
 * DatabaseManager.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Generator main class 
 */
import javax.swing.JOptionPane;
import java.lang.Math;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.File;
import javax.swing.JFileChooser; 
import java.util.Scanner;


public class DatabaseManager{ 
    QuestionDatabase qb; 
    
    //read a file and create a database
    public DatabaseManager() throws Exception { 
        this.createDatabase();
    }
    
    //editing a database
    public DatabaseManager(QuestionDatabase qb) throws Exception{ 
        this.qb = qb;
    }
    
    public void appendToFile(QuestionDatabase qb) throws Exception {
        ArrayList<String> contents = new ArrayList<String>();
        //copying contents of current file and combining with QuestionInput.txt
        File sourceFile = qb.getSource();
        String path = sourceFile.getPath();
        System.out.println(path);
        File fileToEdit = new File(path);
        ArrayList<String> contentsOfSource = this.getContentsOfFile(fileToEdit);
        String questionInputPath = System.getProperty("user.dir");
        File questionsInput = new File(questionInputPath.substring(0,questionInputPath.lastIndexOf("\\")) + "/Questions/QuestionInput.txt");
        ArrayList<String> contentsOfInput = this.getContentsOfFile(questionsInput);
        contents.addAll(contentsOfSource);
        contents.addAll(contentsOfInput);
        //write contents to unit questions file
        PrintWriter fileWriter = new PrintWriter(fileToEdit);
        int i = 0;
        System.out.println(contents);
        while(!contents.isEmpty() && i<contents.size()) {
            fileWriter.println(contents.remove(i));
            i++;
        }
        fileWriter.close();
        QuestionDatabase newQb = new QuestionDatabase(new File(path.substring(0,path.lastIndexOf("\\"))));
        outputFiles(newQb);
    }
    
    public QuestionDatabase getDatabase(){ 
        return this.qb; 
    }
    
    public boolean addQuestion(Question question) {
        return qb.addQuestion(question);
    }
    
    public boolean removeQuestion(Question question){ 
        return qb.removeQuestion(question);
    }
    
    public boolean editText(String text,Question question) {
        return this.qb.setText(text,question);
    }
    
    public void saveFiles() throws Exception {
        ArrayList<String> contentsOfFile = getContentsOfFile(this.qb.getSource());
        System.out.println(this.qb.getSource().getName());
        this.outputFiles(this.qb);
        System.exit(0);
    }
    
    public void saveFiles(String databaseName) throws Exception {
        ArrayList<String> contentsOfFile = getContentsOfFile(this.qb.getSource());
        System.out.println(this.qb.getSource().getName());
        this.outputFiles(databaseName);
    	JOptionPane.showMessageDialog(null, "Changes saved to original database: " + this.getDatabase().getDatabaseName());
        System.exit(0);
    }
    

    
    public void createDatabase() throws Exception {
        //creating question database
        String path = System.getProperty("user.dir");
        path = path.substring(0,path.lastIndexOf("\\")) + "/Questions/QuestionInput.txt";
        File inputFile = new File(path);
        QuestionDatabase qb = new QuestionDatabase(inputFile);
        //creating new directory with questions from question database 
        this.outputFiles(qb);
        //removing content of QuestionInput.txt
        PrintWriter clearFile = new PrintWriter(inputFile);
        clearFile.print("");
        clearFile.close();
    }
    
     public void outputFiles(QuestionDatabase qb) throws Exception{ 
        String databaseName = JOptionPane.showInputDialog(null,"Where would you like to save this database to?"); 
         
        //creating new directory with database of questions 
        String path = System.getProperty("user.dir");  
        System.out.println(path); 
        File questionInput = new File(path.substring(0,path.lastIndexOf("/")) + "/Questions/QuestionInput.txt"); 
        File databaseFolder = new File(path +"/Questions/Databases"); 
        databaseFolder.mkdir(); 
        path = path.substring(0,path.lastIndexOf("/")) + "/Questions/Databases/"; 
        ArrayList<String> contentsOfQuestionInput = this.getContentsOfFile(questionInput); 
 
        File newFile = new File(path + databaseName); 
        newFile.mkdirs(); 
        System.out.println(path + databaseName); 
        //create files  
        PrintWriter knowledgeWriter = new PrintWriter(path + databaseName + "/Knowledge Questions.txt"); 
        PrintWriter thinkingWriter = new PrintWriter(path + databaseName + "/Thinking Questions.txt"); 
        PrintWriter appWriter = new PrintWriter(path + databaseName + "/Application Questions.txt"); 
        PrintWriter commWriter = new PrintWriter(path + databaseName + "/Communication Questions.txt"); 
        File copy = new File(path + databaseName + "/Unit Questions.txt"); 
        PrintWriter allQuestionsWriter = new PrintWriter(copy); 
 
         
        for(int i = 0;i < qb.getKQuestions().size();i++) { 
            knowledgeWriter.println((i+1)+". "+qb.getKQuestions().get(i)); 
        } 
         
        for(int i = 0;i < qb.getTQuestions().size();i++) { 
            thinkingWriter.println((i+1)+". "+ qb.getTQuestions().get(i)); 
        } 
         
        for(int i = 0;i < qb.getAQuestions().size();i++) { 
            appWriter.println((i+1)+". "+ qb.getAQuestions().get(i)); 
        } 
         
        for(int i = 0;i < qb.getCQuestions().size();i++) { 
            commWriter.println((i+1)+". "+ qb.getCQuestions().get(i)); 
        } 
         
        for(int i = 0;i < qb.getQuestions().size();i++) {
            Question currentQuestion = qb.getQuestions().get(i);
            if(currentQuestion instanceof KQuestion) {
                allQuestionsWriter.println("K" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof TQuestion) {
                allQuestionsWriter.println("T" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof CQuestion) {
                allQuestionsWriter.println("C" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }else if(currentQuestion instanceof AQuestion) {
                allQuestionsWriter.println("A" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
            }
        }
         
        knowledgeWriter.close(); 
        thinkingWriter.close(); 
        appWriter.close(); 
        commWriter.close(); 
        allQuestionsWriter.close(); 
         
    } 
     
     public void outputFiles(String databaseName) throws Exception{  
         //creating new directory with database of questions 
         String path = System.getProperty("user.dir");  
         System.out.println(path); 
         File questionInput = new File(path.substring(0,path.lastIndexOf("/")) + "/Questions/QuestionInput.txt"); 
         File databaseFolder = new File(path +"/Questions/Databases"); 
         databaseFolder.mkdir(); 
         path = path.substring(0,path.lastIndexOf("/")) + "/Questions/Databases/"; 
         ArrayList<String> contentsOfQuestionInput = this.getContentsOfFile(questionInput); 
  
         File newFile = new File(path + databaseName); 
         newFile.mkdirs(); 
         System.out.println(path + databaseName); 
         //create files  
         PrintWriter knowledgeWriter = new PrintWriter(path + databaseName + "/Knowledge Questions.txt"); 
         PrintWriter thinkingWriter = new PrintWriter(path + databaseName + "/Thinking Questions.txt"); 
         PrintWriter appWriter = new PrintWriter(path + databaseName + "/Application Questions.txt"); 
         PrintWriter commWriter = new PrintWriter(path + databaseName + "/Communication Questions.txt"); 
         File copy = new File(path + databaseName + "/Unit Questions.txt"); 
         PrintWriter allQuestionsWriter = new PrintWriter(copy); 
  
          
         for(int i = 0;i < qb.getKQuestions().size();i++) { 
             knowledgeWriter.println((i+1)+". "+qb.getKQuestions().get(i)); 
         } 
          
         for(int i = 0;i < qb.getTQuestions().size();i++) { 
             thinkingWriter.println((i+1)+". "+ qb.getTQuestions().get(i)); 
         } 
          
         for(int i = 0;i < qb.getAQuestions().size();i++) { 
             appWriter.println((i+1)+". "+ qb.getAQuestions().get(i)); 
         } 
          
         for(int i = 0;i < qb.getCQuestions().size();i++) { 
             commWriter.println((i+1)+". "+ qb.getCQuestions().get(i)); 
         } 
          
         for(int i = 0;i < qb.getQuestions().size();i++) {
             Question currentQuestion = qb.getQuestions().get(i);
             if(currentQuestion instanceof KQuestion) {
                 allQuestionsWriter.println("K" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof TQuestion) {
                 allQuestionsWriter.println("T" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof CQuestion) {
                 allQuestionsWriter.println("C" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }else if(currentQuestion instanceof AQuestion) {
                 allQuestionsWriter.println("A" + currentQuestion.getNumMarks() + currentQuestion.getMultipleChoiceLetter() + currentQuestion.toString());
             }
         }
          
         knowledgeWriter.close(); 
         thinkingWriter.close(); 
         appWriter.close(); 
         commWriter.close(); 
         allQuestionsWriter.close(); 
          
     } 
     
     public boolean editMultipleChoiceText(Question question, String newText, String choiceText){ 
         return this.qb.editMCText(question,newText,choiceText);
     }
     
    private ArrayList<String> getContentsOfFile(File fileToCopy) throws Exception { 
        Scanner fileReader = new Scanner(fileToCopy); 
        ArrayList<String> contents = new ArrayList<String>(); 
        while(fileReader.hasNext()) { 
            contents.add(fileReader.nextLine()); 
        } 
        fileReader.close(); 
        return contents; 
    }
    //    public Test createTest(String testName){ 
//        
//    }
//        int numKnowledge = 0;
//        int numThinking = 0;
//        int numApp = 0;
//        int numComm = 0;
//        
//        ArrayList<KQuestion> testKQuestions = new ArrayList<KQuestion>();
//        ArrayList<AQuestion> testAQuestions= new ArrayList<AQuestion>();
//        ArrayList<CQuestion> testCQuestions= new ArrayList<CQuestion>();
//        ArrayList<TQuestion> testTQuestions= new ArrayList<TQuestion>();
//       
//        
//        do{
//            numKnowledge = Integer.parseInt(JOptionPane.showInputDialog(null,"How many knowledge questions would you like?"));
//        }while(numKnowledge > db.getKQuestions().size());
//        
//        do{ 
//            numThinking = Integer.parseInt(JOptionPane.showInputDialog(null,"How many thinking questions would you like?"));
//        }while(numThinking > db.getTQuestions().size());
//        
//        do{ 
//            numApp = Integer.parseInt(JOptionPane.showInputDialog(null,"How many application questions would you like?"));
//        } while(numApp > db.getAQuestions().size());
//        
//        do{ 
//            numComm = Integer.parseInt(JOptionPane.showInputDialog(null,"How many communication questions would you like?"));
//        }while(numComm > db.getCQuestions().size());
//        if(numKnowledge > 0) {
//            ArrayList<KQuestion> copyKQuestions = db.getKQuestions(); 
//            testKQuestions = new ArrayList<KQuestion>();
//            for(int question = 0; question < numKnowledge; question++){ 
//                System.out.println(copyKQuestions.size());
//                testKQuestions.add(copyKQuestions.remove( (int)(Math.random() * (copyKQuestions.size()-1))));
//            }
//        }
//        if(numThinking > 0) {
//            ArrayList<TQuestion> copyTQuestions = db.getTQuestions(); 
//            testTQuestions = new ArrayList<TQuestion>();
//            for(int question = 0; question < numThinking; question++){ 
//                testTQuestions.add(copyTQuestions.remove( (int)(Math.random() * (copyTQuestions.size()-1))));
//            }
//        }
//        if(numApp > 0) {
//            ArrayList<AQuestion> copyAQuestions = db.getAQuestions(); 
//            testAQuestions = new ArrayList<AQuestion>();
//            for(int question = 0; question < numApp; question++){ 
//                testAQuestions.add(copyAQuestions.remove( (int)(Math.random() * (copyAQuestions.size()-1))));
//            }
//        }
//        if(numComm > 0) {
//            ArrayList<CQuestion> copyCQuestions = db.getCQuestions(); 
//            testCQuestions = new ArrayList<CQuestion>();
//            for(int question = 0; question < numComm; question++){ 
//                testCQuestions.add(copyCQuestions.remove( (int)(Math.random() * (copyCQuestions.size()-1))));
//            }
//        }
//        outputFiles(testKQuestions,testTQuestions,testAQuestions,testCQuestions);
//        String testName = JOptionPane.showInputDialog(null,"What will be the name of the test generated?");
//        Test test = new Test(testName,testKQuestions,testTQuestions,testAQuestions,testCQuestions);
//        new TestGUI(test);
    }
