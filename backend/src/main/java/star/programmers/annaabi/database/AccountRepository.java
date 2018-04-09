package star.programmers.annaabi.database;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<Account, Long>
{
    List<Account> findByNameAndPassword(String name, String password);
    List<Account> findByToken(String token);
}
