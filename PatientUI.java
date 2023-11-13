package com.example.suncareconnect;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class PatientUI {

	static ArrayList<String> patientInfoContents = new ArrayList<>();
	static ArrayList<String> patientAptContents = new ArrayList<>();
	static int patientInfoCountIndex = 0;
	static int appointmentCount = 0;
	static String doctorName = "Jane Doe"; // Placeholder
	static String doctorsNotes = "This is a placeholder."; // Placeholder

	public static Pane patientUI(Stage primaryStage, String patientID) {
		VBox mainLayout = new VBox();
		mainLayout.setStyle("-fx-background-color: #B3BFB8;");

		// -----------Load
		// Data-------------------------------------------------------------------------------------------------------------------
		String patientInfoFileName = patientID + "_Personal_Info.txt";
		File patientInfoFile = new File(patientInfoFileName);
		try {
			Scanner sc = new Scanner(patientInfoFile);
			while (sc.hasNextLine()) {
				patientInfoContents.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String patientAptFileName = patientID + "_AptHistory.txt";
		File patientAptFile = new File(patientAptFileName);
		try {
			Scanner sc = new Scanner(patientAptFile);
			while (sc.hasNextLine()) {
				patientAptContents.add(sc.nextLine());
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		appointmentCount = patientAptContents.size() / 7;

		// -------------Buttons------------------------------------------------------------------------------------------------------------------------------------
		Button logoutButton = new Button("Log Out");
		logoutButton.setStyle("-fx-background-color: #A2E3C4;");
		logoutButton.setPrefHeight(27);

		ToggleButton editPersonalInfoButton = new ToggleButton("Edit");
		editPersonalInfoButton.setStyle("-fx-background-color: #A2E3C4;");
		editPersonalInfoButton.setPrefWidth(60);
		editPersonalInfoButton.setPrefHeight(27);
		VBox editPersonalInfoButtonHolder = new VBox(editPersonalInfoButton);
		editPersonalInfoButtonHolder.setPadding(new Insets(0, 0, 4, 0));

		Button messageCenterButton = new Button("Messages");
		messageCenterButton.setStyle("-fx-background-color: #A2E3C4;");
		messageCenterButton.setPrefHeight(27);

		// -------------Patient Info Form
		// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		TextArea greetingMessage = new TextArea();
		greetingMessage.setText("Welcome, " + patientInfoContents.get(1) + " " + patientInfoContents.get(2));
		greetingMessage.setPadding(new Insets(2, 2, 2, 2));
		VBox greetingMessageHolder = new VBox(greetingMessage);
		greetingMessageHolder.setPadding(new Insets(0, 2, 9, 0));

		Label patientInfoTitle = labelMaker("Your Information: ", 1);
		patientInfoTitle.setPrefWidth(440);
		HBox patientInfoTitleHolder = new HBox(patientInfoTitle, editPersonalInfoButton);
		patientInfoTitleHolder.setPadding(new Insets(2, 0, 0, 2));

		HBox patientFirstName = labelTextBoxMaker("First Name: ");
		HBox patientLastName = labelTextBoxMaker("Last Name: ");
		HBox patientDoB = labelTextBoxMaker("Date of Birth: ");
		HBox patientGender = labelTextBoxMaker("Gender: ");

		Label patientContactInfoTitle = labelMaker("Your Contact Information: ", 2);
		VBox patientContactInfoTitleHolder = new VBox(patientContactInfoTitle);
		patientContactInfoTitleHolder.setPadding(new Insets(0, 0, 0, 2));

		HBox patientAddress = labelTextBoxMaker("Address: ");
		HBox patientCity = labelTextBoxMaker("City: ");
		HBox patientZipCode = labelTextBoxMaker("Zip Code: ");
		HBox patientState = labelTextBoxMaker("State: ");
		HBox patientEmail = labelTextBoxMaker("Email Address: ");
		HBox patientPhoneNumber = labelTextBoxMaker("Phone Number: ");

		Label patientPharmacyInfoTitle = labelMaker("Your Pharmacy Information: ", 2);
		VBox patientPharmacyInfoTitleHolder = new VBox(patientPharmacyInfoTitle);
		patientPharmacyInfoTitleHolder.setPadding(new Insets(0, 0, 0, 2));

		HBox pharmacyName = labelTextBoxMaker("Pharmacy Name: ");
		HBox pharmacyAddress = labelTextBoxMaker("Pharmacy Address: ");
		HBox pharmacyCity = labelTextBoxMaker("Pharmacy City: ");
		HBox pharmacyZipCode = labelTextBoxMaker("Pharmacy Zip Code: ");
		HBox pharmacyState = labelTextBoxMaker("Pharmacy State: ");

		VBox yourInfoVBox = new VBox(patientInfoTitleHolder, patientFirstName, patientLastName, patientDoB,
				patientGender, patientContactInfoTitleHolder, patientAddress, patientCity, patientZipCode, patientState,
				patientEmail, patientPhoneNumber, patientPharmacyInfoTitleHolder, pharmacyName, pharmacyAddress,
				pharmacyCity, pharmacyZipCode, pharmacyState);

		VBox leftBody = new VBox(greetingMessageHolder, yourInfoVBox);
		leftBody.minWidth(500);

		// -------------Patient Email Center
		// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		Label messageCenterTitle = labelMaker("Message Center: ", 1);
		messageCenterTitle.setPrefWidth(422);
		HBox messageCenterHolder = new HBox(messageCenterTitle, messageCenterButton);
		messageCenterHolder.setPadding(new Insets(0, 0, 2, 0));

		Label fromLabel = new Label("From: ");
		fromLabel.setFont(new Font("Arial", 15));
		fromLabel.setTextFill(Color.BLACK);
		fromLabel.setStyle("-fx-background-color: #f0f7f4;");
		fromLabel.setPrefHeight(30);
		fromLabel.setPrefWidth(200);
		fromLabel.setPadding(new Insets(2, 2, 2, 4));
		fromLabel.setAlignment(Pos.CENTER_LEFT);
		VBox fromLabelHolder = new VBox(fromLabel);

		Label senderNameLabel = new Label("Nurse Joy"); // Place Holder
		senderNameLabel.setFont(new Font("Arial", 15));
		senderNameLabel.setTextFill(Color.BLACK);
		senderNameLabel.setStyle("-fx-background-color: #f0f7f4;");
		senderNameLabel.setPrefHeight(30);
		senderNameLabel.setPrefWidth(300);
		senderNameLabel.setPadding(new Insets(2, 2, 2, 4));
		senderNameLabel.setAlignment(Pos.CENTER_LEFT);
		VBox senderNameLabelHolder = new VBox(senderNameLabel);
		senderNameLabelHolder.setPadding(new Insets(0, 0, 0, 2));

		HBox messageAddressBox = new HBox(fromLabelHolder, senderNameLabelHolder);
		messageAddressBox.setMaxWidth(500);

		TextArea messagePreview = new TextArea();
		messagePreview.setPadding(new Insets(2, 2, 2, 2));
		messagePreview.setPrefSize(255, 255);
		VBox messagePreviewHolder = new VBox(messagePreview);
		messagePreviewHolder.setPadding(new Insets(2, 0, 10, 0));
		messagePreviewHolder.prefHeight(255);
		messagePreviewHolder.maxHeight(255);

		VBox messageCenterVBox = new VBox(messageCenterHolder, messageAddressBox, messagePreviewHolder);

		// -------------Visit History
		// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		Label visitHistoryTitle = labelMaker("Visit History: ", 1);
		VBox visitHistoryHolder = new VBox(visitHistoryTitle);
		visitHistoryHolder.setPadding(new Insets(0, 0, 2, 0));

		int visitHistoryVBoxAmount = 20;
		ArrayList<VBox> visitHistoryVBoxes = visitHisotryHBoxMaker(visitHistoryVBoxAmount);
		VBox allVisitHistoryVBox = new VBox();

		for (int i = 0; i < visitHistoryVBoxAmount; ++i) {
			allVisitHistoryVBox.getChildren().add(visitHistoryVBoxes.get(i));
		}

		ScrollPane aptHistoryScrollPane = new ScrollPane();
		aptHistoryScrollPane.setContent(allVisitHistoryVBox);
		aptHistoryScrollPane.setStyle("-fx-background: #B3BFB8");
		aptHistoryScrollPane.setMaxHeight(600);

		VBox rightBody = new VBox(messageCenterVBox, aptHistoryScrollPane);
		rightBody.setPadding(new Insets(0, 0, 0, 10));
		rightBody.setMaxWidth(500);
		HBox mainBody = new HBox(leftBody, rightBody);

		mainBody.setPadding(new Insets(10, 20, 20, 20));

		// ----- Main Header----------------------------------------------------
		Label mainHeaderLabel = new Label("Patient Portal");
		mainHeaderLabel.setFont(new Font("Arial", 15));
		mainHeaderLabel.setTextFill(Color.WHITE);
		mainHeaderLabel.setStyle("-fx-background-color: #3c493f;");
		mainHeaderLabel.setPrefHeight(28);
		mainHeaderLabel.setPrefWidth(990);
		mainHeaderLabel.setPadding(new Insets(2, 2, 2, 2));
		mainHeaderLabel.setAlignment(Pos.CENTER_LEFT);
		HBox mainHeaderLabelHolder = new HBox(mainHeaderLabel, logoutButton);
		mainHeaderLabelHolder.setPadding(new Insets(0, 0, 10, 0));

		// -------------------Merging Header and Body----------------------------
		VBox mainVBox = new VBox(mainHeaderLabelHolder, mainBody);

		mainLayout.getChildren().addAll(mainVBox);

		// --------Button Actions-------------
		logoutButton.setOnAction(e -> {
			Platform.exit();
		});

		messageCenterButton.setOnAction(e -> {
			Message newMessageStage = new Message();
			try {
				newMessageStage.start(primaryStage);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		// --------Toggle Button to Edit Personal Info-----------------
		// On toggle on: Sets Contact Info and Pharmacy info text fields to editable.
		// Changes button and text visual style.
		// On toggle off: Sets Contact Info and Pharmacy info text fields to
		// un-editable. Changes button and text visual style. Saves to file.
		editPersonalInfoButton.setOnAction(new EventHandler<ActionEvent>() {

			private File file;

			@Override
			public void handle(ActionEvent arg0) {
				if (editPersonalInfoButton.isSelected()) {
					editPersonalInfoButton.setText("Done");
					editPersonalInfoButton.setStyle("-fx-text-fill: #ffffff;");

					((TextField) (patientAddress.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientAddress.getChildren().get(1))).setEditable(true);

					((TextField) (patientCity.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientCity.getChildren().get(1))).setEditable(true);

					((TextField) (patientZipCode.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientZipCode.getChildren().get(1))).setEditable(true);

					((TextField) (patientState.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientState.getChildren().get(1))).setEditable(true);

					((TextField) (patientEmail.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientEmail.getChildren().get(1))).setEditable(true);

					((TextField) (patientPhoneNumber.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (patientPhoneNumber.getChildren().get(1))).setEditable(true);

					((TextField) (pharmacyName.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (pharmacyName.getChildren().get(1))).setEditable(true);

					((TextField) (pharmacyAddress.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (pharmacyAddress.getChildren().get(1))).setEditable(true);

					((TextField) (pharmacyCity.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (pharmacyCity.getChildren().get(1))).setEditable(true);

					((TextField) (pharmacyZipCode.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (pharmacyZipCode.getChildren().get(1))).setEditable(true);

					((TextField) (pharmacyState.getChildren().get(1))).setStyle("-fx-text-fill: #000000");
					((TextField) (pharmacyState.getChildren().get(1))).setEditable(true);

					editPersonalInfoButton.setStyle("-fx-background-color: #F1B6AC;");

				} else {
					editPersonalInfoButton.setText("Edit");
					editPersonalInfoButton.setStyle("-fx-text-fill: #000000;");

					((TextField) (patientAddress.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientAddress.getChildren().get(1))).setEditable(false);

					((TextField) (patientCity.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientCity.getChildren().get(1))).setEditable(false);

					((TextField) (patientZipCode.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientZipCode.getChildren().get(1))).setEditable(false);

					((TextField) (patientState.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientState.getChildren().get(1))).setEditable(false);

					((TextField) (patientEmail.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientEmail.getChildren().get(1))).setEditable(false);

					((TextField) (patientPhoneNumber.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (patientPhoneNumber.getChildren().get(1))).setEditable(false);

					((TextField) (pharmacyName.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (pharmacyName.getChildren().get(1))).setEditable(false);

					((TextField) (pharmacyAddress.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (pharmacyAddress.getChildren().get(1))).setEditable(false);

					((TextField) (pharmacyCity.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (pharmacyCity.getChildren().get(1))).setEditable(false);

					((TextField) (pharmacyZipCode.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (pharmacyZipCode.getChildren().get(1))).setEditable(false);

					((TextField) (pharmacyState.getChildren().get(1))).setStyle("-fx-text-fill: #aaaaaa");
					((TextField) (pharmacyState.getChildren().get(1))).setEditable(false);

					editPersonalInfoButton.setStyle("-fx-background-color: #A2E3C4;");

					patientInfoContents.set(5, ((TextField) (patientAddress.getChildren().get(1))).getText());
					patientInfoContents.set(6, ((TextField) (patientCity.getChildren().get(1))).getText());
					patientInfoContents.set(7, ((TextField) (patientZipCode.getChildren().get(1))).getText());
					patientInfoContents.set(8, ((TextField) (patientState.getChildren().get(1))).getText());
					patientInfoContents.set(9, ((TextField) (patientEmail.getChildren().get(1))).getText());
					patientInfoContents.set(10, ((TextField) (patientPhoneNumber.getChildren().get(1))).getText());
					patientInfoContents.set(11, ((TextField) (pharmacyName.getChildren().get(1))).getText());
					patientInfoContents.set(12, ((TextField) (pharmacyAddress.getChildren().get(1))).getText());
					patientInfoContents.set(13, ((TextField) (pharmacyCity.getChildren().get(1))).getText());
					patientInfoContents.set(14, ((TextField) (pharmacyZipCode.getChildren().get(1))).getText());
					patientInfoContents.set(15, ((TextField) (pharmacyState.getChildren().get(1))).getText());

					String patientinfoFileName = patientID + "_Personal_Info.txt";

					try {

						File patientInfoFile = new File(patientinfoFileName);
						patientInfoFile.delete();
						FileWriter fw = new FileWriter(patientInfoFile, true);

						for (int i = 0; i < 16; i++) {
							fw.write(patientInfoContents.get(i) + "\n");
						}

						fw.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		});

		// --------Toggle Buttons to show appointment information-----------------
		// On toggle on: Creates text field and populates it using strings from
		// patientAptContents. patientAptContents gets contents from .txt file.
		// On toggle off: Deletes text field. Changes button and text style.
		// SUPPORTS 5 ENTRIES, NEEDS REFACTOR(GOOD) OR HARD CODING MORE BUTTON ACTION
		// HANDLERS(BAD) OR FIGURE OUT HOW TOHANDLE DIFFERENT BUTTON PRESSES
		// USING SAME CODE.
		((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren().get(4)))
				.setOnAction(new EventHandler<ActionEvent>() {
					private File file;

					@Override
					public void handle(ActionEvent arg0) {
						if (((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
								.get(4))).isSelected()) {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setText("Close");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #ffffff;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setPrefWidth(68);

							TextArea visitDetailsTextArea = new TextArea();
							visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
							visitDetailsTextArea.maxHeight(2);
							VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
							visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 2, 0));
							visitHistoryTextAreaHolder.minHeight(800);

							visitDetailsTextArea.appendText("Height (feet): " + patientAptContents.get(1) + "\n");
							visitDetailsTextArea.appendText("Height (inches): " + patientAptContents.get(2) + "\n");
							visitDetailsTextArea.appendText("Weight: " + patientAptContents.get(3) + "\n");
							visitDetailsTextArea.appendText("Blood Pressure: " + patientAptContents.get(4) + "\n");
							visitDetailsTextArea.appendText("New Allergies: " + patientAptContents.get(5) + "\n");
							visitDetailsTextArea.appendText("New Health Concerns: " + patientAptContents.get(6) + "\n");
							visitDetailsTextArea.appendText("Doctor's Notes: " + doctorsNotes + "\n");

							visitHistoryVBoxes.get(0).getChildren().add(visitHistoryTextAreaHolder);

							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #F1B6AC;");

						} else {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setText("Details");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #000000;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(0).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #A2E3C4;");

							visitHistoryVBoxes.get(0).getChildren().remove(1);
						}
					}
				});

		((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren().get(4)))
				.setOnAction(new EventHandler<ActionEvent>() {
					private File file;

					@Override
					public void handle(ActionEvent arg0) {
						if (((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
								.get(4))).isSelected()) {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setText("Close");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #ffffff;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setPrefWidth(68);

							TextArea visitDetailsTextArea = new TextArea();
							visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
							visitDetailsTextArea.maxHeight(2);
							VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
							visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 2, 0));
							visitHistoryTextAreaHolder.minHeight(800);

							visitDetailsTextArea.appendText("Height (feet): " + patientAptContents.get(8) + "\n");
							visitDetailsTextArea.appendText("Height (inches): " + patientAptContents.get(9) + "\n");
							visitDetailsTextArea.appendText("Weight: " + patientAptContents.get(10) + "\n");
							visitDetailsTextArea.appendText("Blood Pressure: " + patientAptContents.get(11) + "\n");
							visitDetailsTextArea.appendText("New Allergies: " + patientAptContents.get(12) + "\n");
							visitDetailsTextArea
									.appendText("New Health Concerns: " + patientAptContents.get(13) + "\n");
							visitDetailsTextArea.appendText("Doctor's Notes: " + doctorsNotes + "\n");

							visitHistoryVBoxes.get(1).getChildren().add(visitHistoryTextAreaHolder);

							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #F1B6AC;");

						} else {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setText("Details");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #000000;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(1).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #A2E3C4;");

							visitHistoryVBoxes.get(1).getChildren().remove(1);
						}
					}
				});

		((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren().get(4)))
				.setOnAction(new EventHandler<ActionEvent>() {
					private File file;

					@Override
					public void handle(ActionEvent arg0) {
						if (((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
								.get(4))).isSelected()) {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setText("Close");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #ffffff;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setPrefWidth(68);

							TextArea visitDetailsTextArea = new TextArea();
							visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
							visitDetailsTextArea.maxHeight(2);
							VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
							visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 2, 0));
							visitHistoryTextAreaHolder.minHeight(800);

							visitDetailsTextArea.appendText("Height (feet): " + patientAptContents.get(15) + "\n");
							visitDetailsTextArea.appendText("Height (inches): " + patientAptContents.get(16) + "\n");
							visitDetailsTextArea.appendText("Weight: " + patientAptContents.get(17) + "\n");
							visitDetailsTextArea.appendText("Blood Pressure: " + patientAptContents.get(18) + "\n");
							visitDetailsTextArea.appendText("New Allergies: " + patientAptContents.get(19) + "\n");
							visitDetailsTextArea
									.appendText("New Health Concerns: " + patientAptContents.get(20) + "\n");
							visitDetailsTextArea.appendText("Doctor's Notes: " + doctorsNotes + "\n");

							visitHistoryVBoxes.get(2).getChildren().add(visitHistoryTextAreaHolder);

							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #F1B6AC;");

						} else {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setText("Details");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #000000;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(2).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #A2E3C4;");

							visitHistoryVBoxes.get(2).getChildren().remove(1);
						}
					}
				});

		((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren().get(4)))
				.setOnAction(new EventHandler<ActionEvent>() {
					private File file;

					@Override
					public void handle(ActionEvent arg0) {
						if (((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
								.get(4))).isSelected()) {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setText("Close");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #ffffff;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setPrefWidth(68);

							TextArea visitDetailsTextArea = new TextArea();
							visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
							visitDetailsTextArea.maxHeight(2);
							VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
							visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 2, 0));
							visitHistoryTextAreaHolder.minHeight(800);

							visitDetailsTextArea.appendText("Height (feet): " + patientAptContents.get(22) + "\n");
							visitDetailsTextArea.appendText("Height (inches): " + patientAptContents.get(23) + "\n");
							visitDetailsTextArea.appendText("Weight: " + patientAptContents.get(24) + "\n");
							visitDetailsTextArea.appendText("Blood Pressure: " + patientAptContents.get(25) + "\n");
							visitDetailsTextArea.appendText("New Allergies: " + patientAptContents.get(26) + "\n");
							visitDetailsTextArea
									.appendText("New Health Concerns: " + patientAptContents.get(27) + "\n");
							visitDetailsTextArea.appendText("Doctor's Notes: " + doctorsNotes + "\n");

							visitHistoryVBoxes.get(3).getChildren().add(visitHistoryTextAreaHolder);

							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #F1B6AC;");

						} else {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setText("Details");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #000000;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(3).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #A2E3C4;");

							visitHistoryVBoxes.get(3).getChildren().remove(1);
						}
					}
				});

		((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren().get(4)))
				.setOnAction(new EventHandler<ActionEvent>() {
					private File file;

					@Override
					public void handle(ActionEvent arg0) {
						if (((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
								.get(4))).isSelected()) {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setText("Close");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #ffffff;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setPrefWidth(68);

							TextArea visitDetailsTextArea = new TextArea();
							visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
							visitDetailsTextArea.maxHeight(2);
							VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
							visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 2, 0));
							visitHistoryTextAreaHolder.minHeight(800);

							visitDetailsTextArea.appendText("Height (feet): " + patientAptContents.get(29) + "\n");
							visitDetailsTextArea.appendText("Height (inches): " + patientAptContents.get(30) + "\n");
							visitDetailsTextArea.appendText("Weight: " + patientAptContents.get(31) + "\n");
							visitDetailsTextArea.appendText("Blood Pressure: " + patientAptContents.get(32) + "\n");
							visitDetailsTextArea.appendText("New Allergies: " + patientAptContents.get(33) + "\n");
							visitDetailsTextArea
									.appendText("New Health Concerns: " + patientAptContents.get(34) + "\n");
							visitDetailsTextArea.appendText("Doctor's Notes: " + doctorsNotes + "\n");

							visitHistoryVBoxes.get(4).getChildren().add(visitHistoryTextAreaHolder);

							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #F1B6AC;");

						} else {
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setText("Details");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-text-fill: #000000;");
							((ToggleButton) (((HBox) (visitHistoryVBoxes.get(4).getChildren().get(0))).getChildren()
									.get(4))).setStyle("-fx-background-color: #A2E3C4;");

							visitHistoryVBoxes.get(4).getChildren().remove(1);
						}
					}
				});

		return mainLayout;
	}

	// Creates the labels and text fields for patient information.
	static HBox labelTextBoxMaker(String dataName) {
		final int PADDING = 2;
		final String FONT = "Arial";
		final int FONT_SIZE = 15;
		final int LABEL_PREF_HEIGHT = 30;
		final int LABEL_PREF_WIDTH = 150;
		final int TF_PREF_HEIGHT = 30;
		final int TF_PREF_WIDTH = 350;

		Label descriptionLabel = new Label(dataName);
		descriptionLabel.setFont(new Font(FONT, FONT_SIZE));
		descriptionLabel.setStyle("-fx-background-color: #f0f7f4;");
		descriptionLabel.setPrefHeight(LABEL_PREF_HEIGHT);
		descriptionLabel.setPrefWidth(LABEL_PREF_WIDTH);
		descriptionLabel.setPadding(new Insets(PADDING, PADDING, PADDING, 2 * PADDING));
		descriptionLabel.setAlignment(Pos.CENTER_LEFT);

		TextField dataField = new TextField();
		dataField.setFont(new Font(FONT, FONT_SIZE));
		dataField.setText(patientInfoContents.get(++patientInfoCountIndex));
		dataField.setStyle("-fx-text-fill: aaaaaa");
		dataField.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		dataField.setPrefHeight(TF_PREF_HEIGHT);
		dataField.setPrefWidth(TF_PREF_WIDTH);
		dataField.setAlignment(Pos.CENTER_LEFT);
		dataField.setEditable(false);

		HBox inputFormHBox = new HBox(descriptionLabel, dataField);
		inputFormHBox.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));

		return inputFormHBox;
	}

	//// Creates the title labels for patient information.
	static Label labelMaker(String dataName, int type) {
		final int PADDING = 2;
		final String FONT = "Arial";
		final int FONT_SIZE = 15;
		final int LABEL_PREF_HEIGHT = 28;
		final int LABEL_PREF_WIDTH = 500;

		Label descriptionLabel = new Label(dataName);
		descriptionLabel.setFont(new Font(FONT, FONT_SIZE));
		descriptionLabel.setTextFill(Color.WHITE);
		descriptionLabel.setStyle("-fx-background-color: #3c493f;");
		descriptionLabel.setPrefHeight(LABEL_PREF_HEIGHT);
		descriptionLabel.setPrefWidth(LABEL_PREF_WIDTH);
		descriptionLabel.setPadding(new Insets(PADDING, PADDING, PADDING, 2 * PADDING));
		descriptionLabel.setAlignment(Pos.CENTER_LEFT);

		if (type == 2) {
			descriptionLabel.setTextFill(Color.BLACK);
			descriptionLabel.setStyle("-fx-background-color: #869389 ;");
		}

		return descriptionLabel;
	}

	// Creates the labels and toggle button for the appointment list part of UI.
	static ArrayList<VBox> visitHisotryHBoxMaker(int count) {
		ArrayList<VBox> newArrayListVBoxes = new ArrayList<VBox>();
		int appointmentCounter = appointmentCount;
		int dateIndex = 0;

		for (int i = 1; i <= count; i++) {
			Label dateLabel = new Label("Date: ");
			dateLabel.setFont(new Font("Arial", 15));
			dateLabel.setTextFill(Color.GRAY);
			dateLabel.setStyle("-fx-background-color: #d8dedb;");
			dateLabel.setPrefHeight(28);
			dateLabel.setPrefWidth(80);
			dateLabel.setPadding(new Insets(2, 2, 2, 4));
			dateLabel.setAlignment(Pos.CENTER_LEFT);
			VBox dateLabelHolder = new VBox(dateLabel);
			dateLabelHolder.setPadding(new Insets(0, 2, 0, 0));

			Label dateDataLabel = new Label("");
			dateDataLabel.setFont(new Font("Arial", 15));
			dateDataLabel.setTextFill(Color.BLACK);
			dateDataLabel.setStyle("-fx-background-color: #d8dedb;");
			dateDataLabel.setPrefHeight(28);
			dateDataLabel.setPrefWidth(120);
			dateDataLabel.setPadding(new Insets(2, 2, 2, 4));
			dateDataLabel.setAlignment(Pos.CENTER_LEFT);
			VBox dateDataLabelHolder = new VBox(dateDataLabel);
			dateDataLabelHolder.setPadding(new Insets(0, 2, 0, 0));

			Label doctorLabel = new Label("Doctor: ");
			doctorLabel.setFont(new Font("Arial", 15));
			doctorLabel.setTextFill(Color.GRAY);
			doctorLabel.setStyle("-fx-background-color: #d8dedb;");
			doctorLabel.setPrefHeight(28);
			doctorLabel.setPrefWidth(80);
			doctorLabel.setPadding(new Insets(2, 2, 2, 4));
			doctorLabel.setAlignment(Pos.CENTER_LEFT);
			VBox doctorLabelHolder = new VBox(doctorLabel);
			doctorLabelHolder.setPadding(new Insets(0, 2, 0, 0));

			Label doctorNameLabel = new Label("");
			doctorNameLabel.setFont(new Font("Arial", 15));
			doctorNameLabel.setTextFill(Color.BLACK);
			doctorNameLabel.setStyle("-fx-background-color: #d8dedb;");
			doctorNameLabel.setPrefHeight(28);
			doctorNameLabel.setPrefWidth(120);
			doctorNameLabel.setPadding(new Insets(2, 2, 2, 4));
			doctorNameLabel.setAlignment(Pos.CENTER_LEFT);
			VBox doctorNameLabelHolder = new VBox(doctorNameLabel);

			ToggleButton showDetailsToggleButton = new ToggleButton("Details");
			showDetailsToggleButton.setDisable(true);
			showDetailsToggleButton.setStyle("-fx-text-fill: #aaaaaa;");
			showDetailsToggleButton.setStyle("-fx-background-color: #888888;");
			showDetailsToggleButton.setPrefHeight(27);
			showDetailsToggleButton.setPrefWidth(68);

			HBox visitHistoryHeadHBox = new HBox(dateLabelHolder, dateDataLabelHolder, doctorLabelHolder,
					doctorNameLabelHolder, showDetailsToggleButton);
			visitHistoryHeadHBox.setMaxWidth(500);
			VBox visitHistoryVBox = new VBox(visitHistoryHeadHBox);
			visitHistoryVBox.setPadding(new Insets(0, 0, 2, 0));

			if (appointmentCounter > 0) {
				dateLabel.setStyle("-fx-background-color: #f0f7f4;");
				dateDataLabel.setStyle("-fx-background-color: #f0f7f4;");
				doctorLabel.setStyle("-fx-background-color: #f0f7f4;");
				doctorNameLabel.setStyle("-fx-background-color: #f0f7f4;");

				dateLabel.setTextFill(Color.BLACK);
				dateDataLabel.setText(patientAptContents.get(dateIndex));
				dateIndex = dateIndex + 7;

				doctorLabel.setTextFill(Color.BLACK);
				doctorNameLabel.setText(doctorName); // Place Holder

				showDetailsToggleButton.setDisable(false);
				showDetailsToggleButton.setStyle("-fx-text-fill: #000000;");
				showDetailsToggleButton.setStyle("-fx-background-color: #A2E3C4;");
			}

			newArrayListVBoxes.add(visitHistoryVBox);
			--appointmentCounter;
		}

		return newArrayListVBoxes;
	}
}
