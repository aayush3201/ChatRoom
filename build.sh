#!/bin/bash

SRC_DIR="./src/Main"

# compiling
javac $SRC_DIR/MessageQueue.java -classpath ./src -d release
javac $SRC_DIR/ChatRoomClient.java -classpath ./src -d release
javac $SRC_DIR/ChatRoomServer.java -classpath ./src -d release
javac $SRC_DIR/ChatStart.java -classpath ./src -d release

# creating jar
cd release || exit
jar cvfe Chat.jar Main.ChatStart Main/*