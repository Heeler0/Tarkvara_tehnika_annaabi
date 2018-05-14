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

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTests
{
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository repository;

    @Test
    public void testAccountRepositoryFindByNameAndPassword() throws Exception {
        Account account = new Account();
        account.setName("test_name");
        account.setPassword("test_password");

        this.entityManager.persist(account);
        List<Account> accounts = this.repository.findByNameAndPassword("test_name", "test_password");
        assert(accounts.size() == 1);
        assert(accounts.get(0).getName().equals("test_name"));
        assert(accounts.get(0).getPassword().equals("test_password"));
    }

    @Test
    public void testAccountRepositoryFindByToken() throws Exception {
        Account account = new Account();
        account.setToken("123456789");

        this.entityManager.persist(account);
        List<Account> accounts = this.repository.findByToken("123456789");
        assert(accounts.size() == 1);
        assert(accounts.get(0).getToken().equals("123456789"));
    }
}
