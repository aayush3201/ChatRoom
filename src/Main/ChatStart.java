package Main;

public class ChatStart {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("arg " + i + " : " + args[i]);
        }
        if (args.length == 0) {
            System.err.println("Invalid arguments.");
            System.err.println("server - for running a server");
            System.err.println("client - for joining a server");
            System.exit(1);
        }
        if (args[0].equals("server")) {
            System.out.println("Running server.");
            Main.ChatRoomServer.run();
        } else if (args[0].equals("client")) {
            String argss[] = new String[args.length - 1];
            System.arraycopy(args, 1, argss, 0, argss.length);
            System.out.println("Joining server.");
            Main.ChatRoomClient.run(argss);
        } else {
            System.err.println("Invalid arguments.");
            System.err.println("server - for running a server");
            System.err.println("client - for joining a server");
        }
    }
}
