/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partthree;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.Random;

public class MessageManagerNGTest {

    private static Message msg1, msg2, msg3, msg4, msg5;
    private static String hash1, hash2, hash4;
    private static String id4;
    private static final String RECIPIENT_COMMON = "+27838884567"; // Used by TD2 and TD5
    
    private static class TestData {
        // TD1Sent
        public static final String M1 = "Did you get the cake?";
        
        // TD2Stored
        public static final String M2 = "Where are you? You are late! I have asked you to be on time.";
        
        // TD3Sent
        public static final String M3 = "Yohoooo, I am at your gate.";
        
        // TD4Sent
        public static final String M4 = "It is dinner time!";
        
        // TD5Stored
        public static final String M5 = "Ok, I am leaving without you.";
    }


    private Message createTestMessage(String idPrefix, int count, String recipient, String text, String flag) {

        String randomDigits = String.valueOf(new Random().nextLong()).replaceAll("[^0-9]", "").substring(0, 8);
        String messageID = idPrefix + randomDigits;
        String messageHash = Message.createMessageHash(messageID, count, text);
        Message msg = new Message(messageID, messageHash, recipient, text, flag); 
        return msg;
    }

    @BeforeClass
    public void setUp() {

        Message.messageCount = 0;
        MessageManager.sentMessages.clear();
        MessageManager.storedMessages.clear();
        MessageManager.disregardedMessages.clear();
        MessageManager.messageHashes.clear();
        MessageManager.messageIDs.clear();

        String fixedIDPrefix = "AB"; 
        
        // The numbers 1 to 5 Details
        msg1 = createTestMessage(fixedIDPrefix, 1, "+27834557896", TestData.M1, "Sent");
        msg2 = createTestMessage(fixedIDPrefix, 2, RECIPIENT_COMMON, TestData.M2, "Stored");
        msg3 = createTestMessage(fixedIDPrefix, 3, "+27834484567", TestData.M3, "Disregard");
        msg4 = createTestMessage(fixedIDPrefix, 4, "0838884567", TestData.M4, "Sent"); 
        msg5 = createTestMessage(fixedIDPrefix, 5, RECIPIENT_COMMON, TestData.M5, "Stored"); 


        hash1 = msg1.messageHash;
        hash2 = msg2.messageHash;
        hash4 = msg4.messageHash;
        id4 = msg4.messageID;
    }
    
    @Test(priority = 1, description = "Checks that only TD1 and TD4 are in the Sent array.")
    public void testSentMessagesPopulation() {

        ArrayList<String> actualMessages = new ArrayList<>();
        for (Message msg : MessageManager.sentMessages) {
            actualMessages.add(msg.messageText);
        }
        
        assertEquals(actualMessages.size(), 2, "FAIL: The Sent array should have exactly 2 messages.");
        assertTrue(actualMessages.contains(TestData.M1), "FAIL: Missing TD1 message.");
        assertTrue(actualMessages.contains(TestData.M4), "FAIL: Missing TD4 message.");
    }

    @Test(priority = 2, description = "Checks the system finds the longest message text.")
    public void testDisplayLongestMessage() {

        String expectedLongest = TestData.M2; 
        String actualLongest = MessageManager.displayLongestMessage();
        
        assertEquals(actualLongest, expectedLongest, "FAIL: The longest message found is wrong.");
    }


    @Test(priority = 3, description = "Checks that searching for TD4's ID returns its message text.")
    public void testSearchForMessageID() {

        String expectedMessageText = TestData.M4;
        String searchResult = MessageManager.searchForMessageID(id4); // Use the real ID we saved earlier
        
        assertTrue(searchResult.contains(expectedMessageText), "FAIL: The search didn't find the text for TD4.");
    }
    

    @Test(priority = 4, description = "Checks that searching for the common recipient (+27838884567) finds TD2 and TD5.")
    public void testSearchForRecipient() {

        
        String searchResult = MessageManager.searchForRecipient(RECIPIENT_COMMON);
        
        assertTrue(searchResult.contains(TestData.M2), "FAIL: Missing TD2 message for the recipient.");
        assertTrue(searchResult.contains(TestData.M5), "FAIL: Missing TD5 message for the recipient.");
    }
    

    @Test(priority = 5, description = "Checks deleting TD2, then verifies the final Sent Report is correct.")
    public void testDeleteMessageAndReport() {

        String expectedDelete = "Message \"" + TestData.M2 + "\" successfully deleted.";
        String actualDelete = MessageManager.deleteMessage(hash2);
        
        assertEquals(actualDelete, expectedDelete, "FAIL: Deletion confirmation message is wrong.");

        String report = MessageManager.displaySentMessagesReport();
        
        assertTrue(report.contains(hash1), "FAIL: TD1 hash missing from the final report.");
        assertTrue(report.contains(hash4), "FAIL: TD4 hash missing from the final report.");
        
        assertFalse(report.contains(hash2), "FAIL: Deleted message TD2 hash is still in the report!");

        long messageCountInReport = report.lines().filter(line -> line.startsWith("--- Message")).count();
        assertEquals(messageCountInReport, 2, "FAIL: Report should only list 2 messages.");
    }
}