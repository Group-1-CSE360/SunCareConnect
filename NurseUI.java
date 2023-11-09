package application; // change package name, if different

//package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
    public static Pane nurseUI() {


        //Stage nurseStage = new Stage();
        //nurseStage.setTitle("SunCare Connect --------- Welcome Nurse Joy");
        VBox mainLayout = new VBox(10);
        VBox aptPortal = new VBox(10);
        HBox searchBar = new HBox(10);
        TextField searchField = new TextField();
        Button searchButton = new Button("Search Patient");
        searchBar.getChildren().addAll(searchField,searchButton);
        
        // TODO: The program will change "XXXX" to the patient's name
        aptPortal.getChildren().addAll(searchBar, new Label("Patient: XXXX"));

        // -------------Appointment Form --------------

        // Text Fields (The fields are disabled until an appointment starts)
        TextField feet = new TextField();
        feet.setDisable(true);
        TextField inches = new TextField();
        inches.setDisable(true);
        TextField weight = new TextField();
        weight.setDisable(true);
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
        
        // TODO: Add field for blood pressure for children 13 or older.

        // Allergies
        HBox allergiesField = new HBox(10);
        allergiesField.getChildren().addAll(new Label("Allergies: "), allergies);

        // Health Concerns
        HBox healthField = new HBox(10);
        healthField.getChildren().addAll(new Label("Health Concerns: "), healthConcerns);
        
        // Spacer

        // Save/Submit/Reset buttons
        HBox saveReset = new HBox(10);
        Button saveForm = new Button("Save");
        saveForm.setDisable(true); // The button is disabled until the form is accessible.
        Button submitForm = new Button("Submit");
        submitForm.setDisable(true); // The button is disabled until the form is complete.
        Button resetButton = new Button("Reset");
        resetButton.setDisable(true);
        saveReset.getChildren().addAll(saveForm,resetButton);
                
        aptPortal.getChildren().addAll(heightFields, weightField, allergiesField, healthField,saveReset,submitForm);

        mainLayout.getChildren().addAll(aptPortal);



        // --------Search Button Actions--------------------
        searchButton.setOnAction( e-> {
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            // TODO: Check if patient exists. For now, assume all patient IDs entered are valid
            // TODO: If the patient exists, enable save button. For now, the save button will always be enabled upon Search button click.
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
					// bloodPressure.setDisable(false);
					// bloodPressure.setText(sc.nextLine());
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
                // bloodPressure.setDisable(false);
                allergies.setDisable(false);
                healthConcerns.setDisable(false);
            }
            
            searchField.setDisable(true);
            searchButton.setDisable(true);
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
                fw.write(allergies.getText() + "\n");
                // if(bloodPressure.getText() == "" && <age > 12> ) formDone = false;
                if(allergies.getText().isEmpty()) formDone = false;
                fw.write(healthConcerns.getText() + "\n");
                if(allergies.getText().isEmpty()) formDone = false;
                fw.close();
                
                // If the form is done, nurse can submit the form to the patient's file
                if(formDone) submitForm.setDisable(false);
              /*  else { // If the form isn't done, the nurse won't submit the form, and the webpage will refresh.
                	searchField.clear();
                	searchField.setDisable(false);
                	searchButton.setDisable(false);
                	feet.clear();
                	feet.setDisable(true);
                	inches.clear();
                	inches.setDisable(true);
                	weight.clear();
                	weight.setDisable(true);
                	allergies.clear();
                	allergies.setDisable(true);
                	healthConcerns.clear();
                	healthConcerns.setDisable(true);
                	saveForm.setDisable(true);
                	submitForm.setDisable(true);
                } */
            } catch (IOException e1) {
                System.out.println("Error");
                e1.printStackTrace();
            }
        });
        
        // Submit form button
        submitForm.setOnAction(e -> {
        	// TODO: Add this text file's name to the list of apt files in the patient record.
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
