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
        VBox root = new VBox(40); // Main container with 40 pixels of spacing between nodes
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 20, 20, 20)); //top, right, left, bottom
        //root.setSpacing(5);

        // Title
        Text title = new Text("SunCare Connect");
        title.setFont(new Font(80));

        // Subtitle
        Text subtitle = new Text("Login Page");
        subtitle.setFont(new Font(35));

        VBox titleSubtitleBox = new VBox(5, title, subtitle); // Group title and subtitle with 5 pixels of spacing
        titleSubtitleBox.setAlignment(Pos.CENTER);


        // Logo
        Image logo = new Image("file:/Users/skyy/IdeaProjects/SunCareConnect/src/main/java/com/example/suncareconnect/logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(110);  // set width
        logoView.setFitHeight(120); // set height

        // Radio Buttons
        ToggleGroup group = new ToggleGroup();

        RadioButton rbPatient = new RadioButton("Patient");
        rbPatient.setToggleGroup(group);
        rbPatient.setFont(new Font(35));
        rbPatient.setScaleX(0.6); // Scaling in the X direction
        rbPatient.setScaleY(0.6); // Scaling in the Y direction

        RadioButton rbRN = new RadioButton("RN");
        rbRN.setToggleGroup(group);
        rbRN.setFont(new Font(35));
        rbRN.setScaleX(0.6); // Scaling in the X direction
        rbRN.setScaleY(0.6); // Scaling in the Y direction

        RadioButton rbProvider = new RadioButton("Provider");
        rbProvider.setToggleGroup(group);
        rbProvider.setFont(new Font(35));
        rbProvider.setScaleX(0.6); // Scaling in the X direction
        rbProvider.setScaleY(0.6); // Scaling in the Y direction

        //Nurse Radio Button event handler
       /* rbRN.setOnAction(event -> {
            if (rbRN.isSelected()) {
                Pane nurseUIPane = NurseUI.nurseUI(); // Get the Nurse UI pane as a Pane
                primaryStage.getScene().setRoot(nurseUIPane); // Set the new root for the scene
                primaryStage.sizeToScene(); // Adjust the stage size to fit the new scene size
                primaryStage.setTitle("SunCare Connect --------- Welcome Nurse Joy"); // Set the title
            }
        }); */


        HBox radioButtons = new HBox(30, rbPatient, rbRN, rbProvider); // HBox for horizontal alignment with increased spacing
        radioButtons.setAlignment(Pos.CENTER);

        // Login ID and TextField
        Text loginText = new Text("Login ID:   ");
        loginText.setFont(new Font(21));
        TextField loginField = new TextField();
        loginField.setPrefWidth(300);

        HBox loginBox = new HBox(0, loginText, loginField); // HBox for horizontal alignment with 0 pixels of spacing
        loginBox.setAlignment(Pos.CENTER);

        // Login Button
        Button loginButton = new Button("LOGIN");


        //LOGIN button EVENT HANDLER
        loginButton.setOnAction(event -> {
            // Get the selected radio button in the ToggleGroup
            RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
            String selectedUserType = selectedRadioButton.getText();

            // Get the Login ID from the text field
            String enteredLoginID = loginField.getText();

            // Check if the Login ID is in the text file and the selected radio button is for a nurse
            if (verifyUser(enteredLoginID, selectedUserType)) {
                // If it's a nurse, switch to the NurseUI page
                if ("RN".equals(selectedUserType)) {
                    Pane nurseUIPane = NurseUI.nurseUI();
                    primaryStage.getScene().setRoot(nurseUIPane);
                    primaryStage.sizeToScene();
                    primaryStage.setTitle("SunCare Connect --------- Welcome Nurse Joy");
                }
                // Add more conditions here if there are other user types to handle
            } else {
                // Show an alert or a message indicating the login failed
                showAlert("Login Failed", "The entered ID is incorrect or does not have access.");
            }

        });

        // Add all elements to the root container
        root.getChildren().addAll(titleSubtitleBox, logoView, radioButtons, loginBox, loginButton);

        //space between logo and radio buttons
        VBox.setMargin(radioButtons, new Insets(10, 0, 0, 0));  // top, right, bottom, left

        //space between subtitle and logo
        VBox.setMargin(logoView, new Insets(20, 0, 0, 0));  // top, right, bottom, left
        VBox.setMargin(titleSubtitleBox, new Insets(50, 0, 0, 0));  // top, right, bottom, left


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
        // Implement the logic to read from the text file and check for the ID
        // For this example, let's assume you have a text file with each line as "userID,userType"
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