package application; // change package name, if different

//package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.io.*;
import java.text.SimpleDateFormat;
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
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #A2E3C4;");
        Label sunCareConnect = new Label("SunCare Connect");
        sunCareConnect.setFont(new Font(15));
        heading.getChildren().addAll(sunCareConnect, logoutButton);
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
        allergiesField.getChildren().addAll(new Label("Allergies: "), allergies);

        // Health Concerns
        HBox healthField = new HBox(10);
        healthField.getChildren().addAll(new Label("Health Concerns: "), healthConcerns);

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
        Label recordTitle = new Label("  <Patient Name>'s Appointment History");
        recordTitle.setFont(new Font(15));
        recordTitle.setAlignment(Pos.BASELINE_CENTER);
        recordHeader.getChildren().add(recordTitle);
        recordPortal.getChildren().add(recordHeader);
        
        // TODO: Replace <Patient Name> with the patient's name
        HBox aptHeader = new HBox(20);
        aptHeader.setStyle("-fx-background-color: #889C90;");
        Label aptTitle = new Label("  Appointment Form for <Patient Name>");
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
        	// TODO: figure out how to return to login page
        });


        // --------Search Button Actions--------------------
        searchButton.setOnAction( e-> {
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            
            String patientFileName = patientID + "AptHistory.txt";
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
	            
	            // TODO: Display the apt history file to the Patient History Portal
	            
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
        	
            // Adds this file name to the patient's apt history
        	String patientFileName = patientID + "AptHistory.txt";
        	File patientFile = new File(patientFileName);
        	try {
				FileWriter fw = new FileWriter(patientFile, true);
				fw.write("\n");
				fw.write("Appointent on " + date + "\n");
				fw.write("Height: " + feet.getText() + "feet " + inches.getText() + " inches\n");
				fw.write("Weight: " + weight.getText() + " lbs\n");
				fw.write("Blood Pressure: " + bloodPressure.getText() + "\n");
				fw.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        	// TODO: Generate the health history text file for the DoctorUI
        	
        	// Resets page
        	searchField.clear();
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
