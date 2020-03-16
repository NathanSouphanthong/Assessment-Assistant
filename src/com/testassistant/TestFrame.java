/* 
 * TestFrame.java
 * 01/02/2020
 * @Jason and Nathan 
 * Version 1.0
 * Generator main class 
 */
package com.testassistant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;
import java.util.Collections;

public class TestFrame extends JFrame { 
    private TestManager tm;
    private JFrame testFrame;
    private TestPanel testPanel;
    private KQuestionPanel kPanel; 
    private TQuestionPanel tPanel; 
    private CQuestionPanel cPanel;
    private AQuestionPanel aPanel; 
    private FramePanel fPanel;
    private JLabel testLabel;
    
    TestFrame(TestManager tm) throws Exception{
        super("Test Generator"); 
        this.testFrame = this;
        this.tm = tm;
        this.testFrame = this;
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,800); 
        this.setResizable(true);
        this.setBackground(new Color(255,255,255));
        
        this.testPanel = new TestPanel(); 
        this.kPanel = new KQuestionPanel(); 
        this.tPanel = new TQuestionPanel(); 
        this.cPanel = new CQuestionPanel(); 
        this.aPanel = new AQuestionPanel();
        this.fPanel = new FramePanel();
        this.testLabel = new JLabel("TEST NAME: " + tm.getTest().getTestName());
        testFrame.add(testLabel,BorderLayout.NORTH);
        testPanel.setLayout(new GridLayout(1,4));
        testPanel.add(new JScrollPane(kPanel));
        testPanel.add(new JScrollPane(tPanel));
        testPanel.add(new JScrollPane(cPanel));
        testPanel.add(new JScrollPane(aPanel));
        this.add(testPanel,BorderLayout.CENTER);
        this.add(fPanel,BorderLayout.SOUTH);
        this.setVisible(true);
        
    }
    
    public class QuestionListener implements ActionListener{ 
        Question question;  
        JButton button;
        JLabel label;
        JPanel currentPanel;
        
        QuestionListener(Question question,JButton button,JPanel currentPanel){  
            this.question = question; 
            this.button = button;
            this.currentPanel = currentPanel;
        } 
        QuestionListener(Question question,JButton button,JLabel label,JPanel currentPanel){  
            this.question = question; 
            this.button = button;
            this.label = label;
            this.currentPanel = currentPanel;
        } 
        String [] choices = {"EDIT", "EDIT MARK" ,"REMOVE", "EXIT"}; 
        final int EDIT_TEXT = 0;
        final int EDIT_MARK = 1;
        final int REMOVE = 2; 
        final int EXIT = 3; 
        public void actionPerformed(ActionEvent event){  
            int choice = JOptionPane.showOptionDialog(null, "What would you like to do?", "Edit", 
                                                      JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,choices, null); 
            
            if(choice == REMOVE){
                if(this.question.isMultipleChoice()){ 
                    currentPanel.remove(label);
                }
                System.out.println("REMOVE");
                tm.removeQuestion(question);
                currentPanel.remove(button);
                currentPanel.revalidate();
                currentPanel.repaint();
                
                
            }else if(choice == EDIT_TEXT){  
                if(this.question.isMultipleChoice() == false){ 
                    String newText = (String)(JOptionPane.showInputDialog("Edit question",button.getText()));
                    if(newText != null) {
                        if(tm.editText(newText,question)) {
                            button.setText(newText);
                            System.out.println(tm.getDatabase().getKQuestions());
                        }
                    }
                }else if(this.question.isMultipleChoice() == true){ 
                    
                    ArrayList<String> editingOptionsList = question.getChoices(); 
                    editingOptionsList.add(0,question.getText());
                    for(int i = 0; i < editingOptionsList.size(); i++){ 
                        System.out.println(editingOptionsList.get(i));
                    }
                    System.out.println();
                    String [] editingOptionsArray = editingOptionsList.toArray(new String[0]);
                    for(int i = 0; i < editingOptionsArray.length; i++){ 
                        System.out.println(editingOptionsArray[i]);
                    }
                    
                    
                    
                    String userInput = (String)JOptionPane.showInputDialog(null,"","",JOptionPane.QUESTION_MESSAGE,null,editingOptionsArray,editingOptionsArray[0]);
                    System.out.println(userInput);
                    if(this.question.getText().equals(userInput)){ 
                        String newText = (String)(JOptionPane.showInputDialog("Edit question",button.getText()));
                        if(newText != null) {
                            if(tm.editText(newText,question)) {
                                button.setText(newText);
                                System.out.println(tm.getDatabase().getKQuestions());
                                this.question.removeChoice(0);
                            }
                        }else{
                            this.question.removeChoice(0);
                        }
                    }else{
                        if(userInput != null) {
                            String newText = (String)(JOptionPane.showInputDialog("Edit field name",userInput));
                            System.out.println(newText);
                            tm.editMultipleChoiceText(this.question, newText, userInput);
                            this.question.removeChoice(0);
                            this.label.setText("<html>" + this.question.returnMCChoices() + "</html>") ;
                            currentPanel.revalidate();
                        }else{
                            this.question.removeChoice(0);
                        }
                        
                    }
                    
                    
                    
                    
                }
            }else if(choice == EDIT_MARK) {
                int newMark = Integer.parseInt(JOptionPane.showInputDialog(null,"Change mark",question.getNumMarks()));
                question.setNumMarks(newMark);
            }else if(choice == EXIT){ 
                System.out.println("EXIT");
            }
        } 
        
    }
    
    
    
    private class FramePanel extends JPanel {
        public FramePanel() {
            JButton addButton = new JButton("ADD");
            JButton exitButton = new JButton("EXIT");
            JButton exportButton = new JButton("EXPORT");
            JButton regenerateButton = new JButton("REGENERATE");
            JButton editTestNameButton = new JButton("EDIT TEST NAME");
            editTestNameButton.addActionListener(new EditTestNameListener());
            regenerateButton.addActionListener(new RegenerateButtonListener());
            exportButton.addActionListener(new ExportButtonListener());
            exitButton.addActionListener(new ExitButtonListener());
            addButton.addActionListener(new AddButtonListener());
            this.add(editTestNameButton);
            this.add(addButton);
            this.add(regenerateButton);
            this.add(exportButton);
            this.add(exitButton);
            this.setBackground(new Color(255,255,255));
        }
        
        private class ExportButtonListener implements ActionListener{ 
            int export;
            final String[] yesNo = {"yes","no"};
            final int YES = 0;
            final int NO = 1;
            
            public void actionPerformed(ActionEvent event) {
                export = JOptionPane.showOptionDialog(null, "Would you like to export test to PDF file?", "", 
                                                           JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,yesNo, null); 
                if(export == YES) {
                	try { 
                		tm.exportToPDF(); 
                	}catch(IOException e) { 
                		
                	}
                	System.exit(0);
                }else{ 
                    System.exit(0);
                }
            }
        }
        
        private class ExitButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        }
        
        private class RegenerateButtonListener implements ActionListener {
        	final int YES = 0;
        	final String[] yesNo = {"yes","no"};
            public void actionPerformed(ActionEvent event) {
            	int response = JOptionPane.showOptionDialog(null, "Would you like to regenerate test?", "", 
                        JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,yesNo, null); 
            	
            	if(response == YES) {
	                tm.regenerateTest();
	                testFrame.dispose();
	                try { 
	                    new TestFrame(tm);
	                }catch(Exception e) {
	                }
            	}
            }
        }
        
        
        
        private class EditTestNameListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				String newTestName = (String)(JOptionPane.showInputDialog("Edit Test Name",(tm.getTest().getTestName())));
				System.out.println(newTestName); 
				tm.getTest().setTestName(newTestName);
				testLabel.setText("TEST NAME: "+newTestName);
				revalidate();
			} 
        	
        }
        
        private class AddButtonListener implements ActionListener {
            String questionType;
            int numMarks;
            String questionText;
            int isMultipleChoice;
            Question newQuestion;
            JButton newQuestionButton;
            ArrayList<String> choices = new ArrayList<String>();
            final String[] questionTypes = {"Knowledge","Thinking","Application","Communication"};
            final String[] yesNo = {"yes","no"};
            final String KNOWLEDGE_QUESTION = questionTypes[0];
            final String THINKING_QUESTION = questionTypes[1];
            final String APPLICATION_QUESTION = questionTypes[2];
            final String COMMUNICATION_QUESTION = questionTypes[3];
            final int YES = 0;
            final int NO = 1;
            
            public void actionPerformed(ActionEvent event) {
                questionType = (String)JOptionPane.showInputDialog(null,"What is the type of question you wish to add?","",JOptionPane.QUESTION_MESSAGE, null,
                                                                   questionTypes,questionTypes[0]);
                numMarks = Integer.parseInt(JOptionPane.showInputDialog(null,"How many marks is your question worth?"));
                questionText = JOptionPane.showInputDialog(null,"What is your question?");
                isMultipleChoice = JOptionPane.showOptionDialog(null, "Would you like your question to be a multiple choice question?", "", 
                                                                JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,yesNo, null); 
                if(questionType.equals(KNOWLEDGE_QUESTION)) {
                    if(isMultipleChoice == NO) {
                        newQuestion = new KQuestion(questionText,numMarks);
                        if(tm.addQuestion(newQuestion)) {
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,kPanel));
                            kPanel.add(newQuestionButton);
                            kPanel.revalidate();
                            kPanel.repaint();
                        }
                    }else{
                        boolean adding = false;
                        String input;
                        do {
                            try{ 
                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
                                if(!input.equals(null)) {
                                    choices.add(input);
                                    adding = true;
                                }
                            } catch (NullPointerException e){ 
                                adding = false;
                            }
                            
                        }while(adding);
                        newQuestion = new KQuestion(questionText,numMarks,choices);
                        if(tm.addQuestion(newQuestion)) {
                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
                            JButton button = new JButton(newQuestion.getText()); 
                            button.addActionListener(new QuestionListener(newQuestion,button,label,kPanel));
                            kPanel.add(button);
                            kPanel.add(label);           
                            kPanel.revalidate();
                        }
                    }
                }else if(questionType.equals(THINKING_QUESTION)) {
                    if(isMultipleChoice == NO) {
                        newQuestion = new TQuestion(questionText,numMarks);
                        if(tm.addQuestion(newQuestion)) {
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,tPanel));
                            tPanel.add(newQuestionButton);
                            tPanel.revalidate();
                            tPanel.repaint();
                        }
                    }else{
                        boolean adding = false;
                        String input;
                        do {
                            try{ 
                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
                                if(!input.equals(null)) {
                                    choices.add(input);
                                    adding = true;
                                }
                            } catch (NullPointerException e){ 
                                adding = false;
                            }
                            
                        }while(adding);
                        newQuestion = new KQuestion(questionText,numMarks,choices);
                        if(tm.addQuestion(newQuestion)) {
                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,tPanel));
                            tPanel.add(newQuestionButton);
                            tPanel.add(label);
                            tPanel.revalidate();
                            tPanel.repaint();
                        }
                    }
                }else if(questionType.equals(APPLICATION_QUESTION)) {
                    if(isMultipleChoice == NO) {
                        newQuestion = new AQuestion(questionText,numMarks);
                        if(tm.addQuestion(newQuestion)) {
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,aPanel));
                            aPanel.add(newQuestionButton);
                            aPanel.revalidate();
                            aPanel.repaint();
                        }
                    }else{
                       boolean adding = false;
                        String input;
                        do {
                            try{ 
                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
                                if(!input.equals(null)) {
                                    choices.add(input);
                                    adding = true;
                                }
                            } catch (NullPointerException e){ 
                                adding = false;
                            }
                            
                        }while(adding);
                        newQuestion = new KQuestion(questionText,numMarks,choices);
                        if(tm.addQuestion(newQuestion)) {
                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,aPanel));
                            aPanel.add(newQuestionButton);
                            aPanel.add(label);
                            aPanel.revalidate();
                            aPanel.repaint();
                        }
                    }
                }else if(questionType.equals(COMMUNICATION_QUESTION)) {
                    if(isMultipleChoice == NO) {
                        newQuestion = new CQuestion(questionText,numMarks);
                        if(tm.addQuestion(newQuestion)) {
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,cPanel));
                            cPanel.add(newQuestionButton);
                            cPanel.revalidate();
                            cPanel.repaint();
                        }
                    }else{
                        boolean adding = false;
                        String input;
                        do {
                            try{ 
                                input = JOptionPane.showInputDialog(null,"Enter choice: ");
                                if(!input.equals(null)) {
                                    choices.add(input);
                                    adding = true;
                                }
                            } catch (NullPointerException e){ 
                                adding = false;
                            }
                        }while(adding);
                        newQuestion = new KQuestion(questionText,numMarks,choices);
                        if(tm.addQuestion(newQuestion)) {
                            JLabel label = new JLabel("<html>" + newQuestion.returnMCChoices() + "</html>",SwingConstants.LEFT);
                            newQuestionButton = new JButton(questionText);
                            newQuestionButton.addActionListener(new QuestionListener(newQuestion,newQuestionButton,label,cPanel));
                            cPanel.add(newQuestionButton);
                            cPanel.add(label);
                            cPanel.revalidate();
                            cPanel.repaint();
                        }
                    }
                }
            }
        }
    }
    
    
    
    private class TestPanel extends JPanel{ 
        public TestPanel(){ 
            this.setBackground(new Color(255,255,255));
        }
    }
    
