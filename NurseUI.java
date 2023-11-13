package application; // change package name, if different

import javafx.application.Platform;

//package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javafx.scene.layout.Pane;


public class NurseUI {
	
    private static final Pos BASELINE_RIGHT = null;

	public static Pane nurseUI() {

        VBox mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-background-color: #B3BFB8;");
        HBox heading = new HBox(20);
        heading.setStyle("-fx-background-color: #889C90;");
        HBox portals = new HBox(10);
        VBox aptPortal = new VBox(10);
        VBox recordPortal = new VBox(10);
        Button mailButton = new Button("Mail");
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #A2E3C4;");
        Label sunCareConnect = new Label("SunCare Connect");
        sunCareConnect.setFont(new Font(15));
        heading.getChildren().addAll(sunCareConnect, mailButton, logoutButton);
        heading.setAlignment(Pos.BASELINE_RIGHT);
        HBox searchBar = new HBox();        
        TextField searchField = new TextField();
        Button searchButton = new Button("Search Patient");
        searchButton.setStyle("-fx-background-color: #A2E3C4;");
        searchBar.getChildren().addAll(searchField,searchButton);


        // -------------Appointment Portal --------------

        // Text Fields (The fields are disabled until an appointment starts)
        TextField feet = new TextField();
        feet.setDisable(true);
        TextField inches = new TextField();
        inches.setDisable(true);
        TextField weight = new TextField();
        weight.setDisable(true);
        TextField bloodPressure = new TextField();
        bloodPressure.setDisable(true);
        TextField allergies = new TextField();
        allergies.setDisable(true);
        TextField healthConcerns = new TextField();
        healthConcerns.setDisable(true);

        // Height
        HBox heightFields = new HBox(10);
        heightFields.getChildren().addAll(new Label("Height: "),feet,new Label("Feet   "), inches, new Label("Inches"));

        // Weight
        HBox weightField = new HBox(10);
        weightField.getChildren().addAll(new Label("Weight: "), weight, new Label("lbs"));
        
        // Blood Pressure
        HBox bloodPressureField = new HBox(10);
        bloodPressureField.getChildren().addAll(new Label("Blood Pressure: "), bloodPressure, new Label("If under 13 y.o., enter \"N/A\""));
        
        // Allergies
        HBox allergiesField = new HBox(10);
        allergiesField.getChildren().addAll(new Label("New Allergies: "), allergies, new Label("If there's no new allergies, enter \"N/A\""));

        // Health Concerns
        HBox healthField = new HBox(10);
        healthField.getChildren().addAll(new Label("New Health Concerns: "), healthConcerns, new Label("If there's no new concerns, enter \"N/A\""));

        // Save/Submit/Reset buttons
        HBox saveReset = new HBox(10);
        Button saveForm = new Button("Save");
        saveForm.setDisable(true); // The button is disabled until the form is accessible.
        Button submitForm = new Button("Submit");
        submitForm.setDisable(true); // The button is disabled until the form is complete.
        submitForm.setStyle("-fx-background-color: #A2E3C4;");
        Button resetButton = new Button("Reset");
        resetButton.setDisable(true);
        saveReset.getChildren().addAll(saveForm,resetButton);
        
        
        //-------------Patient Record Portal -------------------
        HBox recordHeader = new HBox(20);
        recordHeader.setStyle("-fx-background-color: #889C90;");
        // TODO: Replace <Patient Name> with the patient's name
        Label recordTitle = new Label("  Appointment History");
        recordTitle.setFont(new Font(15));
        recordTitle.setAlignment(Pos.BASELINE_CENTER);
        recordHeader.getChildren().add(recordTitle);
        recordPortal.getChildren().add(recordHeader);
        
        Label medHistoryHeader = new Label("Medical History");
        TextArea medHistoryText = new TextArea();
        medHistoryText.setPrefHeight(70);
        Label medicationHeader = new Label("Previous Medications");
        TextArea medicationText = new TextArea();
        medicationText.setPrefHeight(70);
        Label immunHeader = new Label("Immunizations");
        TextArea immunText = new TextArea();
        immunText.setPrefHeight(70);
        Label aptHistoryHeader = new Label("Previous Appointments");
        TextArea aptHistoryText = new TextArea();
        recordPortal.getChildren().addAll(medHistoryHeader,medHistoryText,medicationHeader,medicationText,immunHeader,immunText,aptHistoryHeader,aptHistoryText);
        
        
        
        HBox aptHeader = new HBox(20);
        aptHeader.setStyle("-fx-background-color: #889C90;");
        Label aptTitle = new Label("  Appointment Form");
        aptTitle.setFont(new Font(15));
        aptTitle.setAlignment(Pos.BASELINE_CENTER);
        aptHeader.getChildren().add(aptTitle);
        aptPortal.getChildren().addAll(aptHeader,heightFields, weightField, bloodPressureField, allergiesField, healthField,saveReset,submitForm);
        aptPortal.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: black;");
        
        recordPortal.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: black;");
        portals.getChildren().addAll(aptPortal,recordPortal);
        mainLayout.getChildren().addAll(heading,searchBar,portals);

        
        
        // -------------Buttons----------------
        
        // --------Log Out Button Actions-------------
        logoutButton.setOnAction( e-> {
        	Platform.exit();
        });
        
        mailButton.setOnAction( e-> {
        	Stage nextStage = new Stage();
        	Message message2 = new Message();
			try {
				message2.start(nextStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        // --------Search Button Actions--------------------
        searchButton.setOnAction( e-> {
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            
            String patientFileName = patientID + "_Medical_History.txt";
            File patientFile = new File(patientFileName);
            if(patientFile.exists()) {
	            	saveForm.setDisable(false);
	            	resetButton.setDisable(false);
	        
	            // Text Files -- Appointment files are named "<Patient ID>_Apt_<dd-MM-yyyy>.txt"
	            String aptFileName = patientID + "_Apt_" + date + ".txt";
	            File aptFile = new File(aptFileName);
	            if(aptFile.exists()) {
	                // If the file exists, that means the nurse has already started the apt form but hasn't finished it yet. 
	            	// This code will enable all the text fields for editing. 
	            	// Also, the info from the save file will auto-populate the fields.
	            	Scanner sc;
	            	try {
						sc = new Scanner(aptFile);
						feet.setDisable(false);
						feet.setText(sc.nextLine());
						inches.setDisable(false);
						inches.setText(sc.nextLine());
						weight.setDisable(false);
						weight.setText(sc.nextLine());
						bloodPressure.setDisable(false);
						bloodPressure.setText(sc.nextLine());
						allergies.setDisable(false);
						allergies.setText(sc.nextLine());
						healthConcerns.setDisable(false);
						healthConcerns.setText(sc.nextLine());
						sc.close();
						
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
	            }
	            else { // If apt file doesn't exist, the form will appear to create the apt record
	                feet.setDisable(false);
	                inches.setDisable(false);
	                weight.setDisable(false);
	                bloodPressure.setDisable(false);
	                allergies.setDisable(false);
	                healthConcerns.setDisable(false);
	            }
	            
	            searchField.setDisable(true);
	            searchButton.setDisable(true);
	            
	            // -----------Medical History---------------
	            // This part of my code reads the Patient history text files. It reads the file "jdoe2010_Medical_History.txt"
	            ArrayList<String> medRecordContents = new ArrayList<>(); // This array list will store each line from the text file
	            String medRecordFileName = patientID + "_Medical_History.txt"; // This sets up the file name
	            File medRecordFile = new File(medRecordFileName); // This is the file
	            try {
					Scanner sc = new Scanner(medRecordFile); 
					while(sc.hasNextLine()) {
						medRecordContents.add(sc.nextLine()); // This reads each line of the file and adds it to the String array list
					}
					sc.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            
	            // Fill Medical History health concerns text area. The health concerns data are listed between the "Health Concerns" heading and the "Medications" heading
	            int startIndex = medRecordContents.indexOf("Health Concerns"); // This stores the index where the heading, "Health Concerns", is in the array list.
	            int endIndex = medRecordContents.indexOf("Medications"); // This stores the index where the heading "Medications" is in the array list
	            medHistoryText.clear(); 
	            for(int i = startIndex + 1; i < endIndex; i++) {
	            	medHistoryText.appendText(medRecordContents.get(i) + "\n"); // For each health concern listing, add it to the medical history text area section, with a new line at the end of it so all the data isn't mushed together on one line.
	            }
	            
	            // Fill medications in text area
	            startIndex = medRecordContents.indexOf("Medications");
	            endIndex = medRecordContents.indexOf("Immunizations");
	            medicationText.clear();
	            for(int i = startIndex + 1; i < endIndex; i++) {
	            	medicationText.appendText(medRecordContents.get(i) + "\n");
	            }
	            
	            // Fill immunizations text area
	            startIndex = medRecordContents.indexOf("Immunizations");
	            immunText.clear();
	            for(int i = startIndex + 1; i < medRecordContents.size(); i++) {
	            	immunText.appendText(medRecordContents.get(i) + "\n");
	            }
	            
	            // Fill appointment history
	            ArrayList<String> aptHistoryContents = new ArrayList<>();
	            String aptHistoryFileName = patientID + "_AptHistory.txt";
	            File aptHistoryFile = new File(aptHistoryFileName);
	            Scanner sc;
	            try {
					sc = new Scanner(aptHistoryFile);
					while(sc.hasNextLine()) {
						aptHistoryContents.add(sc.nextLine());
					}
					sc.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            aptHistoryText.clear();
	            int tracker = 1;
	            for(int i = 0; i < aptHistoryContents.size(); i++) {
	            	if(tracker == 1) aptHistoryText.appendText("\nDate: " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 2) aptHistoryText.appendText("Height (feet): " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 3) aptHistoryText.appendText("Height (inches): " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 4) aptHistoryText.appendText("Weight: " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 5) aptHistoryText.appendText("Blood Pressure: " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 6) aptHistoryText.appendText("New Allergies: " + aptHistoryContents.get(i) + "\n");
	            	else if(tracker == 7)  {
	            		aptHistoryText.appendText("New Health Concerns: " + aptHistoryContents.get(i) + "\n");
	            		tracker = 0;
	            	}
	            	tracker++;
	            	
	            }
	            
             } 
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Patient ID Error");
                alert.setHeaderText(null);
                alert.setContentText("The patient ID: " + patientID + " does not exist.");
                searchField.clear();
                alert.showAndWait();
            }
        });

        // -------Save Form Actions-------------
        
        // The form data will be saved on a text file and the web page will reset
        saveForm.setOnAction(e-> {
        	// Generates file name
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String aptFileName = patientID + "_Apt_" + date + ".txt";
            
            boolean formDone = true; // If all the data is entered, then the form is ready to submit.
            try {
                FileWriter fw = new FileWriter(aptFileName);
                fw.write(feet.getText() + "\n");
                if(feet.getText().isEmpty()) formDone = false;
                fw.write(inches.getText() + "\n");
                if(inches.getText().isEmpty()) formDone = false;
                fw.write(weight.getText() + "\n");
                if(weight.getText().isEmpty()) formDone = false;
                fw.write(bloodPressure.getText() + "\n");
                if(bloodPressure.getText().isEmpty()) formDone = false;
                fw.write(allergies.getText() + "\n");
                if(allergies.getText().isEmpty()) formDone = false;
                fw.write(healthConcerns.getText() + "\n");
                if(allergies.getText().isEmpty()) formDone = false;
                fw.close();
                
                // If the form is done, nurse can submit the form to the patient's file
                if(formDone) submitForm.setDisable(false);
            } catch (IOException e1) {
                System.out.println("Error");
                e1.printStackTrace();
            }
        });
        
        // Submit form button
        submitForm.setOnAction(e -> {
        	// Generates Appointment File Name
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        	String aptFileName = patientID + "_Apt_" + date + ".txt";
        	File aptFile = new File(aptFileName);
        	aptFile.delete();
            // Adds the apt info to the patient's apt history
        	String patientFileName = patientID + "_AptHistory.txt";
        	File patientFile = new File(patientFileName);
        	try {
				FileWriter fw = new FileWriter(patientFile, true);
				fw.write("\n");
				fw.write(date + "\n");
				fw.write(feet.getText() + "\n" + inches.getText() + "\n");
				fw.write(weight.getText() + "\n");
				fw.write(bloodPressure.getText() + "\n");
				fw.write(allergies.getText() + "\n");
				fw.write(healthConcerns.getText());
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	        	
        	// TODO: Generate the health history text file for the DoctorUI
        	// The health file name looks like " <Patient ID>_Medical_History.txt "
        	String medHistoryFileName = patientID + "_Medical_History.txt";
        	File medHistoryFile = new File(medHistoryFileName);
        	
        	// Stores each line in an array list
        	ArrayList<String> fileContents = new ArrayList<>();
        	Scanner sc;
        	try {
				sc = new Scanner(medHistoryFile);
				while(sc.hasNextLine()) {
					fileContents.add(sc.nextLine());
				}
				sc.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	fileContents.set(1, feet.getText()); // replace old height
        	fileContents.set(2, inches.getText()); // replace old height
        	fileContents.set(4, weight.getText()); // replace old weight
        	if(allergies.getText() != "N/A") {
        		int indexNewAllergy = fileContents.indexOf("Health Concerns");
        		fileContents.add(indexNewAllergy,allergies.getText() + ", " + date);
        	}
        	if(healthConcerns.getText() != "N/A") {
        		int indexNewConcern = fileContents.indexOf("Medications");
        		fileContents.add(indexNewConcern,healthConcerns.getText() + ", " + date);
        	}
        	
        	// Replace the medical history file with the new contents
        	try {
				FileWriter fw = new FileWriter(medHistoryFile);
				for(int i = 0; i < fileContents.size(); i++) {
					fw.write(fileContents.get(i) + "\n");
				}
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	
        	
        	// Resets page
        	searchField.clear();
        	medHistoryText.clear();
        	medicationText.clear();
        	immunText.clear();
        	aptHistoryText.clear();
        	searchField.setDisable(false);
        	searchButton.setDisable(false);
        	feet.clear();
        	feet.setDisable(true);
        	inches.clear();
        	inches.setDisable(true);
        	weight.clear();
        	weight.setDisable(true);
        	bloodPressure.clear();
        	bloodPressure.setDisable(true);
        	allergies.clear();
        	allergies.setDisable(true);
        	healthConcerns.clear();
        	healthConcerns.setDisable(true);
        	saveForm.setDisable(true);
        	submitForm.setDisable(true);
        	resetButton.setDisable(true);
        });
        
        resetButton.setOnAction(e -> {
        	searchField.clear();
        	medHistoryText.clear();
        	medicationText.clear();
        	immunText.clear();
        	aptHistoryText.clear();
        	searchField.setDisable(false);
        	searchButton.setDisable(false);
        	feet.clear();
        	feet.setDisable(true);
        	inches.clear();
        	inches.setDisable(true);
        	weight.clear();
        	weight.setDisable(true);
        	bloodPressure.clear();
        	bloodPressure.setDisable(true);
        	allergies.clear();
        	allergies.setDisable(true);
        	healthConcerns.clear();
        	healthConcerns.setDisable(true);
        	saveForm.setDisable(true);
        	submitForm.setDisable(true);
        	resetButton.setDisable(true);
        });
        
        // TODO: Display appointment history.
        
        

        //Scene scene = new Scene(mainLayout,500,500);
        //nurseStage.setScene(scene);
        //nurseStage.show();


        return mainLayout;
    }

}
