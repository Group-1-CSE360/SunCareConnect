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
import java.util.Dictionary;
import java.util.Hashtable;
public class Message extends Application{
    int current_user = 12345;
    String dir = System.getProperty("user.dir");
    HBox button_container = new HBox(5);
    Button reply_button = add_button("Reply", "A2E3C4", "3C493F");
    Button compose_button = add_button("New", "007BFF", "FFFFFF");
    Button discard_button = add_button("Discard", "F1B6AC", "FFFFFF");
    Button send_button = add_button("Send", "A2E3C4", "3C493F");
    TextField comp_msg_subject = new TextField();
    TextField comp_msg_to = new TextField();
    TextField comp_msg = new TextField();
    Label comp_msg_subject_label = new Label("Subject");
    Label comp_msg_to_label = new Label("To");
    Label read_msg_subject_label = new Label("Subject: ");
    Label read_msg_date_label = new Label("Date: ");
    Label read_msg_from_label = new Label("From: ");
    TextArea read_msg_contents = new TextArea();

    TextArea read_msg_subject = new TextArea();
    TextArea read_msg_from = new TextArea();
    TextArea read_msg_date = new TextArea();
    GridPane grid = add_grid();
    VBox message_list = add_vbox("B3BFB8");

    VBox placeholder_right = add_vbox("4B574E");
    GridPane compose_message_container = setup_message_composer_scene();
    GridPane read_message_container = setup_message_reader_scene();
    //this serves as the entry point scene
    Scene entry = new Scene(grid, 640, 480);

    @Override
    public void start(Stage stage) throws Exception {
        Integer user = 12345;
        //setup button menu
        button_container.getChildren().addAll(send_button ,reply_button, compose_button, discard_button);
        button_container.setSpacing(10);
        button_container.setAlignment(Pos.CENTER);
        //adding to grid syntax reminder grid.add(Node, colIndex, rowIndex, colSpan, rowSpan):
        //add to container for all UI items
        grid.add(message_list, 0, 0);
        grid.add(button_container, 1, 1);
        grid.add(compose_message_container, 1, 0);
        grid.add(read_message_container, 1, 0);
        grid.add(placeholder_right, 1, 0);
        compose_message_container.setVisible(false);
        get_messages(current_user);

        compose_button.setOnAction(e -> {
            placeholder_right.setVisible(false);
            compose_message_container.setVisible(true);
            read_message_container.setVisible(false);
        });
        discard_button.setOnAction(e -> {
            discard_message();
        });
        send_button.setOnAction(e -> {
            Integer send_to_user = Integer.parseInt(comp_msg_to.getText());
            send_message(send_to_user);
        });

        //using gridlines for testing alignment purposes
        grid.setGridLinesVisible(true);
        build_message_list(12345);
        stage.setScene(entry);
        stage.show();
    }
    //try to keep button styling consistent by using a method
    public Button add_button(String btnText, String btnColor, String textColor) {
    Button btn = new Button(btnText);
    String style_str = "-fx-background-color: #" + btnColor + "; -fx-text-fill: #" + textColor +";";
    btn.setStyle(style_str);
    btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    btn.setAlignment(Pos.CENTER);
    HBox.setHgrow(btn, Priority.ALWAYS);
    return btn;
}
public List<String> get_messages(Integer user) {
    String line;
    String newline;
    List<String> msg_list = new ArrayList<String>();
    Dictionary msg_dict = new Hashtable();
    try {
        BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + user + "_msgs.txt"));
        while((line = mReader.readLine()) != null) {
            if (line.equals("###SUBJECT###")) {
                newline = mReader.readLine();
                msg_list.add(newline);
            } else if (line.equals("###DATE####")) {
                newline = mReader.readLine();
                msg_list.add(newline);
            } else if (line.equals("###CONTENTS###")) {
                newline = mReader.readLine();
                msg_list.add(newline);
            } else if (line.equals("###FROM###")) {
                newline = mReader.readLine();
                msg_list.add(newline);
            }

        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return msg_list;
}

public void send_message(Integer user) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter( dir + "/" + user + "_msgs.txt", true))) {
        bw.write("###FROM###");
        bw.newLine();
        bw.write(Integer.toString(current_user));
        bw.newLine();
        bw.write("###TO###");
        bw.newLine();
        bw.write(comp_msg_to.getText());
        bw.newLine();
        bw.write("###DATE###");
        bw.newLine();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
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
        System.out.println("Saved file successfully");
        bw.close();
    }catch (IOException e) {
        e.printStackTrace();
    }
}


