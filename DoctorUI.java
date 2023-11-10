package com.example.suncareconnect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.CheckBox;


import java.time.LocalDate;


public class DoctorUI {

    public static Pane doctorUI(Stage primaryStage) {

        SplitPane splitPane = new SplitPane();
        splitPane.setStyle("-fx-background-color: #B3BFB8;");

        Label leftPaneLabel = createStyledLabel("Patient Info");
        Label rightPaneLabel = createStyledLabel("Patient Record");


//-- Left Pane -----------------------------------------------------------------------------------------------------------------------
        VBox leftBox = new VBox(10);
        leftBox.setAlignment(Pos.TOP_CENTER); // Center alignment for the VBox

        VBox topSection = new VBox(5);
        topSection.setAlignment(Pos.TOP_CENTER);
        VBox bottomSection = new VBox(5);

        Label personalLabel = new Label("Personal");
        personalLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        personalLabel.setMaxWidth(Double.MAX_VALUE); // the label spans almost the width of the VBox
        personalLabel.setAlignment(Pos.CENTER); // Center the text in the label
        topSection.getChildren().add(personalLabel);

        Label visitNotesLabel = new Label("Visit Notes");
        visitNotesLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        visitNotesLabel.setMaxWidth(Double.MAX_VALUE); // the label spans almost the width of the VBox
        visitNotesLabel.setAlignment(Pos.CENTER); // Center the text in the label
        bottomSection.getChildren().add(visitNotesLabel);

        TextArea visitNotesTextArea = new TextArea();
        visitNotesTextArea.setPrefSize(300, 300); // Set preferred size (width, height)


        Button submitButton = new Button("Submit");

        //HBox for alignment
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT); // Align to bottom-right
        buttonBox.getChildren().add(submitButton);

        bottomSection.getChildren().addAll(visitNotesTextArea, buttonBox);

        // Patient Info GridPane
        GridPane patientInfoGrid = new GridPane();
        patientInfoGrid.setHgap(10); // Horizontal gap between columns
        patientInfoGrid.setVgap(10); // Vertical gap between rows

        // Column Constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setMinWidth(100); // Set minimum width for the first column
        ColumnConstraints column2 = new ColumnConstraints(150); // Set preferred width for the second column

        patientInfoGrid.getColumnConstraints().addAll(column1, column2, column1, column2); // Apply column constraints

        // Create a new GridPane for additional rows (rows 4 and 5)
        GridPane additionalInfoGrid = new GridPane();
        additionalInfoGrid.setHgap(10); // Adjust horizontal gap as needed
        additionalInfoGrid.setVgap(10); // Adjust vertical gap as needed

        // Define new column constraints for additionalInfoGrid
        ColumnConstraints addCol1 = new ColumnConstraints(120); // Customize width as needed
        ColumnConstraints addCol2 = new ColumnConstraints(130); // Customize width as needed
        // Continue defining additional columns if necessary

        // Apply these new constraints to additionalInfoGrid
        additionalInfoGrid.getColumnConstraints().addAll(addCol1, addCol2); // Add more columns if necessary

        // Add elements to the additionalInfoGrid
        Label addressLabel = new Label("Address");
        TextField addressTextField = new TextField();
        Label cityLabel = new Label("City");
        TextField cityTextField = new TextField();
        Label stateLabel = new Label("State");
        TextField stateTextField = new TextField();
        Label postalLabel = new Label("Postal");
        TextField postalTextField = new TextField();

        // Add elements to specific columns and rows
        additionalInfoGrid.add(addressLabel, 0, 0); // Adjust column, row indices as needed
        additionalInfoGrid.add(addressTextField, 0, 1); // Adjust column, row indices as needed
        additionalInfoGrid.add(cityLabel, 1, 0); // Adjust column, row indices as needed
        additionalInfoGrid.add(cityTextField, 1, 1); // Adjust column, row indices as needed
        additionalInfoGrid.add(stateLabel, 2, 0); // Adjust column, row indices as needed
        additionalInfoGrid.add(stateTextField, 2, 1); // Adjust column, row indices as needed
        additionalInfoGrid.add(postalLabel, 3, 0); // Adjust column, row indices as needed
        additionalInfoGrid.add(postalTextField, 3, 1); // Adjust column, row indices as needed

        leftBox.getChildren().add(personalLabel);

