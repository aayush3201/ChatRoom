# ChatRoom

ChatRoom is a software which enables users to group chat via the command line.
It has been written entirely using Java.

## Building

```sh
javac MessageQueue.java
javac ChatRoomServer.java
javac ChatRoomClient.java
```

## How to use?

- change current directory to where compiled `.class` files location.
- `java ChatRoomServer` - to start the server on the current machine.
- `java ChatRoomClient [server-ip] [username]` - connect to sever with username as `username`
  - `server-ip` - ip of the computer running the server program
  - `username` - name of your user

## TODO

- [ ] Fix server crashing problem, current if user leaves without using `$LEAVE` it cause server crash.

## License

MIT