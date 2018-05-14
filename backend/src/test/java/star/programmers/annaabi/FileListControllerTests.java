package star.programmers.annaabi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import star.programmers.annaabi.controllers.FileListController;
import star.programmers.annaabi.database.*;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class FileListControllerTests
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UploadRepository repository;

    @Autowired
    private FileListController controller;

    @Test
    public void testSearch() throws Exception {
        Upload upload = new Upload();
        upload.setTitle("Test File 9513");

        this.entityManager.persist(upload);

        String json = controller.getFileList("Test File 9513", null);
        assert(json.contains("\"title\" : \"Test File 9513\""));
    }

    @Test
    public void testCategoryFiltering() throws Exception {
        Upload upload = new Upload();
        upload.setTitle("Test File 12411");
        upload.setCategoryId(12L);

        this.entityManager.persist(upload);

        String json = controller.getFileList("", 1242134L);
        assert(!json.contains("\"title\" : \"Test File 12411\""));
    }
}
