package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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

import javafx.scene.layout.Pane;

public class PatientUI {

	static ArrayList<String> patientInfoContents = new ArrayList<>();
	static int countIndex = 0;

	public static Pane patientUI(Stage primaryStage, String patientID) {
		// patientID = "jdoe2010"; // Temporary
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

		// -------------Buttons------------------------------------------------------------------------------------------------------------------------------------
		Button logoutButton = new Button("Log Out");
		logoutButton.setStyle("-fx-background-color: #A2E3C4;");

		ToggleButton editPersonalInfoButton = new ToggleButton("Edit");
		editPersonalInfoButton.setStyle("-fx-background-color: #A2E3C4;");
		editPersonalInfoButton.setPrefWidth(60);

		Button showDetailsButton = new Button("Show Details");
		showDetailsButton.setStyle("-fx-background-color: #A2E3C4;");
		showDetailsButton.setPrefHeight(29);

		// -------------Patient Info Form
		// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		TextArea greetingMessage = new TextArea();
		greetingMessage.setText("Welcome, " + patientInfoContents.get(1) + " " + patientInfoContents.get(2));
		greetingMessage.setPadding(new Insets(2, 2, 2, 2));
		VBox greetingMessageHolder = new VBox(greetingMessage);
		greetingMessageHolder.setPadding(new Insets(2, 2, 10, 0));

		Label patientInfoTitle = labelMaker("Your Information: ", 1);
		patientInfoTitle.setPrefWidth(440);
		HBox patientInfoTitleHolder = new HBox(patientInfoTitle, editPersonalInfoButton);
		patientInfoTitleHolder.setPadding(new Insets(2, 0, 2, 2));

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
		VBox messageCenterHolder = new VBox(messageCenterTitle);
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
		VBox messagePreviewHolder = new VBox(messagePreview);
		messagePreviewHolder.setPadding(new Insets(2, 0, 10, 0));

		VBox messageCenterVBox = new VBox(messageCenterHolder, messageAddressBox, messagePreviewHolder);

		// -------------Visit History
		// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		Label visitHistoryTitle = labelMaker("Visit History: ", 1);
		VBox visitHistoryHolder = new VBox(visitHistoryTitle);
		visitHistoryHolder.setPadding(new Insets(0, 0, 2, 0));

		Label dateLabel = new Label("Date: ");
		dateLabel.setFont(new Font("Arial", 15));
		dateLabel.setTextFill(Color.BLACK);
		dateLabel.setStyle("-fx-background-color: #f0f7f4;");
		dateLabel.setPrefHeight(30);
		dateLabel.setPrefWidth(80);
		dateLabel.setPadding(new Insets(2, 2, 2, 4));
		dateLabel.setAlignment(Pos.CENTER_LEFT);
		VBox dateLabelHolder = new VBox(dateLabel);
		dateLabelHolder.setPadding(new Insets(0, 2, 0, 0));

		Label dateDataLabel = new Label("10/10/2023");
		dateDataLabel.setFont(new Font("Arial", 15));
		dateDataLabel.setTextFill(Color.BLACK);
		dateDataLabel.setStyle("-fx-background-color: #f0f7f4;");
		dateDataLabel.setPrefHeight(30);
		dateDataLabel.setPrefWidth(120);
		dateDataLabel.setPadding(new Insets(2, 2, 2, 4));
		dateDataLabel.setAlignment(Pos.CENTER_LEFT);
		VBox dateDataLabelHolder = new VBox(dateDataLabel);
		dateDataLabelHolder.setPadding(new Insets(0, 2, 0, 0));

		Label doctorLabel = new Label("Doctor: "); // Place Holder
		doctorLabel.setFont(new Font("Arial", 15));
		doctorLabel.setTextFill(Color.BLACK);
		doctorLabel.setStyle("-fx-background-color: #f0f7f4;");
		doctorLabel.setPrefHeight(30);
		doctorLabel.setPrefWidth(80);
		doctorLabel.setPadding(new Insets(2, 2, 2, 4));
		doctorLabel.setAlignment(Pos.CENTER_LEFT);
		VBox doctorLabelHolder = new VBox(doctorLabel);
		doctorLabelHolder.setPadding(new Insets(0, 2, 0, 0));

		Label doctorNameLabel = new Label("Jane Doe");
		doctorNameLabel.setFont(new Font("Arial", 15));
		doctorNameLabel.setTextFill(Color.BLACK);
		doctorNameLabel.setStyle("-fx-background-color: #f0f7f4;");
		doctorNameLabel.setPrefHeight(30);
		doctorNameLabel.setPrefWidth(120);
		doctorNameLabel.setPadding(new Insets(2, 2, 2, 4));
		doctorNameLabel.setAlignment(Pos.CENTER_LEFT);
		VBox doctorNameLabelHolder = new VBox(doctorNameLabel);

		HBox visitHistoryHBox = new HBox(dateLabelHolder, dateDataLabelHolder, doctorLabelHolder, doctorNameLabelHolder,
				showDetailsButton);
		visitHistoryHBox.setMaxWidth(500);

		TextArea visitDetailsTextArea = new TextArea();
		visitDetailsTextArea.setPadding(new Insets(2, 2, 2, 2));
		visitDetailsTextArea.minHeight(800);
		VBox visitHistoryTextAreaHolder = new VBox(visitDetailsTextArea);
		visitHistoryTextAreaHolder.setPadding(new Insets(2, 0, 10, 0));
		visitHistoryTextAreaHolder.minHeight(800);

		VBox visitHistoryVBox = new VBox(visitHistoryHolder, visitHistoryHBox, visitHistoryTextAreaHolder);

		VBox rightBody = new VBox(messageCenterVBox, visitHistoryVBox);
		rightBody.setPadding(new Insets(0, 0, 0, 10));
		rightBody.setMaxWidth(500);
		HBox mainBody = new HBox(leftBody, rightBody);

		mainBody.setPadding(new Insets(10, 20, 20, 20));

		// ----- Main Header----------------------------------------------------
		Label mainHeaderLabel = new Label("Patient Portal");
		mainHeaderLabel.setFont(new Font("Arial", 15));
		mainHeaderLabel.setTextFill(Color.WHITE);
		mainHeaderLabel.setStyle("-fx-background-color: #3c493f;");
		mainHeaderLabel.setPrefHeight(30);
		mainHeaderLabel.setPrefWidth(990);
		mainHeaderLabel.setPadding(new Insets(2, 2, 2, 2));
		mainHeaderLabel.setAlignment(Pos.CENTER_LEFT);
		HBox mainHeaderLabelHolder = new HBox(mainHeaderLabel, logoutButton);

		// -------------------Merging Header and Body----------------------------
		VBox mainVBox = new VBox(mainHeaderLabelHolder, mainBody);

		mainLayout.getChildren().addAll(mainVBox);

		// --------Button Actions-------------
		logoutButton.setOnAction(e -> {
			Platform.exit();
		});

		editPersonalInfoButton.setOnAction(new EventHandler<ActionEvent>() {

			private File file;

			@Override
			public void handle(ActionEvent arg0) {
				if (editPersonalInfoButton.isSelected()) {
					editPersonalInfoButton.setText("Done");
					editPersonalInfoButton.setStyle("-fx-background-color: #FFCC5C;");
					editPersonalInfoButton.setStyle("-fx-text-fill: #000000;");

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

					editPersonalInfoButton.setStyle("-fx-background-color: #FFCC5C;");

				} else {
					editPersonalInfoButton.setText("Edit");
					editPersonalInfoButton.setStyle("-fx-background-color: #A2E3C4;");
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

						fw.write(patientInfoContents.get(0) + "\n");
						fw.write(patientInfoContents.get(1) + "\n");
						fw.write(patientInfoContents.get(2) + "\n");
						fw.write(patientInfoContents.get(3) + "\n");
						fw.write(patientInfoContents.get(4) + "\n");
						fw.write(patientInfoContents.get(5) + "\n");
						fw.write(patientInfoContents.get(6) + "\n");
						fw.write(patientInfoContents.get(7) + "\n");
						fw.write(patientInfoContents.get(8) + "\n");
						fw.write(patientInfoContents.get(9) + "\n");
						fw.write(patientInfoContents.get(10) + "\n");
						fw.write(patientInfoContents.get(11) + "\n");
						fw.write(patientInfoContents.get(12) + "\n");
						fw.write(patientInfoContents.get(13) + "\n");
						fw.write(patientInfoContents.get(14) + "\n");
						fw.write(patientInfoContents.get(15) + "\n");
						fw.close();

					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		});

		return mainLayout;
	}

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
		descriptionLabel.setPadding(new Insets(PADDING, PADDING, PADDING, 4));
		descriptionLabel.setAlignment(Pos.CENTER_LEFT);

		TextField dataField = new TextField();
		dataField.setText(patientInfoContents.get(++countIndex));
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

	static Label labelMaker(String dataName, int type) {
		final int PADDING = 2;
		final String FONT = "Arial";
		final int FONT_SIZE = 15;
		final int LABEL_PREF_HEIGHT = 30;
		final int LABEL_PREF_WIDTH = 500;

		Label descriptionLabel = new Label(dataName);
		descriptionLabel.setFont(new Font(FONT, FONT_SIZE));
		descriptionLabel.setTextFill(Color.WHITE);
		descriptionLabel.setStyle("-fx-background-color: #3c493f;");
		descriptionLabel.setPrefHeight(LABEL_PREF_HEIGHT);
		descriptionLabel.setPrefWidth(LABEL_PREF_WIDTH);
		descriptionLabel.setPadding(new Insets(PADDING, PADDING, PADDING, 4));
		descriptionLabel.setAlignment(Pos.CENTER_LEFT);

		if (type == 2) {
			descriptionLabel.setTextFill(Color.BLACK);
			descriptionLabel.setStyle("-fx-background-color: #869389 ;");
		}

		return descriptionLabel;
	}

}
