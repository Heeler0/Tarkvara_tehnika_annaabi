package star.programmers.annaabi.database;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>
{
    List<Category> findAllByOrderByName();
}
