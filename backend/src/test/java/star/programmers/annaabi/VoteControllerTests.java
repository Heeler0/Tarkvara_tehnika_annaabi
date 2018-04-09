package star.programmers.annaabi;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import star.programmers.annaabi.controllers.AccountController;
import star.programmers.annaabi.controllers.VoteController;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class VoteControllerTests
{
    @Test
    public void testIsValidScoreOk()
    {
        assertEquals(true, VoteController.isValidScore(1));
    }

    @Test
    public void testIsValidScoreOkNegative()
    {
        assertEquals(true, VoteController.isValidScore(-1));
    }

    @Test
    public void testIsValidScoreOutOfBounds()
    {
        assertEquals(false, VoteController.isValidScore(13));
    }
}
