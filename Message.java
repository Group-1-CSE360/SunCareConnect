package com.example.messaging;
import javafx.scene.layout.Priority;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import java.io.IOException;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.*;
import javafx.scene.Node;
import java.util.Dictionary;
import java.util.Hashtable;
import javafx.application.Platform;
public class Message extends Application{
    String current_user = "";
//    protected int message_id = 0;
    String dir = System.getProperty("user.dir");
    HBox button_container = new HBox(5);
    Button reply_button = add_button("Reply", "A2E3C4", "3C493F");
    Button compose_button = add_button("New", "007BFF", "FFFFFF");
    Button discard_button = add_button("Discard", "F1B6AC", "FFFFFF");
    Button send_button = add_button("Send", "A2E3C4", "3C493F");
    Button logout_button = add_button("Log Out", "A2E3C4", "3C493F" );
    Button login_button = add_button("Log In", "A2E3C4", "3C493F" );

    TextField comp_msg_subject = new TextField();
    TextField comp_msg_to = new TextField();
    TextField comp_msg = new TextField();
    Label comp_msg_subject_label = new Label("Subject: ");
    Label comp_msg_to_label = new Label("To:          ");
    Label comp_msg_line = new Label("Message");
    Label read_msg_subject_label = new Label("Subject: ");
    Label read_msg_date_label = new Label("Date: ");
    Label read_msg_from_label = new Label("From: ");
    Label read_msg_contents = new Label("Message: ");
    Label login_title = new Label("Login as: ");
    Label discarded_msg = new Label("Message Discarded Successfully");
    Label sent_msg = new Label("Message Sent Sucessfully!");
    TextArea read_msg_subject = new TextArea();
    TextArea login_entry = new TextArea();
    TextArea read_msg_from = new TextArea();
    TextArea read_msg_date = new TextArea();
    GridPane grid = add_grid();
    VBox placeholder_right = add_vbox("4B574E");
    VBox compose_message_container = setup_message_composer_scene();

    GridPane login_menu = new GridPane();
    GridPane reply_message_container = new GridPane();
    GridPane read_message_container = setup_message_reader_scene();
    protected ArrayList<HashMap> messages;
    protected ArrayList<String> msg_ids;
    //this serves as the entry point scene
    Scene entry = new Scene(grid, 640, 480);

