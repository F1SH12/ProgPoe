/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parttwo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Message {

    public static ArrayList<Message> allMessages = new ArrayList<>();
    public static int totalMessagesSent = 0;
    public static int messageCount = 0;

    public String messageID;
    public String messageHash;
    public String recipient;
    public String messageText;

    public Message(String messageID, String messageHash, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageHash = messageHash;
        this.recipient = recipient;
        this.messageText = messageText;
        allMessages.add(this);
    }
    
    public static void incrementMessageCount() {
        messageCount++;
    }

    public static String createMessageID() {
        Random random = new Random();
        long tenDigitNumber = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
        return String.valueOf(tenDigitNumber);
    }
   
    public static String checkMessageLength(String message) {
        if (message == null) 
            return "Failure: Message is empty.";
        int length = message.length();
        if (length <= 250) {
            return "Success:Message ready to send.";
        } else {
            return "Failure:Message exceeds 250 characters by " + (length - 250) + ", please reduce size.";
        }
    }

    public static String checkRecipientCell(String cellphoneNumber) {
        Pattern pattern = Pattern.compile("^\\+27\\d{9}$"); 

        if (pattern.matcher(cellphoneNumber).matches()) {
            return "Success:Cell phone number successfully captured.";
        } else {
            return "Failure:Cell phone number is incorrectly formatted or does not contain an international code.";
        }
    }

    public static String createMessageHash(String messageID, int msgNumber, String messageText) {
        if (messageID == null || messageID.length() < 2 || messageText == null || messageText.trim().isEmpty()) {
            return ""; 
        }

        String idPrefix = messageID.substring(0, 2);
        
        String cleanedText = messageText.replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] words = cleanedText.trim().split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        return (idPrefix + ":" + msgNumber + ":" + (firstWord + lastWord)).toUpperCase();
    }

    public String SentMessage() {
        String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
        int choice = JOptionPane.showOptionDialog(null, "What would you like to do with this message?", "Message Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);

        switch (choice) {
            case 0:
                totalMessagesSent++;
                return "Message successfully sent.";
            case 1:
                return "Press 0 to delete message.";
            case 2:
                storeMessage(this);
                return "Message successfully stored.";
            default:
                return "Message action cancelled.";
        }
    }
    
    public static void storeMessage(Message msg) {
        try (FileWriter file = new FileWriter("stored_messages.txt", true)) {
            file.write("--- STORED MESSAGE ---\n");
            file.write("ID: " + msg.messageID + ", Hash: " + msg.messageHash + ", Recipient: " + msg.recipient + "\n");
            file.write("Text: " + msg.messageText + "\n\n");
            JOptionPane.showMessageDialog(null, "Message saved to stored_messages.txt successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while storing the message.");
        }
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }
}
