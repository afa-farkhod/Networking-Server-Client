package com.special.effect.javafxprojects.Networking;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MultiThreadServer extends Application {
    private TextArea txtArea = new TextArea();
    private int clientNo = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new ScrollPane(txtArea), 450, 200);
        stage.setTitle("MultiThreadServer");
        stage.setScene(scene);
        stage.show();

        new Thread(() ->{
            try{
                ServerSocket serverSocket = new ServerSocket(8000);
                txtArea.appendText("MultiThreadServer started at " + new Date() + '\n');
                while(true){
                    Socket socket = serverSocket.accept();
                    clientNo++;

                    Platform.runLater(()->{
                        txtArea.appendText("Starting thread for client " + clientNo + " at " + new Date() + '\n');

                        InetAddress inetAddress = socket.getInetAddress();
                        txtArea.appendText("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + '\n');
                        txtArea.appendText("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + '\n');
                    });
                    new Thread(new HandleAClient(socket)).start();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    class HandleAClient implements Runnable{
        private Socket socket;
        private int clientNo; // new line

        public HandleAClient(Socket socket, int clientNo){
            this.socket = socket;
            this.clientNo = clientNo;
        }

        public HandleAClient(Socket socket) {

        }

        @Override
        public void run() {
            try{
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                while(true){
                    double radius = inputFromClient.readDouble();

                    double area = radius * radius * Math.PI;

                    outputToClient.writeDouble(area);

                    Platform.runLater(()->{
//                        txtArea.appendText("Radius received from client: " + radius + "\n");
                        txtArea.appendText("Radius receieved from client " + clientNo + ": " + radius + '\n');
                        txtArea.appendText("Area is: " + area + "\n");
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