    @Override
    public void start(Stage stage) throws Exception {
        //Make sure the message id is on the right starting point for any new messages
//        update_message_id(current_user);
        //Setup the button menu
        button_container.getChildren().addAll(compose_button, send_button, reply_button, discard_button, logout_button);
        button_container.setSpacing(5);
        button_container.setAlignment(Pos.CENTER);

        //Setup the UI Elements for entry screen into Mail
        grid.add(button_container, 1, 1);
        grid.add(login_menu, 0, 1);
        grid.add(compose_message_container, 1, 0);
        grid.add(read_message_container, 1, 0);
        grid.add(reply_message_container, 1, 0);
        grid.add(placeholder_right, 1, 0);

        //setup right placeholder UI elements for on entry
        placeholder_right.getChildren().addAll(discarded_msg, sent_msg);

        //login menu items setup
        login_entry.setPrefHeight(50);
        login_entry.setPrefWidth(200);
        login_menu.add(login_title, 0, 0);
        login_menu.add(login_entry, 1, 0);
        login_menu.add(login_button, 2, 0);

        //resize the composition menu textarea
        comp_msg.setPrefHeight(500);
        comp_msg.setPrefWidth(350);
        //Setup actions for buttons
        compose_button.setOnAction(e -> {
            scene_selection("Compose");
        });
        reply_button.setOnAction(e -> {
            reply_scene_setup();
            scene_selection("Reply");
        });
        discard_button.setOnAction(e -> {
            discard_message();
            scene_selection("Discarded");
        });
        send_button.setOnAction(e -> {
            String send_to_user = comp_msg_to.getText();
            send_message(send_to_user);
            scene_selection("Sent");
        });
        logout_button.setOnAction( e-> {
            Platform.exit();
        });
        login_button.setOnAction( e-> {
            current_user = get_current_user();
            messages = msgs_list();
            msg_ids = get_msg_ids(current_user);
            System.out.println("The current user is " + current_user);
            build_msg_list_dict();
            scene_selection("Logged In");
        });
        scene_selection("Entry");
        stage.setScene(entry);
        stage.show();
    }
    public String get_current_user() {
        String user = login_entry.getText();
        Boolean found = false;
        if (user == null) {
            return "";
        }
        String line;
        try {
            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/users.txt"));
            while ((line = mReader.readLine()) != null) {
                String[] words = line.split(",");
                System.out.println(words[0]);
                if (words[0].equals(user)) {
                    System.out.println("The found matching criteria is " + words[1]);
                    user = words[0];
                    System.out.println("The user var is set to " + user);
                    found = true;
                }
            }
        }   catch(IOException e) {
            e.printStackTrace();
        }
        if (!found) {
            login_entry.setText("Please enter a valid user ID");
        } else {
            login_entry.setText("Logged in as " + user);
        }
        return user;
    }
    public void scene_selection(String scene) {
        if (scene.equals("Read")) {
            compose_message_container.setVisible(false);
            placeholder_right.setVisible(false);
            read_message_container.setVisible(true);
            reply_message_container.setVisible(false);
            button_container.setVisible(true);
            reply_button.setDisable(false);
            send_button.setDisable(true);
            logout_button.setDisable(false);
            discard_button.setDisable(false);
            compose_button.setDisable(false);
        } else if (scene.equals("Compose")) {
            compose_message_container.setVisible(true);
            placeholder_right.setVisible(false);
            reply_message_container.setVisible(false);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            reply_button.setDisable(true);
            send_button.setDisable(false);
            logout_button.setDisable(false);
            discard_button.setDisable(false);
            compose_button.setDisable(true);
        } else if (scene.equals("Reply")) {
            compose_message_container.setVisible(true);
            placeholder_right.setVisible(false);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            reply_button.setDisable(true);
            send_button.setDisable(false);
            logout_button.setDisable(false);
            discard_button.setDisable(false);
            compose_button.setDisable(true);
        } else if (scene.equals("Discarded")) {
            reply_message_container.setVisible(false);
            compose_message_container.setVisible(false);
            placeholder_right.setVisible(true);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            sent_msg.setVisible(false);
            discarded_msg.setVisible(true);
            reply_button.setDisable(true);
            send_button.setDisable(true);
            logout_button.setDisable(false);
            discard_button.setDisable(true);
            compose_button.setDisable(false);
        } else if (scene.equals("Sent")) {
            reply_message_container.setVisible(false);
            compose_message_container.setVisible(false);
            placeholder_right.setVisible(true);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            sent_msg.setVisible(true);
            discarded_msg.setVisible(false);
            reply_button.setDisable(true);
            send_button.setDisable(true);
            logout_button.setDisable(false);
            discard_button.setDisable(true);
            compose_button.setDisable(false);
        } else if (scene.equals("Entry")) {
            reply_message_container.setVisible(false);
            compose_message_container.setVisible(false);
            placeholder_right.setVisible(true);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            sent_msg.setVisible(false);
            discarded_msg.setVisible(false);
            reply_button.setDisable(true);
            send_button.setDisable(true);
            logout_button.setDisable(false);
            discard_button.setDisable(true);
            compose_button.setDisable(true);
        }
        else if (scene.equals("Logged In")) {
            reply_message_container.setVisible(false);
            compose_message_container.setVisible(false);
            placeholder_right.setVisible(true);
            read_message_container.setVisible(false);
            button_container.setVisible(true);
            sent_msg.setVisible(false);
            discarded_msg.setVisible(false);
            reply_button.setDisable(true);
            send_button.setDisable(true);
            logout_button.setDisable(false);
            discard_button.setDisable(true);
            compose_button.setDisable(false);
        }
    }
    //try to keep button styling consistent by using a method
    public Button add_button(String btnText, String btnColor, String textColor) {
    Button btn = new Button(btnText);
    String style_str = "-fx-background-color: #" + btnColor + "; -fx-text-fill: #" + textColor +";";
    btn.setStyle(style_str);
    btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    btn.setAlignment(Pos.CENTER);
    HBox.setHgrow(btn, Priority.ALWAYS);
    VBox.setVgrow(btn, Priority.ALWAYS);
    return btn;
    }
    //try to keep all grid configuration in a separate method for readability
    public VBox setup_message_composer_scene() {
        VBox box = add_vbox("F0F7F4");
        HBox subject_row = add_hbox(new Node[] {comp_msg_subject_label, comp_msg_subject});
        HBox recipient_row = add_hbox(new Node[] {comp_msg_to_label, comp_msg_to});
        HBox messagetitle_row = add_hbox(new Node[] {comp_msg_line});
        HBox message_row = add_hbox(new Node[] {comp_msg});
        VBox.setVgrow(message_row, Priority.ALWAYS);
        box.getChildren().addAll(recipient_row,subject_row, messagetitle_row, message_row);
        return box;
    }