public void discard_message() {
    comp_msg_subject.setText("");
    comp_msg_to.setText("");
    comp_msg.setText("");
    compose_message_container.setVisible(false);
    placeholder_right.setVisible(true);
}
//try to keep all grid configuration in a separate method for readability
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

public void change_background(VBox vbox, String bg_color) {
    String style_str =  "-fx-background-color: #" + bg_color + ";";
    vbox.setStyle(style_str);
}

public void set_button_style(Button btn, String text_color, String bg_color, Boolean enable_state, Boolean visible) {
    btn.setVisible(visible);
    btn.setDisable(enable_state);
    String style_str = "-fx-background-color: #" + bg_color + "; -fx-text-fill: #" + text_color +";";
    btn.setStyle(style_str);
}

public GridPane setup_message_composer_scene() {
        GridPane grid = new GridPane();
    grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
    grid.setVgap(10); //vertical gap in pixels
    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setStyle("-fx-background-color: #F0F7F4");
    grid.add(comp_msg_subject_label, 0, 1);
    grid.add(comp_msg_subject, 1, 1);
    grid.add(comp_msg_to_label, 0, 0);
    grid.add(comp_msg_to, 1, 0);
    grid.add(comp_msg, 0, 2, 2, 9);
    return grid;
}
    public GridPane setup_message_reader_scene() {
        GridPane grid = new GridPane();
        grid.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        grid.setVgap(10); //vertical gap in pixels
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setStyle("-fx-background-color: #F0F7F4");
        grid.add(read_msg_from_label, 0, 0);
//        grid.add(read_msg_from, 1, 0);
//        grid.add(read_msg_date, 2, 0);
        grid.add(read_msg_subject_label, 0, 1);
        grid.add(read_msg_contents, 0, 3);
        return grid;
    }

    public void build_message_list(Integer user) {
        List<String> inbox = get_messages(user);
        String button_text = "";
        List<Button> inbox_buttons = new ArrayList<>();

        int occurrences = Collections.frequency(inbox, "{{{{{ENDMESSAGE}}}}}");
        for (int i=0; i<=occurrences; i++){
            for (String msg : inbox) {
                if (msg.equals("{{{{{ENDMESSAGE}}}}}")) {
                    break;
                } else {
                    button_text += msg;
                }
            }
            inbox_buttons.add(add_button(button_text, "F0F7F4", "3C493F"));
            button_text = "";
        }
        for (Button btn : inbox_buttons) {
            btn.setOnAction(e -> {
                this.show_message(12345);
            });
            message_list.getChildren().add(btn);
        }
    }

    private void show_message(Integer user) {
        try {
            BufferedReader mReader = new BufferedReader(new FileReader(dir + "/" + user + "_msgs.txt"));
            String line;
            String newline;
            line = mReader.readLine();
                if (line.equals("###SUBJECT###")) {
                    newline = mReader.readLine();
                    read_msg_subject_label.setText(read_msg_subject_label.getText() + newline);
                } else if (line.equals("###DATE####")) {
                    newline = mReader.readLine();
                    read_msg_date_label.setText(read_msg_date_label.getText() + newline);
                } else if (line.equals("###FROM###")) {
                    newline = mReader.readLine();
                    read_msg_from_label.setText(read_msg_from_label.getText() + newline);
                }  else if (line.equals("###CONTENTS###")) {
                    while((line = mReader.readLine()) != null) {
                        read_msg_contents.setText(read_msg_contents.getText() + line);
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        compose_message_container.setVisible(false);
        placeholder_right.setVisible(false);
        read_message_container.setVisible(true);
    }
}


