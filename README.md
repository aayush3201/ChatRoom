# ChatRoom

ChatRoom is a software which enables users to group chat via the command line.
It has been written entirely using Java.

## Building

- run `build.sh`
- the final build will be inside `release` directory.

## How to use?

- run `build.sh` to compile and build the `Chat.jar` file
- change directory to `release`
- `java -jar Chat.jar server` to start the server on the current machine.
- `java -jar Chat.jar client [server-ip] [username]` - connect to sever with username as `username`
  - `server-ip` - ip of the computer running the server program
  - `username` - name of your user

## TODO

- [ ] Fix server crashing problem, current if user leaves without using `$LEAVE` it cause server crash.

## License

MIT