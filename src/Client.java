import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        try (Socket socketClient = new Socket("localhost", 12345)) {
            BufferedReader bufClient = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream output = new DataOutputStream(socketClient.getOutputStream());
            DataInputStream input = new DataInputStream(socketClient.getInputStream());


            System.out.println("Client connected to socket.");
            System.out.println();
            System.out.println("Client writing channel = output & reading channel = input initialized.");

            while (!socketClient.isOutputShutdown()) {

                if (bufClient.ready()) {
                    System.out.println("Client start writing in channel...");
                    String clientMessage = bufClient.readLine();

                    output.writeUTF(clientMessage);
                    output.flush();
                    System.out.println("Client sent a message " + clientMessage + " to server.");

                    if (clientMessage.equalsIgnoreCase("bye")) {
                        if (input.read() > -1) {
                            String in = input.readUTF();
                            System.out.println("reading...");
                            System.out.println(in);
                        }
                        break;
                    }
                    System.out.println("Client sent message & start waiting for data from server...");
                    if (input.read() > -1) {
                        System.out.println("reading...");
                        String inputServ = input.readUTF();
                        System.out.println(inputServ);
                    }
                }
            }
            System.out.println("Closing connections & channels on client side - DONE.");

            input.close();
            output.close();
            socketClient.close();

        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }
}
