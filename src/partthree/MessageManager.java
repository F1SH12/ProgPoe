/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package partthree;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageManager {

    // 1. Define the Arrays
    public static ArrayList<Message> sentMessages = new ArrayList<>();
    public static ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static ArrayList<Message> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    
    public static void populateMessageArrays(Message msg) {

        messageIDs.add(msg.messageID);
        messageHashes.add(msg.messageHash);
        
        //flag
        if (msg.flag.equals("Sent")) {
            sentMessages.add(msg);
        } else if (msg.flag.equals("Disregard")) {
            disregardedMessages.add(msg);
        } else if (msg.flag.equals("Stored")) {
            storedMessages.add(msg);
        }
    }

    public static String displayLongestMessage() {
        String longestMsgText = "";
        int maxLength = 0;

        //sent messages
        for (Message msg : sentMessages) {
            if (msg.messageText.length() > maxLength) {
                maxLength = msg.messageText.length();
                longestMsgText = msg.messageText;
            }
        }
        
        //stored messages
        for (Message msg : storedMessages) {
            if (msg.messageText.length() > maxLength) {
                maxLength = msg.messageText.length();
                longestMsgText = msg.messageText;
            }
        }

        return longestMsgText;
    }

    public static String searchForMessageID(String messageId) {
        
        ArrayList<Message> allMsgs = new ArrayList<>();
        allMsgs.addAll(sentMessages);
        allMsgs.addAll(storedMessages);
        allMsgs.addAll(disregardedMessages);
        
        for (Message msg : allMsgs) {
            if (msg.messageID.equals(messageId)) {
                return "Recipient: " + msg.recipient + "\nMessage: \"" + msg.messageText + "\"";
            }
        }
        return "Message ID not found.";
    }

    public static String searchForRecipient(String recipient) {
        StringBuilder report = new StringBuilder("--- Messages for Recipient " + recipient + " ---\n");
        int count = 0;

        ArrayList<Message> searchPool = new ArrayList<>();
        searchPool.addAll(sentMessages);
        searchPool.addAll(storedMessages);

        for (Message msg : searchPool) {
            if (msg.recipient.equals(recipient)) {
                report.append("Status: ").append(msg.flag).append(", Hash: ").append(msg.messageHash)
                      .append("\nText: \"").append(msg.messageText).append("\"\n---\n");
                count++;
            }
        }
        
        if (count == 0) {
            return "No Sent or Stored messages found for recipient " + recipient + ".";
        }
        
        return report.toString();
    }

    public static String deleteMessage(String messageHash) {
        
        ArrayList<Message> allArrays[] = new ArrayList[] {sentMessages, storedMessages, disregardedMessages};
        String deletedMessageText = null;
        boolean deleted = false;
        
        for (ArrayList<Message> messageList : allArrays) {
            Iterator<Message> iterator = messageList.iterator();
            while (iterator.hasNext()) {
                Message msg = iterator.next();
                if (msg.messageHash.equals(messageHash)) {
                    deletedMessageText = msg.messageText;
                    iterator.remove();

                    messageIDs.remove(msg.messageID);
                    messageHashes.remove(msg.messageHash);
                    
                    deleted = true;
                    break; 
                }
            }
            if (deleted) break;
        }

        if (deleted) {
            return "Message \"" + deletedMessageText + "\" successfully deleted."; 
        } else {
            return "Failure: Message with hash " + messageHash + " not found.";
        }
    }
    
    public static String displaySentMessagesReport() {
        StringBuilder report = new StringBuilder("--- QUICKCHAT SENT MESSAGES REPORT ---\n");

        if (sentMessages.isEmpty()) {
            report.append("No sent messages to display.");
            return report.toString();
        }

        for (int i = 0; i < sentMessages.size(); i++) {
            Message msg = sentMessages.get(i);

            report.append("--- Message ").append(i + 1).append(" ---\n");
            report.append("Message Hash: ").append(msg.messageHash).append("\n");
            report.append("Recipient: ").append(msg.recipient).append("\n");
            report.append("Message: \"").append(msg.messageText).append("\"\n");
        }
        
        return report.toString();
    }
}