//    private class EditingField extends JFrame{ 
//        JButton button; 
//        
//        EditingField(JButton button){ 
//            this.button = button; 
//            this.setSize(200,200); 
//            this.setResizable(false); 
//        }
//    }
    
    private class KQuestionPanel extends JPanel{ 
        ArrayList<KQuestion> kQuestionsArray;
        public KQuestionPanel(){
            JLabel questionsTypeText = new JLabel("Knowledge Questions");
            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.setBackground(new Color(255,255,255));
            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
            this.setVisible(true);
            this.kQuestionsArray = tm.getTest().getKQuestions();
            this.add(questionsTypeText);
            for(int question = 0; question < kQuestionsArray.size();question++){
                if(kQuestionsArray.get(question).isMultipleChoice() == false){ 
                    JButton button = new JButton(kQuestionsArray.get(question).getText());
                    button.addActionListener(new QuestionListener(kQuestionsArray.get(question),button,this));
                    this.add(button);
                }else{ 
                    System.out.println(kQuestionsArray.get(question).getText() + "\n" + kQuestionsArray.get(question).returnMCChoices());
                    JButton button = new JButton(kQuestionsArray.get(question).getText()  );
                    JLabel label= new JLabel("<html>" + kQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
                    button.addActionListener(new QuestionListener(kQuestionsArray.get(question),button,label,this));
                    this.add(button);
                    this.add(label);
                }
            }
        }
        
    }
    
    
    private class TQuestionPanel extends JPanel{ 
        ArrayList<TQuestion> tQuestionsArray;
        public TQuestionPanel(){ 
            JLabel questionsTypeText = new JLabel("Thinking Questions");
            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.setBackground(new Color(255,255,255));
            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
            this.setVisible(true);
            this.tQuestionsArray = tm.getTest().getTQuestions();
            this.add(questionsTypeText);
            for(int question = 0; question < tQuestionsArray.size();question++){
                if(tQuestionsArray.get(question).isMultipleChoice() == false){ 
                    JButton button = new JButton(tQuestionsArray.get(question).getText());
                    button.addActionListener(new QuestionListener(tQuestionsArray.get(question),button,this));
                    this.add(button);
                }else{ 
                    System.out.println(tQuestionsArray.get(question).getText() + "\n" + tQuestionsArray.get(question).returnMCChoices());
                    JButton button = new JButton(tQuestionsArray.get(question).getText()  );
                    JLabel label= new JLabel("<html>" + tQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
                    button.addActionListener(new QuestionListener(tQuestionsArray.get(question),button,label,this));
                    this.add(button);
                    this.add(label);
                }
            }
        }
        
    }
    
    
    private class AQuestionPanel extends JPanel{ 
        ArrayList<AQuestion> aQuestionsArray;
        public AQuestionPanel(){ 
            JLabel questionsTypeText = new JLabel("Application Questions");
            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.setBackground(new Color(255,255,255));
            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
            this.setVisible(true);
            this.aQuestionsArray = tm.getTest().getAQuestions();
            this.add(questionsTypeText);
            for(int question = 0; question < aQuestionsArray.size();question++){
                if(aQuestionsArray.get(question).isMultipleChoice() == false){ 
                    JButton button = new JButton(aQuestionsArray.get(question).getText());
                    button.addActionListener(new QuestionListener(aQuestionsArray.get(question),button,this));
                    this.add(button);
                }else{ 
//                    System.out.println(aQuestionsArray.get(question).getText() + "\n" + tQuestionsArray.get(question).returnMCChoices());
                    JButton button = new JButton(aQuestionsArray.get(question).getText()  );
                    JLabel label= new JLabel("<html>" + aQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
                    button.addActionListener(new QuestionListener(aQuestionsArray.get(question),button,label,this));
                    this.add(button);
                    this.add(label);
                }
            }
        }
    }
    
    private class CQuestionPanel extends JPanel{ 
        ArrayList<CQuestion> cQuestionsArray;
        public CQuestionPanel(){ 
            JLabel questionsTypeText = new JLabel("Communication Questions");
            questionsTypeText.setFont(new Font("Sans Serif",Font.BOLD,20));
            questionsTypeText.setHorizontalAlignment(JLabel.CENTER);
            this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            this.setBackground(new Color(255,255,255));
            this.setBorder(BorderFactory.createLineBorder(new Color(0,0,0))); 
            this.setVisible(true);
            this.cQuestionsArray = tm.getTest().getCQuestions();
            this.add(questionsTypeText);
            for(int question = 0; question < cQuestionsArray.size();question++){
                if(cQuestionsArray.get(question).isMultipleChoice() == false){ 
                    JButton button = new JButton(cQuestionsArray.get(question).getText());
                    button.addActionListener(new QuestionListener(cQuestionsArray.get(question),button,this));
                    this.add(button);
                }else{ 
//                    System.out.println(aQuestionsArray.get(question).getText() + "\n" + tQuestionsArray.get(question).returnMCChoices());
                    JButton button = new JButton(cQuestionsArray.get(question).getText()  );
                    JLabel label= new JLabel("<html>" + cQuestionsArray.get(question).returnMCChoices() + "</html>",SwingConstants.LEFT);
                    button.addActionListener(new QuestionListener(cQuestionsArray.get(question),button,label,this));
                    this.add(button);
                    this.add(label);
                }
            }
        }
    }
    
    
}