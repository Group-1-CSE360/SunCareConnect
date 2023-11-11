package com.example.suncareconnect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DoctorUI {

    public static Pane doctorUI(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-background-color: #B3BFB8;");

        Label leftPaneLabel = createStyledLabel("Patient Info");
        Label rightPaneLabel = createStyledLabel("Patient Record");


//-- Left Pane -----------------------------------------------------------------------------------------------------------------------
        VBox leftBox = new VBox(10);
        leftBox.setAlignment(Pos.TOP_CENTER);

        VBox topSection = new VBox(5);
        topSection.setAlignment(Pos.TOP_CENTER);
        VBox bottomSection = new VBox(5);

        Label personalLabel = new Label("Personal");
        personalLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        personalLabel.setMaxWidth(Double.MAX_VALUE);
        personalLabel.setAlignment(Pos.CENTER);
        topSection.getChildren().add(personalLabel);

        Label visitNotesLabel = new Label("Visit Notes");
        visitNotesLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        visitNotesLabel.setMaxWidth(Double.MAX_VALUE);
        visitNotesLabel.setAlignment(Pos.CENTER);
        bottomSection.getChildren().add(visitNotesLabel);

        TextArea visitNotesTextArea = new TextArea();
        visitNotesTextArea.setPrefSize(300, 300); //(width, height)


        Button submitButton = new Button("Submit");

        //HBox for alignment
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().add(submitButton);

        bottomSection.getChildren().addAll(visitNotesTextArea, buttonBox);

        // Patient Info GridPane
        GridPane patientInfoGrid = new GridPane();
        patientInfoGrid.setHgap(10);
        patientInfoGrid.setVgap(10);

        // Column Constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(100);
        ColumnConstraints column2 = new ColumnConstraints(150);

        patientInfoGrid.getColumnConstraints().addAll(column1, column2, column1, column2);

        // GridPane for additional rows (rows 4 and 5)
        GridPane additionalInfoGrid = new GridPane();
        additionalInfoGrid.setHgap(10);
        additionalInfoGrid.setVgap(10);


        ColumnConstraints addCol1 = new ColumnConstraints(120);
        ColumnConstraints addCol2 = new ColumnConstraints(130);

        additionalInfoGrid.getColumnConstraints().addAll(addCol1, addCol2);

        Label addressLabel = new Label("Address");
        TextField addressTextField = new TextField();
        Label cityLabel = new Label("City");
        TextField cityTextField = new TextField();
        Label stateLabel = new Label("State");
        TextField stateTextField = new TextField();
        Label postalLabel = new Label("Postal");
        TextField postalTextField = new TextField();

        // Adding elements to specific columns and rows
        additionalInfoGrid.add(addressLabel, 0, 0);
        additionalInfoGrid.add(addressTextField, 0, 1);
        additionalInfoGrid.add(cityLabel, 1, 0);
        additionalInfoGrid.add(cityTextField, 1, 1);
        additionalInfoGrid.add(stateLabel, 2, 0);
        additionalInfoGrid.add(stateTextField, 2, 1);
        additionalInfoGrid.add(postalLabel, 3, 0); 
        additionalInfoGrid.add(postalTextField, 3, 1);

        leftBox.getChildren().add(personalLabel);

        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Label genderLabel = new Label("Gender:");
        TextField genderTextField = new TextField();
        Label heightLabel = new Label("Height:");
        TextField heightTextField = new TextField();
        Label dobLabel = new Label("Date Of Birth:");
        TextField dobTextField =  new TextField();
        dobTextField.setPromptText("mm/dd/yyyy"); //prompt text
        dobTextField.setPrefWidth(100);
        Label weightLabel = new Label("Weight:");
        TextField weightTextField = new TextField();

        submitButton.setOnAction(event -> {
            String patientName = nameTextField.getText();
            String dob = dobTextField.getText();
            String visitNotes = visitNotesTextArea.getText();
            writeToFile(patientName, dob, visitNotes);
            visitNotesTextArea.clear();
        });

        // Adding elements to GridPane
        patientInfoGrid.add(nameLabel, 0, 0);
        patientInfoGrid.add(nameTextField, 1, 0);
        patientInfoGrid.add(genderLabel, 2, 0);
        patientInfoGrid.add(genderTextField, 3, 0);
        patientInfoGrid.add(heightLabel, 2, 1);
        patientInfoGrid.add(heightTextField, 3, 1);
        patientInfoGrid.add(dobLabel, 0, 3);
        patientInfoGrid.add(dobTextField, 1, 3);
        patientInfoGrid.add(weightLabel, 2, 3);
        patientInfoGrid.add(weightTextField, 3, 3);

        topSection.getChildren().addAll(personalLabel, patientInfoGrid, additionalInfoGrid);

        leftBox.getChildren().addAll(leftPaneLabel, topSection, bottomSection);
        leftBox.setPadding(new Insets(20, 20, 20, 20));

//-- Right Pane -----------------------------------------------------------------------------------------------
        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.TOP_CENTER);
        rightBox.getChildren().addAll(rightPaneLabel);
        rightBox.setPadding(new Insets(20, 20, 20, 20)); //top, right, left, bottom

        VBox topSectionRight = new VBox(5);
        topSection.setAlignment(Pos.TOP_CENTER);
        VBox bottomSectionRight = new VBox(5);

        Label healthHistoryLabel = new Label("Health History");
        healthHistoryLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        healthHistoryLabel.setMaxWidth(Double.MAX_VALUE);
        healthHistoryLabel.setAlignment(Pos.CENTER);
        rightBox.getChildren().add(healthHistoryLabel);

        //ALLERGIES
        HBox allergiesSection = new HBox(5);
        Label allergiesLabel = new Label("Allergies:");
        allergiesLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea allergiesTextArea = new TextArea();
        allergiesTextArea.setPrefHeight(30);
        allergiesTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(allergiesTextArea, Priority.ALWAYS);
        allergiesSection.getChildren().addAll(allergiesLabel, allergiesTextArea);

        //HEALTH CONCERNS
        HBox healthConcernsSection = new HBox(5);
        Label healthConcernsLabel = new Label("Health Concerns:");
        healthConcernsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea healthConcernsTextArea = new TextArea();
        healthConcernsTextArea.setPrefHeight(30);
        healthConcernsTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(healthConcernsTextArea, Priority.ALWAYS);
        healthConcernsSection.getChildren().addAll(healthConcernsLabel, healthConcernsTextArea);

        //MEDICAL HISTORY
        HBox medicalHistorySection = new HBox(5);
        Label medicalHistoryLabel = new Label("Medical History:");
        medicalHistoryLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea medicalHistoryTextArea = new TextArea();
        medicalHistoryTextArea.setPrefHeight(30);
        medicalHistoryTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(medicalHistoryTextArea, Priority.ALWAYS);
        medicalHistorySection.getChildren().addAll(medicalHistoryLabel, medicalHistoryTextArea);

        //MEDICATIONS
        HBox medicationsSection = new HBox(5);
        Label medicationsLabel = new Label("Medications:");
        medicationsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea medicationsTextArea = new TextArea();
        medicationsTextArea.setPrefHeight(30);
        medicationsTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(medicationsTextArea, Priority.ALWAYS);
        medicationsSection.getChildren().addAll(medicationsLabel, medicationsTextArea);

        //IMMUNIZATIONS
        HBox immunizationsSection = new HBox(5);
        Label immunizationsLabel = new Label("Immunizations:");
        immunizationsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea immunizationsTextArea = new TextArea();
        immunizationsTextArea.setPrefHeight(30);
        immunizationsTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(immunizationsTextArea, Priority.ALWAYS);
        immunizationsSection.getChildren().addAll(immunizationsLabel, immunizationsTextArea);


        topSectionRight.getChildren().addAll(healthHistoryLabel, allergiesSection, healthConcernsSection, medicalHistorySection, medicationsSection, immunizationsSection);

//-- BOTTOM SECTION RIGHT ------------------------------------------------------------------------------------

        Label prescriptionFormLabel = new Label("Prescription Form");
        prescriptionFormLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        prescriptionFormLabel.setMaxWidth(Double.MAX_VALUE);
        prescriptionFormLabel.setAlignment(Pos.CENTER);

        VBox rxSection = new VBox(5);
        rxSection.setAlignment(Pos.CENTER);
        Label rxLabel = new Label("Rx: Medication/Strength/Frequency");
        rxLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea rxTextArea = new TextArea();
        rxTextArea.setPrefHeight(30);
        rxTextArea.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(rxTextArea, Priority.ALWAYS);
        rxSection.getChildren().addAll(rxLabel, rxTextArea);

        VBox dateSection = new VBox(5);
        dateSection.setAlignment(Pos.CENTER);
        Label dateLabel = new Label("Date signed:");
        dateLabel.setMinWidth(Label.USE_PREF_SIZE);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());


        CheckBox disclaimerCheckBox = new CheckBox("By checking this box, you are authorizing this prescription for the patient");
        disclaimerCheckBox.setFont(new Font("Arial", 10));
        dateSection.getChildren().addAll(dateLabel, datePicker, disclaimerCheckBox);

        Button submitButton2 = new Button("Submit");

        //HBox for alignment
        HBox buttonBox2 = new HBox();
        buttonBox2.setAlignment(Pos.BOTTOM_CENTER);
        buttonBox2.getChildren().add(submitButton2);

        submitButton2.setOnAction(event -> {
            bottomSectionRight.getChildren().clear(); //clear existing contents in box

            Label confirmationLabel = new Label("Prescription sent to the pharmacy on file.");
            confirmationLabel.setFont(new Font("Arial", 16));
            confirmationLabel.setTextFill(Color.GREEN);

            bottomSectionRight.getChildren().add(confirmationLabel);
        });

        bottomSectionRight.getChildren().addAll(prescriptionFormLabel, rxSection, dateSection, buttonBox2);

        rightBox.getChildren().addAll(topSectionRight, bottomSectionRight);

        HBox patientRecord = new HBox(10);
        patientRecord.setAlignment(Pos.CENTER_LEFT);
        rightBox.getChildren().add(patientRecord);

        splitPane.getItems().addAll(leftBox, rightBox);

        //border for the left and right VBox containers
        applyBorder(leftBox);
        applyBorder(rightBox);

        //spacing between the panes
        splitPane.setPadding(new Insets(10));
        splitPane.setDividerPositions(0.5);



