package com.special.effect.javafxprojects.Networking;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client2 extends Application {
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5,5,5,5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter a radius: "));

        TextField txtField = new TextField();
        txtField.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(txtField);

        BorderPane mainPane = new BorderPane();
        TextArea txtArea = new TextArea();
        mainPane.setCenter(new ScrollPane(txtArea));
        mainPane.setTop(paneForTextField);

        Scene scene = new Scene(mainPane, 450, 200);
        stage.setTitle("Client2");
        stage.setScene(scene);
        stage.show();

        txtField.setOnAction(e->{
            try{
                double radius = Double.parseDouble(txtField.getText().trim());

                toServer.writeDouble(radius);
                toServer.flush();

                double area = fromServer.readDouble();

                txtArea.appendText("Radius is " + radius + "\n");
                txtArea.appendText("Area recieved from the server is " + area + '\n');


            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        try{
            Socket socket = new Socket("localhost", 8000);
            //Socket socket = new Socket("130.254.204.36", 8000);
            //Socket socket = new Socket("study-education.com", 8000);
            txtArea.appendText("local port: " + socket.getLocalPort() + '\n');
            //System.out.println("local port: " + socket.getLocalPort());

            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
