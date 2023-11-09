package com.example.suncareconnect;

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
import javafx.scene.layout.Pane;


public class NurseUI {

    public static Pane nurseUI() {


        //Stage nurseStage = new Stage();
        //nurseStage.setTitle("SunCare Connect --------- Welcome Nurse Joy");
        VBox mainLayout = new VBox();
        VBox aptPortal = new VBox();
        HBox searchBar = new HBox();
        TextField searchField = new TextField();
        Button searchButton = new Button();
        searchBar.getChildren().addAll(searchField,searchButton);
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
        HBox heightFields = new HBox();
        heightFields.getChildren().addAll(new Label("Height: "),feet,new Label("Feet   "), inches, new Label("Inches"));

        // Weight
        HBox weightField = new HBox();
        weightField.getChildren().addAll(new Label("Weight: "), weight, new Label("lbs"));

        // Allergies
        HBox allergiesField = new HBox();
        allergiesField.getChildren().addAll(new Label("Allergies: "), allergies);

        // Health Concerns
        HBox healthField = new HBox();
        healthField.getChildren().addAll(new Label("Health Concerns: "), healthConcerns);

        Button saveForm = new Button("Save");

        aptPortal.getChildren().addAll(heightFields, weightField, allergiesField, healthField,saveForm);

        mainLayout.getChildren().addAll(aptPortal);



        // --------Search Button Actions--------------------
        searchButton.setOnAction( e-> {
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            // TODO: Check if patient exists. For now, assume all patient IDs entered are valid

            // Text Files -- Appointment files are named "<Patient ID>_Apt_<dd-MM-yyyy>.txt"
            String aptFileName = patientID + "_Apt_" + date + ".txt";
            File aptFile = new File(aptFileName);
            if(aptFile.exists()) {
                // TODO: Resume apt. Populate the appointment field with the data that was entered
            }
            else { // If apt file doesn't exist, the form will appear to create the apt record
                feet.setDisable(false);
                inches.setDisable(false);
                weight.setDisable(false);
                allergies.setDisable(false);
                healthConcerns.setDisable(false);
            }
        });

        // -------Save Form Actions-------------
        saveForm.setOnAction(e-> {
            String patientID = searchField.getText();
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            String aptFileName = patientID + "_Apt_" + date + ".txt";
            try {
                FileWriter fw = new FileWriter(aptFileName);
                fw.write(feet.getText());
                fw.write(inches.getText());
                fw.write(weight.getText());
                fw.write(allergies.getText());
                fw.write(healthConcerns.getText());
                fw.close();
            } catch (IOException e1) {
                System.out.println("Error");
                e1.printStackTrace();
            }
        });

        //Scene scene = new Scene(mainLayout,500,500);
        //nurseStage.setScene(scene);
        //nurseStage.show();


        return mainLayout;
    }

}