    public HBox add_hbox(Node[] items) {
        HBox box = new HBox(5);
        for (Node item : items) {
            box.getChildren().add(item);
        }
        return box;
    }
    public GridPane setup_message_reader_scene() {
        GridPane grid = new GridPane();
        grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        grid.setVgap(10); //vertical gap in pixels
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setStyle("-fx-background-color: #F0F7F4");
        grid.add(read_msg_from_label, 0, 0);
        grid.add(read_msg_subject_label, 0, 2);
        grid.add(read_msg_date_label, 0, 1);
        grid.add(read_msg_contents, 0, 4);
        return grid;
    }
    public GridPane add_grid() {
        GridPane grid = new GridPane();
        grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        grid.setVgap(10); //vertical gap in pixels
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setStyle("-fx-background-color: #3C493F;");
        ColumnConstraints col_const1 = new ColumnConstraints();
        ColumnConstraints col_const2 = new ColumnConstraints();
        col_const1.setPercentWidth(50.0);
        col_const2.setPercentWidth(50.0);
        grid.getColumnConstraints().add(col_const1);
        grid.getColumnConstraints().add(col_const2);
        RowConstraints row_const1 = new RowConstraints();
        RowConstraints row_const2 = new RowConstraints();
        row_const1.setPercentHeight(90.00);
        row_const1.setVgrow(Priority.ALWAYS);
        row_const1.setFillHeight(true);
        row_const1.setMaxHeight(Double.MAX_VALUE);
        grid.getRowConstraints().add(row_const1);
        row_const2.setPercentHeight(10.00);
        grid.getRowConstraints().add(row_const2);
        return grid;
    }
    //keep vbox style configurations separate
    public VBox add_vbox(String bg_color){
        String style_str = "-fx-background-color: #" + bg_color + ";";
        VBox vbox = new VBox(20);
        vbox.setStyle(style_str);
        return vbox;
    }
    public ArrayList<String> get_msg_ids(String user) {
        ArrayList<String> msg_id_list = new ArrayList<String>();
        String line;
        String newline;
        try {
            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + current_user + "_msgs.txt"));
            while ((line = mReader.readLine()) != null) {
                if (line.equals("###MESSAGEID###")) {
                    newline = mReader.readLine();

                    msg_id_list.add(newline);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg_id_list;
    }


    public HashMap<String, String> build_message_dict (String id) {
        String line;
        String newline;
        String contents = "";
        HashMap<String, String> msg = new HashMap<>();
        try {
            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + current_user + "_msgs.txt"));
            while ((line = mReader.readLine()) != null) {
                if (line.equals("###MESSAGEID###")) {
                    if ((line = mReader.readLine()).equals(id)) {
                        msg.put("Id", line);
                        line = mReader.readLine();

                        msg.put("From", mReader.readLine());
                        line = mReader.readLine();

                        msg.put("To", mReader.readLine());
                        line = mReader.readLine();

                        msg.put("Date", mReader.readLine());
                        line = mReader.readLine();
                        line = mReader.readLine();
                        msg.put("Subject", line);
                        line = mReader.readLine();
                        while ((line = mReader.readLine()) != null) {
                            if (line.equals("{{{{{ENDMESSAGE}}}}}")) {
                                break;
                            } else {
                                contents = contents + line;
                            }
                        }
                    }
                }
            }
        msg.put("Contents", contents);
        } catch(IOException e) {
                e.printStackTrace();
            }
            return msg;
        }

    public ArrayList<HashMap> msgs_list() {
        ArrayList<String> ids = get_msg_ids(current_user);
        int count = ids.size();
        ArrayList <HashMap> inbox = new ArrayList<>();
        for (String msg : ids ) {
            inbox.add(build_message_dict(msg));
        }
        return inbox;
    }
    public Integer update_message_id(String user) {
//        Integer id_count = get_msg_ids(user).size() + 1;
//        if (user.equals(current_user)) {
//            if (id_count <= get_msg_ids(user).size()) {
////                id_count = get_msg_ids(user).size() + 1;
//                id_count = get_msg_ids(user).size() + 1;
//            } else {
//
//            }
            return get_msg_ids(user).size() + 1;
        }
    public void send_message(String user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter( dir + "/" + user + "_msgs.txt", true))) {
            bw.write("###MESSAGEID###");
            bw.newLine();
            Integer msg_id = update_message_id(comp_msg_to.getText());
            bw.write(Integer.toString(msg_id));
            bw.newLine();
            bw.write("###FROM###");
            bw.newLine();
            bw.write(current_user);
            bw.newLine();
            bw.write("###TO###");
            bw.newLine();
            bw.write(comp_msg_to.getText());
            bw.newLine();
            bw.write("###DATE###");
            bw.newLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();
            bw.write(dtf.format(now));
            bw.newLine();
            bw.write("###SUBJECT###");
            bw.newLine();
            bw.write(comp_msg_subject.getText());
            bw.newLine();
            bw.write("###CONTENTS###");
            bw.newLine();
            bw.write(comp_msg.getText());
            bw.newLine();
            bw.write("{{{{{ENDMESSAGE}}}}}");
            bw.newLine();
            discard_message();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void discard_message() {
        comp_msg_subject.setText("");
        comp_msg_to.setText("");
        comp_msg.setText("");
    }
    public void build_msg_list_dict() {
        List<Button> inbox_buttons = new ArrayList<>();
        String button_text;
        VBox message_list = add_vbox("B3BFB8");
        grid.add(message_list, 0, 0);
        message_list.setVisible(true);
        for (int i = 0; i < messages.size(); i++) {
            HashMap<String, String> message = messages.get(i);
            for (String id : msg_ids) {
                if (message.containsValue(id)) {
                    button_text = "SUBJECT: ";
                    button_text = button_text + message.get("Subject");
                    button_text = button_text + " FROM: ";
                    button_text = button_text + message.get("From");
                    Button btn = add_button(button_text, "FFFFFF", "000000");
                    btn.setOnAction(e -> {
                        this.show_message_dict(id);
                    });
                    inbox_buttons.add(btn);
                    message_list.getChildren().add(btn);
                    button_text = "";
                }
        }
        }
    }
    private void show_message_dict(String id) {
        clear_read_message_container();
        for (int i = 0; i < messages.size(); i++) {
            HashMap<String, String> message = messages.get(i);
            for (String search_id : msg_ids) {
                if (message.containsValue(id)) {
                    read_msg_subject_label.setText(read_msg_subject_label.getText() + message.get("Subject"));
                    read_msg_date_label.setText(read_msg_date_label.getText() + message.get("Date"));
                    read_msg_from_label.setText(read_msg_from_label.getText() + message.get("From"));
                    read_msg_contents.setText(read_msg_contents.getText() + message.get("Contents"));
                    break;
                }
            }
        }
        scene_selection("Read");
    }

    private void reply_scene_setup() {
        scene_selection("Compose");
        String reply_to = read_msg_from_label.getText().substring(5, read_msg_from_label.getText().length());
        String subject_reply = "RE: " + read_msg_subject_label.getText().substring(8, read_msg_from_label.getText().length());
        comp_msg_subject.setText(subject_reply);
        comp_msg_to.setText(reply_to);
    }

    private void clear_read_message_container() {
        read_msg_subject_label.setText("Subject ");
        read_msg_date_label.setText("Date ");
        read_msg_from_label.setText("From ");
        read_msg_contents.setText("Message ");
    }

    // OLD METHODS USING JUST STRINGS AND ARRAYLIST, DICT/HASHMAP CLEANER IMPLEMENTATION OVERALL
//    public List<String> get_messages() {
//        String line;
//        String newline;
//        List<String> msg_list = new ArrayList<String>();
//        List<HashMap<String, String>> msg_dict = new ArrayList<>();
//        int count = get_msg_count();
//
//        try {
//            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + current_user + "_msgs.txt"));
//            while((line = mReader.readLine()) != null) {
//                if (line.equals("###SUBJECT###")) {
//                    newline = mReader.readLine();
//                    msg_list.add(newline);
//                } else if (line.equals("###DATE####")) {
//                    newline = mReader.readLine();
//                    msg_list.add(newline);
//                } else if (line.equals("###CONTENTS###")) {
//                    newline = mReader.readLine();
//                    msg_list.add(newline);
//                } else if (line.equals("###FROM###")) {
//                    newline = mReader.readLine();
//                    msg_list.add(newline);
//                } else if (line.equals("{{{{{ENDMESSAGE}}}}}")) {
//                    newline = mReader.readLine();
//                    msg_list.add(newline);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return msg_list;
//    }
//    public void build_message_list() {
//        List<String> inbox = get_messages();
//        String button_text = "";
//        List<Button> inbox_buttons = new ArrayList<>();
//
//        int occurrences = Collections.frequency(inbox, "{{{{{ENDMESSAGE}}}}}");
//        for (int i=0; i<occurrences; i++){
//            for (String msg : inbox) {
//                if (msg.equals("{{{{{ENDMESSAGE}}}}}")) {
//                    break;
//                } else {
//                    button_text += msg;
//                }
//            }
//            inbox_buttons.add(add_button(button_text, "F0F7F4", "3C493F"));
//            button_text = "";
//        }
//        for (Button btn : inbox_buttons) {
//            btn.setOnAction(e -> {
//                this.show_message();
//            });
//            message_list.getChildren().add(btn);
//        }
//    }
//        private void show_message() {
//            try {
//                BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + current_user + "_msgs.txt"));
//                String line;
//                String newline;
//                while((line = mReader.readLine()) != null) {
//                    if (line.equals("###SUBJECT###")) {
//                        newline = mReader.readLine();
//                        read_msg_subject_label.setText(read_msg_subject_label.getText() + newline);
//                    }
//                    if (line.equals("###DATE####")) {
//                        newline = mReader.readLine();
//                        read_msg_date_label.setText(read_msg_date_label.getText() + newline);
//                    }
//                    if (line.equals("###FROM###")) {
//                        newline = mReader.readLine();
//                        read_msg_from_label.setText(read_msg_from_label.getText() + newline);
//                    }
//                    if (line.equals("###CONTENTS###")) {
//                        while ((line = mReader.readLine()) != null) {
//                            if(line.equals("{{{{{ENDMESSAGE}}}}}")) {
//                                break;
//                            } else {
//                                read_msg_contents.setText(read_msg_contents.getText() + line);
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            compose_message_container.setVisible(false);
//            placeholder_right.setVisible(false);
//            read_message_container.setVisible(true);
//        }
//    public void change_background(VBox vbox, String bg_color) {
//        String style_str =  "-fx-background-color: #" + bg_color + ";";
//        vbox.setStyle(style_str);
//    }
//    public void set_button_style(Button btn, String text_color, String bg_color, Boolean enable_state, Boolean visible) {
//        btn.setVisible(visible);
//        btn.setDisable(enable_state);
//        String style_str = "-fx-background-color: #" + bg_color + "; -fx-text-fill: #" + text_color +";";
//        btn.setStyle(style_str);
//    }
//    public int get_msg_count(){
//        int occurrences = 0;
//        String line;
//        try {
//            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + current_user + "_msgs.txt"));
//            while ((line = mReader.readLine()) != null) {
//                if (line.equals("{{{{{ENDMESSAGE}}}}}")) {
//                    occurrences++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(occurrences);
//        return occurrences;
//    }
}


