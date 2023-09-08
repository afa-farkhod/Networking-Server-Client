# [Networking-Server-Client](https://en.wikipedia.org/wiki/Client%E2%80%93server_model)

- This repository presents a `client` program and a `server` program. The client sends data to a server. The server receives the data, uses it to produce a result, and then sends the result back to the client. The client displays the result on the console. In this example, the data sent from the client comprise the radius of a circle, and the result produced by the server is the area of the circle.
  
<p align="center">
  <img src="https://user-images.githubusercontent.com/24220136/234190297-f013cc38-04e6-4655-bf56-ced22dbfe647.png" alt="Image">
</p>

- The client sends the radius through a `DataOutputStream` on the output stream socket, and the server receives the radius through the `DataInputStream` on the input stream socket, as shown in below Figure. The server computes the area and sends it to the client through a `DataOutputStream` on the output stream socket, and the client receives the area through a `DataInputStream` on the input stream socket.

<p align="center">
  <img src="https://user-images.githubusercontent.com/24220136/234190505-8115fb1c-2ea0-4413-9eaf-fd71a109bbdf.png" alt="Image">
</p>

- You start the server program first and then start the client program. In the client program, enter a radius in the text field and press Enter to send the radius to the server. The server computes the area and sends it back to the client. This process is repeated until one of the two programs terminates. Local port on the client is also visible in the first line. Following demo source code is located in the `Server-Single-Client` folder.

![Capture](https://user-images.githubusercontent.com/24220136/234189747-402b0ed9-cfb7-471c-8bb7-97bd56b98769.PNG)

- The networking classes are in the package java.net. You should import this package when writing Java network programs. The Server class creates a ServerSocket serverSocket and attaches it to port 8000 using this statement (line 26 in `Server.java`):
```
ServerSocket serverSocket = new ServerSocket(8000)
```
- The server then starts to listen for connection requests, we can check from the `terminal`

<p align="center">
  <img src="https://user-images.githubusercontent.com/24220136/234188058-dac6fc14-328b-4d62-9db2-79d90063d92c.png" alt="Image">
</p>

- The server waits until the client requests a connection. After it is connected, the server reads the radius from the client through an input stream, computes the area, and sends the result to the client through an output stream. The ServerSocket accept() method takes time
to execute. It is not appropriate to run this method in the JavaFX application thread. So, we
place it in a separate thread (lines 23–59). The statements for updating GUI need to run from
the JavaFX application thread using the Platform.runLater method (lines 27–28, 49–53).

- The Client class uses the following statement to create a socket that will request a connection to the server on the same machine (localhost) at port 8000 (line 67 in `Client.java`):
```
Socket socket = new Socket("localhost", 8000)
```
- If you receive a `java.net.BindException` when you start the server, the server port is currently in use. You need to terminate the process that is using the server port and then restart the server. First check for the `port` whether it's in use or not by following command in the terminal:
```
newstat -ano | findstr :8080
```
- After that, you can terminate that particular port with the following command (in the below example 7352 is PID (Process ID):
```
taskkill /PID 7352 /F
```
## Networking Server-Multiple-Client Java API

- A server can serve multiple clients. The connection to each client is handled by one thread. Multiple clients are quite often connected to a single server at the same time. Typically, a server runs continuously on a server computer, and clients from all over the Internet can connect to it. You can use threads to handle the server’s multiple clients simultaneously—simply create a thread for each connection. Here is how the server handles the establishment of a connection:

<p align="center">
  <img src="https://user-images.githubusercontent.com/24220136/234768575-e24f3f1d-ce81-40a9-abb8-1550f8eee127.png" alt="Image">
</p>

- The server socket can have many connections. Each iteration of the while loop creates a new connection. Whenever a connection is established, a new thread is created to handle communication between the server and the new client, and this allows multiple connections to run at the same time. Below demo creates a server class that serves multiple clients simultaneously. For each connection, the server starts a new thread. This thread continuously receives input (the radius of a circle) from clients and sends the results (the area of the circle) back to them. The client program is the same as in previous example. A sample run of the server with two clients is shown in below Figure. Following is the demo of Multi Thread Server and Multiple Clients connection Java API, source is located in the `Server-Multiple-Client` folder

![image](https://user-images.githubusercontent.com/24220136/234768523-eab7abc5-0bf9-4f64-b6d4-5a7065b86bc0.png)


