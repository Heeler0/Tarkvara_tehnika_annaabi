package star.programmers.annaabi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import star.programmers.annaabi.controllers.CommentController;
import star.programmers.annaabi.database.Comment;
import star.programmers.annaabi.database.CommentRepository;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CommentControllerTests
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentController controller;

    @Autowired
    private CommentRepository repository;

    @Test
    public void testGetComments() throws Exception
    {
        Comment comment = new Comment();
        comment.setAccountId(124512L);
        comment.setFileId(12345L);
        comment.setComment("testcomment1380457u154");

        this.entityManager.persist(comment);

        String json = controller.getComments(12345L);
        assert(json.contains("\"comment\" : \"testcomment1380457u154\","));
    }
}
