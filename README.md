# ChatRoom
ChatRoom is a software which enables users to group chat via the command line. It has been written entirely using Java.

# How to use?
Download the files to a new folder.

Open the command line.

Navigate to this folder and then navigate to the src folder.

Type in "java ChatRoomServer" to start the server on the current machine/console.

Now, other machines/consoles can join this server with the following command (while in the same folder):-
"java ChatRoomClient x y"
where x is the hostname of the machine on which the server is running and y is the name the client wishes to join the room under.

Example: Two devices are connected to the same Wi-fi network and have IP addresses IP1 and IP2 respectively. Suppose you run the server on the PC with IP address IP1. Then, run the client on the other PC using "java ChatRoomClient IP1 name".
If the PC on which the server is running wants to join chat as well, you can use "java ChatRoomClient localhost name". 

# How to exit?
Exiting abruptly would result in the server crashing. If a client wishes to leave, he/she must type in "$LEAVE" in the terminal (instead of a message)
before closing the terminal.

# Note
The binary files in the repository might have been compiled using a newer version of java compliler. If this is the case run the following commands in this order (in the src folder):-
javac MessageQueue.java
javac ChatRoomServer.java
javac ChatRoomClient.java

Then proceed as above.
