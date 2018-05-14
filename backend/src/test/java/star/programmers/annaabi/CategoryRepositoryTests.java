package star.programmers.annaabi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import star.programmers.annaabi.database.Account;
import star.programmers.annaabi.database.AccountRepository;
import star.programmers.annaabi.database.Category;
import star.programmers.annaabi.database.CategoryRepository;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepositoryTests
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CategoryRepository repository;

    @Test
    public void testCategoryRepositoryFindAllOrderByName() throws Exception {
        Category category = new Category();

        this.entityManager.persist(category);

        List<Category> categories = this.repository.findAllByOrderByName();
        System.out.println(categories.size());
        assert(categories.size() > 0);
    }
}
