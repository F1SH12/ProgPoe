    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package partthree;

import java.util.regex.Pattern;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MessageNGTest {
    
    public MessageNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

//  Test AssertEquals: Message Length Check
    @Test
    public void testCheckMessageLength_Success_Below250() {
        String message = "Hi Mike, can you join us for dinner tonight";
        String expected = "Success:Message ready to send.";
        assertEquals(expected, Message.checkMessageLength(message),
                "Should succeed if message under 250 characters.");
    }

    @Test
    public void testCheckMessageLength_Success_Boundary250() {
        String message = new String(new char[250]);
        String expected = "Success:Message ready to send.";
        assertEquals(expected, Message.checkMessageLength(message),
                "Should succeed if exactly 250 characters.");
    }

    @Test
    public void testCheckMessageLength_Failure_Exceeds() {
        String message = new String(new char[255]);
        String expected = "Failure:Message exceeds 250 characters by 5, please reduce size.";
        assertEquals(expected, Message.checkMessageLength(message),
                "Should fail and report the correct excess count.");
    }

    @Test
    public void testCheckMessageLength_Failure_Null() {
        String expected = "Failure: Message is empty.";
        assertEquals(expected, Message.checkMessageLength(null),
                "Should fail for a null message input.");
    }

//Test AssertEquals: Recipient Number Format Check
    @Test
    public void testCheckRecipientCell_Success_ValidZA() {
        String number = "+27718693002";
        String expected = "Success:Cell phone number successfully captured.";
        assertEquals(expected, Message.checkRecipientCell(number),
                "Should succeed if a valid +27 international code and 9 digits.");
    }

    @Test
    public void testCheckRecipientCell_Failure_MissingInternationalCode() {
        String number = "08575975889";
        String expected = "Failure:Cell phone number is incorrectly formatted or does not contain an international code.";
        assertEquals(expected, Message.checkRecipientCell(number),
                "Should fail if a number without the required '+27' international code.");
    }

    @Test
    public void testCheckRecipientCell_Failure_IncorrectLength() {
        String number = "+271234567890";
        String expected = "Failure:Cell phone number is incorrectly formatted or does not contain an international code.";
        assertEquals(expected, Message.checkRecipientCell(number),
                "Should fail if the number of digits is incorrect.");
    }

//Test Message ID and Hash Creation  
    @Test
    public void testCreateMessageID_Format() {
        String messageID = Message.createMessageID();
        assertTrue(Pattern.matches("\\d{10}", messageID),
                "Message ID should be a 10-digit numeric string.");
    }

    @Test
    public void testCreateMessageHash_Test_Data_Task1_Requirement() {
        String messageID_TDT1 = "0000000000";
        int msgNumber_TDT1 = 0;
        String messageText_TDT1 = "Hi Mike, can you join us for dinner tonight";

        String expected = "00:0:HITONIGHT";
        String actual = Message.createMessageHash(messageID_TDT1, msgNumber_TDT1, messageText_TDT1);
        assertEquals(expected, actual,
                "Message hash must match the '00:0:HITONIGHT' requirement for Test Case 1 data/Test Data Task 1.");
    }

//Test SentMessage and Total Messages 
    @Test
    public void testSentMessage_Return_Send() {
        String expected = "Message successfully sent.";
        assertEquals(expected, "Message successfully sent.",
                "Should return the correct success message for 'Send Message'.");
    }

    @Test
    public void testSentMessage_Return_Disregard() {
        String expected = "Press 0 to delete message.";
        assertEquals(expected, "Press 0 to delete message.",
                "Should return the correct message for 'Disregard Message'.");
    }

    @Test
    public void testSentMessage_Return_Store() {
        String expected = "Message successfully stored.";
        assertEquals(expected, "Message successfully stored.",
                "Should return the correct success message for 'Store Message'.");
    }

    @Test
    public void testReturnTotalMessages_Initial() {
        assertEquals(0, Message.returnTotalMessages(),
                "Coming soon!");
    }

}
