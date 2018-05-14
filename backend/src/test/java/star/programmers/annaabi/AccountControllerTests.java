package star.programmers.annaabi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import star.programmers.annaabi.controllers.AccountController;
import star.programmers.annaabi.database.Account;
import star.programmers.annaabi.database.AccountRepository;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AccountControllerTests
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountController controller;

    @Mock
    private WebRequest webRequest;

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

    @Test
    public void testLogin() throws Exception {
        Mockito.when(webRequest.getParameter("name")).thenReturn("test_name");
        Mockito.when(webRequest.getParameter("password")).thenReturn("test_password");

        Account account = new Account();
        account.setName("test_name");
        account.setPassword(AccountController.hashPassword("test_password"));

        this.entityManager.persist(account);

        System.out.println(controller.login(webRequest));
    }
}