        //HBox patientInfo = new HBox(5);
        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        Label genderLabel = new Label("Gender:");
        TextField genderTextField = new TextField();
        Label heightLabel = new Label("Height:");
        TextField heightTextField = new TextField();
        Label dobLabel = new Label("Date Of Birth:");
        TextField dobTextField =  new TextField();
        dobTextField.setPromptText("mm/dd/yyyy"); // Set prompt text
        dobTextField.setPrefWidth(100); // Set a preferred width for the Date of Birth text field
        Label weightLabel = new Label("Weight:");
        TextField weightTextField = new TextField();


        // Adding elements to GridPane
        patientInfoGrid.add(nameLabel, 0, 0); // Column 0, Row 0
        patientInfoGrid.add(nameTextField, 1, 0); // Column 1, Row 0
        patientInfoGrid.add(genderLabel, 2, 0); // Column 2, Row 0
        patientInfoGrid.add(genderTextField, 3, 0); // Column 3, Row 0
        patientInfoGrid.add(heightLabel, 2, 1); // Column 2, Row 1
        patientInfoGrid.add(heightTextField, 3, 1); // Column 3, Row 1
        patientInfoGrid.add(dobLabel, 0, 3);
        patientInfoGrid.add(dobTextField, 1, 3); // Column 1, Row 0
        patientInfoGrid.add(weightLabel, 2, 3);
        patientInfoGrid.add(weightTextField, 3, 3); // Column 1, Row 0

        topSection.getChildren().addAll(personalLabel, patientInfoGrid, additionalInfoGrid);

        leftBox.getChildren().addAll(leftPaneLabel, topSection, bottomSection);
        leftBox.setPadding(new Insets(20, 20, 20, 20));

//-- Right Pane -----------------------------------------------------------------------------------------------
        VBox rightBox = new VBox(10);
        rightBox.setAlignment(Pos.TOP_CENTER); // Center alignment for the VBox
        rightBox.getChildren().addAll(rightPaneLabel); // Add the styled label
        rightBox.setPadding(new Insets(20, 20, 20, 20)); //top, right, left, bottom

        VBox topSectionRight = new VBox(5);
        topSection.setAlignment(Pos.TOP_CENTER);
        VBox bottomSectionRight = new VBox(5);

        Label healthHistoryLabel = new Label("Health History");
        healthHistoryLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        healthHistoryLabel.setMaxWidth(Double.MAX_VALUE); // the label spans almost the width of the VBox
        healthHistoryLabel.setAlignment(Pos.CENTER); // Center the text in the label
        rightBox.getChildren().add(healthHistoryLabel);

