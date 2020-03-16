package com.testassistant;
/*
 * StartingMenu.java
 * Version 1.0
 * @Jason and Nathan 
 * 01/13/2020
 */


import javax.swing.*;
import java.io.File;
import java.io.*;
import java.awt.FileDialog;


public class StartingMenu { 
    public static void main(String[] args) throws Exception{
        String userInput;
        final String[] programSelect = {"Generate test with database","Choose a database to edit","Create database"};
        userInput = (String)JOptionPane.showInputDialog(null,"","",JOptionPane.QUESTION_MESSAGE, null,
                                                        programSelect,programSelect[0]);
        final String CREATE_TEST = programSelect[0];
        final String EDIT_DATABASE = programSelect[1];
        
        JFileChooser fileChooser = new JFileChooser();
        File folderPathAddress = null;
        File sourceFolder;
        String path = System.getProperty("user.dir");
        if(userInput.equals(CREATE_TEST)) { //CREATE TEST
            JFrame chooserFrame = new JFrame();
            path = path.substring(0,path.lastIndexOf("/")) + "/Questions/Databases";
            System.setProperty("apple.awt.fileDialogForDirectories","true");
            FileDialog dialog = new FileDialog(chooserFrame,"Select Directory to Open");
            dialog.setMode(FileDialog.LOAD);
            dialog.setDirectory(path);
            dialog.setVisible(true);
            String folderPathChoosen = dialog.getFile();
            System.out.println(folderPathChoosen);
            File choosenFile = new File(path + "/"+ folderPathChoosen);
            QuestionDatabase qb = new QuestionDatabase(choosenFile);
            TestManager tm = new TestManager(qb);
            new TestFrame(tm);
        }else if(userInput.equals(EDIT_DATABASE)){ //EDITING DATABASE
            JFrame chooserFrame = new JFrame();
            path = path.substring(0,path.lastIndexOf("/")) + "/Questions/Databases";
            System.setProperty("apple.awt.fileDialogForDirectories","true");
            FileDialog dialog = new FileDialog(chooserFrame,"Select Directory to Open");
            dialog.setMode(FileDialog.LOAD);
            dialog.setDirectory(path);
            dialog.setVisible(true);
            String folderPathChoosen = dialog.getFile();
            File choosenFile = new File(path + "/"+ folderPathChoosen);
            System.out.println(path+ "/" + folderPathChoosen);
            QuestionDatabase qb = new QuestionDatabase(choosenFile);
            DatabaseManager manager = new DatabaseManager(qb);
            DatabaseFrame df = new DatabaseFrame(manager);
        }else{ //CREATE DATABASE
            DatabaseManager dm = new DatabaseManager();
//            new DatabaseFrame(new DatabaseManager());
        }
    }
    
    
    
}