# ChatRoom
ChatRoom is a software which enables users to group chat via the command line. It has been written entirely using Java.

# How to use?
Download the files to a new folder.

Open the command line.

Navigate to this folder and then navigate to the src folder.

Type in "java ChatRoomServer" to start the server on the current machine/console.

Now, other machines/consoles can join this server with the following command (while in the same folder):-
"java ChatRoomClient <hostname> <name>"
where <hostname> is the hostname of the machine on which the server is running and <name> is the name the client wishes to join the room under.

# How to exit?
Exiting abruptly would result in the server crashing. If a client wishes to leave, he/she must type in "$LEAVE" in the terminal (instead of a message)
before closing the terminal.
