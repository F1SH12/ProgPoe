/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package parttwo;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_lab
 */
public class LoginNGTest {
    
    public LoginNGTest() {
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


    @Test
    public void testCheckUsernameCorrect() {
        System.out.println("checkUsername");
        String username = "kyl_1";
        boolean expResult = true;
        boolean result = Login.checkUsername(username);
        assertEquals(result, expResult);

    }
    
        @Test
    public void testCheckUsernameIncorrect() {
        System.out.println("checkUsername");
        String username = "kyle!!!!!!!";
        boolean expResult = false;
        boolean result = Login.checkUsername(username);
        assertEquals(result, expResult);

    }
    
            @Test
    public void testCheckUsername_noUnderscore() {
        System.out.println("checkUsername");
        String username = "kyle";
        boolean expResult = false;
        boolean result = Login.checkUsername(username);
        assertEquals(result, expResult);

    }

    @Test
    public void testCheckPasswordComplexityCorrect() {
        System.out.println("checkPasswordComplexity");
        String password = "Ch&&sec@ke99!";
        boolean expResult = true;
        boolean result = Login.checkPasswordComplexity(password);
        assertEquals(result, expResult);

    }
    
        @Test
    public void testCheckPasswordComplexityIncorrect() {
        System.out.println("checkPasswordComplexity");
        String password = "password";
        boolean expResult = false;
        boolean result = Login.checkPasswordComplexity(password);
        assertEquals(result, expResult);

    }


    @Test
    public void testCheckCellphoneNumberCorrect() {
        System.out.println("checkCellphoneNumber");
        String cellphoneNumber = "+27838968976";
        boolean expResult = true;
        boolean result = Login.checkCellphoneNumber(cellphoneNumber);
        assertEquals(result, expResult);

    }
    
        @Test
    public void testCheckCellphoneNumberIncorrect() {
        System.out.println("checkCellphoneNumber");
        String cellphoneNumber = "08966553";
        boolean expResult = false;
        boolean result = Login.checkCellphoneNumber(cellphoneNumber);
        assertEquals(result, expResult);

    }


    @Test
    public void testRegisterUser() {
        System.out.println("registerUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99";
        String cellphoneNumber = "+27838968976";
        String firstName = "raiyan";
        String lastName = "patel";
        String expResult = "true";
        String result = Login.registerUser(username, password, cellphoneNumber, firstName, lastName);
        assertEquals(result, expResult);

    }


    @Test
     public void testLoginUserSuccessful() {
        System.out.println("loginUser");
        String username = "kyl_1";
        String password = "Ch&&sec@ke99";
        String expResult = "True";
        String result = Login.loginUser(username, password) ;
        assertEquals(result, expResult);

    }
    
        @Test
    public void testLoginUserFailed() {
        System.out.println("loginUser");
        String username = "kyle!!!!!!!";
        String password = "password";
        String expResult = "false";
        String result = Login.loginUser(username, password) ;
        assertEquals(result, expResult);

    }
    
}
