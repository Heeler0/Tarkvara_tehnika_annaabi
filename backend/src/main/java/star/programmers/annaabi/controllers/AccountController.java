package star.programmers.annaabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import star.programmers.annaabi.database.Account;
import star.programmers.annaabi.database.AccountRepository;
import star.programmers.annaabi.database.Upload;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class AccountController
{
    @Autowired
    AccountRepository accountRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/registerAccount", method = RequestMethod.POST)
    public String registerAccount(WebRequest request)
    {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        if (!isValidName(name))
            return "Invalid name.";

        if (!isValidPassword(password))
            return "Invalid password.";

        if (!isValidEmail(email))
            return "Invalid email.";

        // hash the password
        String hashedPassword;
        try
        {
            hashedPassword = hashPassword(password);
        }
        catch (Exception e)
        {
            return "Error while hashing password.";
        }

        // save account to database
        try
        {
            Account account = new Account();
            account.setName(name);
            account.setPassword(hashedPassword);
            account.setEmail(email);
            accountRepository.save(account);
        }
        catch (Exception e)
        {
            return "An account already exists with this name and/or e-mail. Please try again.";
        }

        return hashedPassword;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public String login(WebRequest request)
    {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        if (!isValidName(name))
            return "Invalid name.";

        if (!isValidPassword(password))
            return "Invalid password.";

        // hash the password
        String hashedPassword;
        try
        {
            hashedPassword = hashPassword(password);
        }
        catch (Exception e)
        {
            return "Error while hashing password.";
        }

        // check if such account exists
        List<Account> accountList = accountRepository.findByNameAndPassword(name, hashedPassword);

        if (accountList.size() == 1)
        {
            String token = generateRandomString(16);

            Account account = accountList.get(0);
            account.setToken(token);
            accountRepository.save(account);
            return token;
        }

        return "Invalid username and/or password.";
    }

    public boolean isValidName(String name)
    {
        return !name.isEmpty();
    }

    public boolean isValidPassword(String password)
    {
        return !password.isEmpty();
    }

    public boolean isValidEmail(String email)
    {
        return !email.isEmpty();
    }

    public static String hashPassword(String password) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

        return String.format("%064x", new java.math.BigInteger(1, hash));
    }

    public static String generateRandomString(int length)
    {
        String alphabet = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++)
        {
            char randomCharacter = alphabet.charAt(ThreadLocalRandom.current().nextInt(alphabet.length()));
            result.append(randomCharacter);
        }

        return result.toString();
    }
}
