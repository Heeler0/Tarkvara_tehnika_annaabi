package star.programmers.annaabi;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import star.programmers.annaabi.controllers.AccountController;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AccountControllerTests
{
    @Test
    public void testIsValidNameOk()
    {
        assertEquals(true, AccountController.isValidName("superuser"));
    }

    @Test
    public void testIsValidNameFail()
    {
        assertEquals(false, AccountController.isValidName(""));
    }

    @Test
    public void testIsValidNameNull()
    {
        assertEquals(false, AccountController.isValidName(null));
    }

    @Test
    public void testIsValidEmailOk()
    {
        assertEquals(true, AccountController.isValidEmail("superuser@mail.com"));
    }

    @Test
    public void testIsValidEmailFail()
    {
        assertEquals(false, AccountController.isValidEmail(""));
    }

    @Test
    public void testIsValidEmailNull()
    {
        assertEquals(false, AccountController.isValidEmail(null));
    }

    @Test
    public void testIsValidPasswordOk()
    {
        assertEquals(true, AccountController.isValidPassword("par0acl210xck"));
    }

    @Test
    public void testIsValidPasswordFail()
    {
        assertEquals(false, AccountController.isValidPassword(""));
    }

    @Test
    public void testIsValidPasswordNull()
    {
        assertEquals(false, AccountController.isValidPassword(null));
    }

    @Test
    public void testGenerateRandomString()
    {
        assertEquals(16, AccountController.generateRandomString(16).length());
    }

    @Test
    public void testGenerateRandomStringEmpty()
    {
        assertEquals(true, AccountController.generateRandomString(0).isEmpty());
    }

    @Test
    public void testHashPassword()
    {
        String hashedPassword = "";

        try
        {
            hashedPassword = AccountController.hashPassword("parool");
        }
        catch (Exception e) { }

        assertEquals("365bdc6ae8c657d005aefebe0e904766c1d7222251738a317671cd0dac96d50d", hashedPassword);
    }
}
