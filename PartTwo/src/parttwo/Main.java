package parttwo;

import javax.swing.JOptionPane;

public class Main {
    
    public static boolean isLoggedIn = false;
    public static int numMessagesToEnter = 0;

    public static void main(String[] args) {

        String[] authOptions = {"Register", "Login", "Exit"};
        int authChoice;

        do {
            authChoice = JOptionPane.showOptionDialog(null, "What would you like to do?", "User Management System", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, authOptions, authOptions[0]);

            if (authChoice == 0) {
                registerFlow();
            } 

            else if (authChoice == 1) { 
                loginFlow();
            }

            if (isLoggedIn) {
                startQuickChat();
                isLoggedIn = false;
            }

        } while (authChoice != JOptionPane.CANCEL_OPTION && authChoice != JOptionPane.CLOSED_OPTION);

        JOptionPane.showMessageDialog(null, "Thank you for using the system. Goodbye!");
    }
    
    public static void registerFlow() {
        JOptionPane.showMessageDialog(null, "--- User Registration ---", "Registration", JOptionPane.INFORMATION_MESSAGE);

                String regUsername = JOptionPane.showInputDialog(null, "Enter username (must contain '_' and be no more than 5 characters long):");
                String regPassword = JOptionPane.showInputDialog(null, "Enter password (at least 8 chars, 1 capital, 1 number, 1 special char):");
                String regCellphone = JOptionPane.showInputDialog(null, "Enter cellphone number (e.g., +27821234567):");
                String regFirstName = JOptionPane.showInputDialog(null, "Enter first name:");
                String regLastName = JOptionPane.showInputDialog(null, "Enter last name:");

        String registrationStatus = Login.registerUser(regUsername, regPassword, regCellphone, regFirstName, regLastName);
        JOptionPane.showMessageDialog(null, registrationStatus);
    }

    public static void loginFlow() {
        JOptionPane.showMessageDialog(null, "--- User Login ---", "Login", JOptionPane.INFORMATION_MESSAGE);

        String loginUsername = JOptionPane.showInputDialog(null, "Enter username:");
        String loginPassword = JOptionPane.showInputDialog(null, "Enter password:");

        String loginStatus = Login.loginUser(loginUsername, loginPassword);
        JOptionPane.showMessageDialog(null, loginStatus);

        if (loginStatus.startsWith("Welcome")) {
            isLoggedIn = true;
        } else {
            isLoggedIn = false;
        }
    }

    public static void startQuickChat() {
        
        String numInput = JOptionPane.showInputDialog(null, "Welcome to QuickChat! How many messages do you wish to enter?");
        try {
            numMessagesToEnter = Integer.parseInt(numInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid number. Defaulting to 1 message.", "Error", JOptionPane.ERROR_MESSAGE);
            numMessagesToEnter = 1;
        }

        String[] chatOptions = {"Send Messages", "Show total messages sent", "Quit"};
        int chatChoice;

        do {
            chatChoice = JOptionPane.showOptionDialog(null,"Choose a feature:", "QuickChat Menu", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, chatOptions,chatOptions[0]);

            if (chatChoice == 0) { 
                sendMessagesLoop(numMessagesToEnter);
            } 

            else if (chatChoice == 1) { 
                JOptionPane.showMessageDialog(null, "Coming Soon");
            } 
            
            else if (chatChoice == 2 || chatChoice == JOptionPane.CANCEL_OPTION || chatChoice == JOptionPane.CLOSED_OPTION) { 
                break;
            }

        } while (true); 

        JOptionPane.showMessageDialog(null, "Exiting QuickChat. Total messages SENT: " + Message.returnTotalMessages());
    }
    
    public static void sendMessagesLoop(int limit) {
        int count = 0;
        
        while (count < limit) {
            
            String recipient;
            do {
                recipient = JOptionPane.showInputDialog("Msg " + (count + 1) + "/" + limit + ": Enter recipient (+27...):");
                String status = Message.checkRecipientCell(recipient);
                JOptionPane.showMessageDialog(null, status);
                if (status.startsWith("Success")) break;
                if (recipient == null) return; 
            } while (true);
            
            String messageText;
            do {
                messageText = JOptionPane.showInputDialog("Msg " + (count + 1) + "/" + limit + ": Enter message (max 250 chars):");
                String status = Message.checkMessageLength(messageText);
                JOptionPane.showMessageDialog(null, status);
                if (status.startsWith("Success")) break;
                if (messageText == null) return;
            } while (true);
            
            Message.incrementMessageCount(); 
            String messageID = Message.createMessageID();
            String messageHash = Message.createMessageHash(messageID, Message.messageCount, messageText);
            
            Message currentMsg = new Message(messageID, messageHash, recipient, messageText);
            String actionStatus = currentMsg.SentMessage();
            
            String display = "--- MESSAGE DISPLAY ---\n"
                           + "ID: " + messageID + "\n"
                           + "Hash: " + messageHash + "\n"
                           + "Recipient: " + recipient + "\n"
                           + "Text: " + messageText + "\n"
                           + "Action: " + actionStatus;
            
            JOptionPane.showMessageDialog(null, display);
            count++;
        }
        JOptionPane.showMessageDialog(null, "Batch of " + limit + " messages completed.");
    }
}