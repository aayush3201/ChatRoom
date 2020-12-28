

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * A ChatRoomServer instance represents a server which acts like a room. The users that have
 * connected to this server can group chat with each other.
 * */

public class ChatRoomServer {
    private ServerSocket serverSocket;
    private MessageQueue messages;
    private int members;

    // serverSocket != null, messages != null and member>=0

    /**
     * Constructor to create a server instance
     * @param port port number for the server to listen on
     * */
    public ChatRoomServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        messages = new MessageQueue();
        members = 0;
    }
    /**
     * An instance of the Server class represents a single client-server connection
     * */
    private class Server implements Runnable{
        private final Socket socket;
        private final Set<Long> alreadyRead;

        /**
         * Constructor to start a client-server connection
         * @param socket is the socket for communication with the client
         * */
        public Server(Socket socket){
            this.socket = socket;
            alreadyRead = new HashSet<>();
        }
        /**
         * Calls the handle(...) method
         * */
        @Override
        public void run() {
                try {
                    handle(socket,alreadyRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    /**
     * Starts the ChatRoomServer on the main thread and listens for new
     * connections. Also runs new client-server connections on new threads
     * */
    public void serve(){
        try {
            while(true){
                //hear for requests
                System.out.println("Waiting on connections!");
                Socket socket = serverSocket.accept();
                try {
                    Server server = new Server(socket);
                    Thread thread = new Thread(server);
                    thread.start();
                    this.members++;
                    System.out.println("In room now: "+members);
                } catch (Exception e) {
                    e.printStackTrace(); // but don't terminate serve()
                }
            }
        } catch(Exception e){
            System.err.println("An error occurred");
        }
    }
    /**
     * An instance of MessageReceiver acts as a medium to receive messages on separate threads
     * */
    private class MessageReceiver implements Runnable{
        private final BufferedReader in;
        private final Socket socket;
        /**
         * Constructor to instantiate MessageReceiver
         * @param socket the socket from where the messages are coming
         * @param in the reader which reads messages incoming in the form of a stream
         * */
        public MessageReceiver(BufferedReader in, Socket socket){
            this.in = in;
            this.socket = socket;
        }

        /**
         * Closes connection if message contains "$LEAVE", else adds message to queue
         * to be sent to everyone in the room.
         * */
        @Override
        public void run() {
            while(!socket.isClosed()){
                String message = null;
                int flag = 0;
                try {
                    message = in.readLine();
                    if(leave(message)) {
                        in.close();
                        socket.close();
                        members--;
                        System.out.println("In room now: "+ members);
                        flag = 1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(message != null && flag==0){
                    messages.add(message,members);
                }
            }
        }
    }
    /**
     * Checks if a message expresses intention to leave the room
     * */
    private static boolean leave(String message){
        int index = message.indexOf("$LEAVE");
        return index != -1;
    }
    /**
     * Handles a client-server connection
     * @param socket The socket with the connection
     * @param alreadyRead A set of numbers which represents the messages already displayed to
     *                    the current client and must not be displayed again
     * */
    private void handle(Socket socket, Set<Long>alreadyRead) throws IOException {
        System.out.println("User has joined the room!");
        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream()));
        Thread thread = new Thread(new MessageReceiver(in,socket));
        thread.start();
        while(socket.isConnected()){
            String toPrint = messages.getMessage(alreadyRead);
            if(toPrint != null){
                out.print(toPrint + "\n");
                out.flush();
            }
        }
        out.close();
    }
    /**
     * Starts the server
     * */
    public static void main(String[] args) {
        try {
            ChatRoomServer server = new ChatRoomServer(4949);
            server.serve();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
