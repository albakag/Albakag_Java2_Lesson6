import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws InterruptedException {


        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Waiting for new Client");

            Socket socket = serverSocket.accept();
            System.out.println("Hello new Client!");

            BufferedReader bufServ = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            while (!socket.isClosed()) {
                String str = input.readUTF();
                System.out.println("Client wrote: " + str);

                if (str.equalsIgnoreCase("bye")) {
                    output.writeUTF("Server ECHO - " + str + " - OK");
                    output.flush();
                    serverSocket.close();
                    break;
                }

                if (bufServ.ready()) {
                    System.out.println("Server start writing in channel...");
                    String serverMessage = bufServ.readLine();

                    output.writeUTF("Server message - " + serverMessage);
                    output.flush();
                    System.out.println("Server sent a message " + serverMessage + " to client.");
                }

            }
            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
