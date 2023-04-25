package com.special.effect.javafxprojects.Networking;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        TextArea txtArea = new TextArea();

        Scene scene = new Scene(new ScrollPane(txtArea), 450, 200);
        stage.setTitle("Server");
        stage.setScene(scene);
        stage.show();

        new Thread(()->{
            try{
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(()->
                        txtArea.appendText("Server started at " + new Date() + '\n'));

                Socket socket = serverSocket.accept();

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while(true){
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    Platform.runLater(()->{
                        txtArea.appendText("Radius received from client: " + radius + "\n");
                        txtArea.appendText("Area is: " + area + "\n");
                    });
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