        //ALLERGIES
        HBox allergiesSection = new HBox(5);
        Label allergiesLabel = new Label("Allergies:");
        allergiesLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea allergiesTextArea = new TextArea();
        allergiesTextArea.setPrefHeight(30); // Set preferred height
        allergiesTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(allergiesTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        allergiesSection.getChildren().addAll(allergiesLabel, allergiesTextArea);

        //HEALTH CONCERNS
        HBox healthConcernsSection = new HBox(5);
        Label healthConcernsLabel = new Label("Health Concerns:");
        healthConcernsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea healthConcernsTextArea = new TextArea();
        healthConcernsTextArea.setPrefHeight(30); // Set preferred height
        healthConcernsTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(healthConcernsTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        healthConcernsSection.getChildren().addAll(healthConcernsLabel, healthConcernsTextArea);

        //MEDICAL HISTORY
        HBox medicalHistorySection = new HBox(5);
        Label medicalHistoryLabel = new Label("Medical History:");
        medicalHistoryLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea medicalHistoryTextArea = new TextArea();
        medicalHistoryTextArea.setPrefHeight(30); // Set preferred height
        medicalHistoryTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(medicalHistoryTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        medicalHistorySection.getChildren().addAll(medicalHistoryLabel, medicalHistoryTextArea);

        //MEDICATIONS
        HBox medicationsSection = new HBox(5);
        Label medicationsLabel = new Label("Medications:");
        medicationsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea medicationsTextArea = new TextArea();
        medicationsTextArea.setPrefHeight(30); // Set preferred height
        medicationsTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(medicationsTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        medicationsSection.getChildren().addAll(medicationsLabel, medicationsTextArea);

        //IMMUNIZATIONS
        HBox immunizationsSection = new HBox(5);
        Label immunizationsLabel = new Label("Immunizations:");
        immunizationsLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea immunizationsTextArea = new TextArea();
        immunizationsTextArea.setPrefHeight(30); // Set preferred height
        immunizationsTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(immunizationsTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        immunizationsSection.getChildren().addAll(immunizationsLabel, immunizationsTextArea);


        topSectionRight.getChildren().addAll(healthHistoryLabel, allergiesSection, healthConcernsSection, medicalHistorySection, medicationsSection, immunizationsSection);

//-- BOTTOM SECTION RIGHT ------------------------------------------------------------------------------------

        Label prescriptionFormLabel = new Label("Prescription Form");
        prescriptionFormLabel.setStyle("-fx-background-color: #889C90; -fx-border-color: black; -fx-border-width: 1; -fx-padding: 5;");
        prescriptionFormLabel.setMaxWidth(Double.MAX_VALUE); // the label spans almost the width of the VBox
        prescriptionFormLabel.setAlignment(Pos.CENTER); // Center the text in the label

        VBox rxSection = new VBox(5);
        rxSection.setAlignment(Pos.CENTER); // Centering contents of rxSection
        Label rxLabel = new Label("Rx: Medication/Strength/Frequency");
        rxLabel.setMinWidth(Label.USE_PREF_SIZE);

        TextArea rxTextArea = new TextArea();
        rxTextArea.setPrefHeight(30); // Set preferred height
        rxTextArea.setMaxWidth(Double.MAX_VALUE); // Allow the TextArea to grow horizontally
        HBox.setHgrow(rxTextArea, Priority.ALWAYS); // Make the TextArea grow to fill space
        rxSection.getChildren().addAll(rxLabel, rxTextArea);

        VBox dateSection = new VBox(5);
        dateSection.setAlignment(Pos.CENTER); // Centering contents of rxSection
        Label dateLabel = new Label("Date signed:");
        dateLabel.setMinWidth(Label.USE_PREF_SIZE);

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now()); // Set the default value to the current date


        CheckBox disclaimerCheckBox = new CheckBox("By checking this box, you are authorizing this prescription for the patient");
        disclaimerCheckBox.setFont(new Font("Arial", 10)); // You can adjust the font and size as needed
        dateSection.getChildren().addAll(dateLabel, datePicker, disclaimerCheckBox);

        Button submitButton2 = new Button("Submit");

        //HBox for alignment
        HBox buttonBox2 = new HBox();
        buttonBox2.setAlignment(Pos.BOTTOM_CENTER); // Align to bottom-right
        buttonBox2.getChildren().add(submitButton2);

        bottomSectionRight.getChildren().addAll(prescriptionFormLabel, rxSection, dateSection, buttonBox2);

        rightBox.getChildren().addAll(topSectionRight, bottomSectionRight);

        HBox patientRecord = new HBox(10);
        patientRecord.setAlignment(Pos.CENTER_LEFT);
       // patientRecord.getChildren().addAll(allergiesLabel, allergiesTextArea, healthConcernsLabel, healthConcernsTextArea);
        rightBox.getChildren().add(patientRecord);

        splitPane.getItems().addAll(leftBox, rightBox);

        // Apply border to the left and right VBox containers
        applyBorder(leftBox);
        applyBorder(rightBox);

        // Add spacing between the panes
        splitPane.setPadding(new Insets(10)); // Adjust this value as needed
        splitPane.setDividerPositions(0.5); // Adjust the divider position if needed



//-- TOP BAR ---------------------------------------------------------------------------------------------------------
        BorderPane topBar = new BorderPane();

        Label programName = new Label("Sun Care Connect");


        // Set the program name label on the left side of the top bar
        topBar.setLeft(programName);

        // Welcome label and buttons container
        HBox rightSideElements = new HBox(10); // Space between elements
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

        // Set the right side container to the right side of the top bar
        topBar.setRight(rightSideElements);

        // Use BorderPane for better layout control
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #B3BFB8;");
        mainLayout.setTop(topBar); // Set the top bar at the top
        mainLayout.setCenter(splitPane); // Set the splitPane in the center

        // Add padding to the main layout
        mainLayout.setPadding(new Insets(10)); // This adds spacing around the entire layout

        return mainLayout;
    }


    // Method to apply a border to a VBox
    private static void applyBorder(VBox vbox) {
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1));
        vbox.setBorder(new Border(borderStroke));
        vbox.setPadding(new Insets(20)); // Add padding inside the VBox
    }

    // Method to create a styled label
    private static Label createStyledLabel(String text) {
        Label label = new Label(text);
        // Additional styling can be added here if needed
        return label;
    }
}
