import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A MessageQueue is a thread-safe queue which stores messages till they are read and discards them once they are
 * read using the First in First out (FIFO) principle. Ensures that a message is read by the same number of people
 * as it is sent to before its removal from the queue.
 * */

public class MessageQueue {
    List<String>messages;
    List<Integer>toHowMany;
    List<Long>time;
    /**
     * Default constructor
     * */
    public MessageQueue(){
        messages = new ArrayList<>();
        toHowMany = new ArrayList<>();
        time = new ArrayList<>();
    }
    /**
     * Returns number of elements in the MessageQueue
     * */
    public synchronized int size(){
        return messages.size();
    }
    /**
     * Adds an element to the queue
     * */
    public synchronized void add(String message, int reads){
        messages.add(message);
        toHowMany.add(reads);
        time.add(System.currentTimeMillis());
    }
    /**
     * Return true if the queue is not empty
     * */
    private synchronized boolean hasMessages(){
        if(this.size()>0)
            return true;
        return false;
    }
    /**
     * Returns the next message if it hasn't been displayed to the current client.
     * @param alreadyRead represents a set of clients who have already read the message
     * */
    public synchronized String getMessage(Set<Long>alreadyRead){
        if(this.hasMessages()){
            if(alreadyRead.contains(time.get(0)))
                return null;
            String message = messages.get(0);
            alreadyRead.add(time.get(0));
            toHowMany.set(0,toHowMany.get(0)-1);
            if(toHowMany.get(0)==0){
                messages.remove(0);
                toHowMany.remove(0);
                alreadyRead.remove(time.get(0));
                time.remove(0);
            }
            return message;
        }
        return null;
    }
}
