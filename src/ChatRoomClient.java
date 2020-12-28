import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
/**
 * A ChatRoomClient represents a client which joins a room by connecting to a ChatRoomServer.
 * Chat can be done in the terminal which executes ChatRoomClient.main()
 * */
public class ChatRoomClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader in2;
    private String userName;
    private MessageQueue received;

    /**
     * Constructor
     * @param hostname hostname of the server to connect to
     * @param port the port the server is listening on
     * @param name to join the room as
     * */

    public ChatRoomClient(String hostname, int port, String name) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //for receiving messages
        in2 = new BufferedReader(new InputStreamReader(System.in)); //for your messages to the server
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        userName = (name!=null && !name.equals(""))?name:"user"+Long.toString(System.currentTimeMillis()%1000);
        received = new MessageQueue();
    }
    /**
     * Connects to the server whose hostname is mentioned in args[0] under the name
     * mentioned in args[1]
     * */
    public static void main(String args[]){
        try {
            String hostname = args.length>0?args[0]:"localhost";
            String name = args.length>1?args[1]:"";
            ChatRoomClient client = new ChatRoomClient(hostname, 4949,name);
            client.getMessages();
            client.sendMessages();
            Set<Long>already = new HashSet<>();
            while(true){
                String message = client.received.getMessage(already);
                if(message!=null)
                    System.out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Couldn't establish connection. Check hostname.");
            e.printStackTrace();
        }
    }
    /**
     * An instance of MessageGetter is responsible for receiving messages sent out by
     * other clients indirectly through the server.
     * */
    private class MessageGetter implements Runnable{

        @Override
        public void run() {
            try {
                while (true)
                    received.add(in.readLine(),1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * An instance of MessageSender is responsible for sending the current client's messages
     * to other clients indirectly through the server
     * */

    private class MessageSender implements Runnable{

        @Override
        public void run() {
            try {
                while(true) {
                    String message = in2.readLine();
                    out.print(userName + ": " + message + "\n");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Listens for messages on a new thread
     * */
    private void getMessages() throws IOException {
       Thread thread = new Thread(new MessageSender());
       thread.start();
    }
    /**
     * Waits for new messages to be sent on a new thread
     * */
    private void sendMessages() throws IOException{
       Thread thread = new Thread(new MessageGetter());
       thread.start();
    }

}