//-- TOP BAR ---------------------------------------------------------------------------------------------------------
        BorderPane topBar = new BorderPane();

        Label programName = new Label("Sun Care Connect");

        topBar.setLeft(programName);

        // Welcome label and buttons container
        HBox rightSideElements = new HBox(10);
        rightSideElements.setAlignment(Pos.CENTER_RIGHT);

        // Welcome label
        Label welcomeLabel = new Label("Welcome Dr. Oz");

        // Mail button
        Button mailButton = new Button("Mail");

        // Log out button
        Button logoutButton = new Button("Log out");
        logoutButton.setOnAction(event -> {
            // Switch back to the LoginUI
            LoginUI loginUI = new LoginUI();
            loginUI.start(primaryStage);
        });

        // welcome label and buttons on right side container
        rightSideElements.getChildren().addAll(welcomeLabel, mailButton, logoutButton);

        topBar.setRight(rightSideElements);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #B3BFB8;");
        mainLayout.setTop(topBar); // Set the top bar at the top
        mainLayout.setCenter(splitPane); // Set the splitPane in the center

        //padding to the main layout
        mainLayout.setPadding(new Insets(10)); // This adds spacing around the entire layout

//-- READING PATIENT DATA --------------------------------------------------------------------------
        // Read patient data from the text file
        String[] patientData = readPatientInfo("/Users/skyy/IdeaProjects/SunCareConnect/src/main/java/com/example/suncareconnect/patient_info.txt");

        // Check if the array has the expected number of elements
        if (patientData.length >= 6) {
            nameTextField.setText(patientData[1]);
            dobTextField.setText(patientData[2]);
            genderTextField.setText(patientData[3]);
            addressTextField.setText(patientData[4]);
            cityTextField.setText(patientData[5]);
            stateTextField.setText(patientData[6]);
            postalTextField.setText(patientData[7]);

            heightTextField.setText(patientData[8] + "' " + patientData[9] + "''");
            weightTextField.setText(patientData[10]);
            allergiesTextArea.setText(patientData[11]);
            healthConcernsTextArea.setText("none");
            medicalHistoryTextArea.setText(patientData[12] + "\n" + patientData[13]);
            medicationsTextArea.setText(patientData[14] + "\n" + patientData[15]);
            immunizationsTextArea.setText(patientData[16] + "\n" + patientData[17] + "\n" + patientData[18] + "\n" + patientData[19] + "\n" + patientData[20] + "\n" + patientData[21]);


            // Make text fields uneditable
            nameTextField.setEditable(false);
            dobTextField.setEditable(false);
            genderTextField.setEditable(false);
            addressTextField.setEditable(false);
            cityTextField.setEditable(false);
            stateTextField.setEditable(false);
            postalTextField.setEditable(false);
            heightTextField.setEditable(false);
            weightTextField.setEditable(false);
            allergiesTextArea.setEditable(false);
            healthConcernsTextArea.setEditable(false);
            medicalHistoryTextArea.setEditable(false);
            medicationsTextArea.setEditable(false);
            immunizationsTextArea.setEditable(false);

        } else {
            System.out.println("Patient data not loaded correctly.");
        }

        return mainLayout;
    }


    // Method to apply a border to a VBox
    private static void applyBorder(VBox vbox) {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
        vbox.setBorder(new Border(borderStroke));
        vbox.setPadding(new Insets(20));
    }

    // Method to create a styled label
    private static Label createStyledLabel(String text) {
        Label label = new Label(text);
        return label;
    }

    //Method to read patient info to fill out page
    private static String[] readPatientInfo(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines.toArray(new String[0]);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return new String[0];
    }

    //Method to write visit note to text file
    private static void writeToFile(String patientName, String dob, String visitNotes) {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("patient_note.txt", true)))) {
            out.println("Date and Time of Entry: " + formattedDateTime);
            out.println("Patient Name: " + patientName);
            out.println("DOB: " + dob);
            out.println("Visit Notes: " + visitNotes);
            out.println("----------------------------------");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }



}
