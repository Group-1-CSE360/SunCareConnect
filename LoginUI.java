package com.example.suncareconnect;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class LoginUI extends Application {

    String userID;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(40); 
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20)); //top, right, left, bottom

        // Title
        Text title = new Text("SunCare Connect");
        title.setFont(new Font(80));

        // Subtitle
        Text subtitle = new Text("Login Page");
        subtitle.setFont(new Font(35));

        VBox titleSubtitleBox = new VBox(5, title, subtitle);
        titleSubtitleBox.setAlignment(Pos.CENTER);


        // Logo
        Image logo = new Image("file:/Users/skyy/IdeaProjects/SunCareConnect/src/main/java/com/example/suncareconnect/logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(110);
        logoView.setFitHeight(120);

        // Radio Buttons
        ToggleGroup group = new ToggleGroup();

        RadioButton rbPatient = new RadioButton("Patient");
        rbPatient.setToggleGroup(group);
        rbPatient.setFont(new Font(35));
        rbPatient.setScaleX(0.6);
        rbPatient.setScaleY(0.6);

        RadioButton rbNurse = new RadioButton("Nurse");
        rbNurse.setToggleGroup(group);
        rbNurse.setFont(new Font(35));
        rbNurse.setScaleX(0.6);
        rbNurse.setScaleY(0.6);

        RadioButton rbDoctor = new RadioButton("Doctor");
        rbDoctor.setToggleGroup(group);
        rbDoctor.setFont(new Font(35));
        rbDoctor.setScaleX(0.6);
        rbDoctor.setScaleY(0.6);


        HBox radioButtons = new HBox(30, rbPatient, rbNurse, rbDoctor); 
        radioButtons.setAlignment(Pos.CENTER);

        // Login ID and TextField
        Text loginText = new Text("Login ID:   ");
        loginText.setFont(new Font(21));
        TextField loginField = new TextField();
        loginField.setPrefWidth(300);

        HBox loginBox = new HBox(0, loginText, loginField);
        loginBox.setAlignment(Pos.CENTER);

        // Login Button
        Button loginButton = new Button("LOGIN");


        //LOGIN button EVENT HANDLER
        loginButton.setOnAction(event -> {
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            String selectedUserType = selectedRadioButton.getText();

            // Get the Login ID from the text field
            String enteredLoginID = loginField.getText();

            // Check if the Login ID is in the text file and the selected radio button is for a nurse
            if (verifyUser(enteredLoginID, selectedUserType)) {
                if ("Nurse".equals(selectedUserType)) {
                    Pane nurseUIPane = NurseUI.nurseUI();
                    primaryStage.getScene().setRoot(nurseUIPane);
                    primaryStage.sizeToScene();
                    primaryStage.setTitle("SunCare Connect --------- Welcome Nurse Joy");
                }
                else if ("Doctor".equals(selectedUserType)){
                    Pane doctorUIPane = DoctorUI.doctorUI(primaryStage);
                    primaryStage.getScene().setRoot(doctorUIPane);
                    primaryStage.sizeToScene();
                    primaryStage.setTitle("SunCare Connect --------- Welcome Dr. Oz");
                }
                else if ("Patient".equals(selectedUserType)){
                    Pane patientUIPane = PatientUI.patientUI(primaryStage, userID);
                    primaryStage.getScene().setRoot(patientUIPane);
                    primaryStage.sizeToScene();
                    primaryStage.setTitle("SunCare Connect --------- Patient Portal");
                }

            }


            else {
                // Show an alert or a message indicating the login failed
                showAlert("Login Failed", "The entered ID is incorrect or does not have access.");
            }

        });

        root.getChildren().addAll(titleSubtitleBox, logoView, radioButtons, loginBox, loginButton);

        //space between logo and radio buttons
        VBox.setMargin(radioButtons, new Insets(10, 0, 0, 0));

        //space between subtitle and logo
        VBox.setMargin(logoView, new Insets(20, 0, 0, 0));
        VBox.setMargin(titleSubtitleBox, new Insets(50, 0, 0, 0));


        Scene scene = new Scene(root, 1050, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to SunCare Connect");
        primaryStage.show();

        //background color
        root.setStyle("-fx-background-color: #B3BFB8;");

    }


    //Setting up for backend
   boolean verifyUser(String userID, int userType){
       return true;
   }

   void loadUI(String userID, int userType) {

   }
    // A method to verify if the user is in the text file
    boolean verifyUser(String loginID, String userType) {

        File file = new File("/Users/skyy/IdeaProjects/SunCareConnect/src/main/java/com/example/suncareconnect/users.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] userInfo = scanner.nextLine().split(",");
                if (userInfo[0].equals(loginID) && userInfo[1].equals(userType)) {
                    return true; // ID and user type match
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // Handle the case where the file doesn't exist
        }
        return false; // ID was not found or didn't match
    }

    // A method to show an alert dialog
    void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



}
