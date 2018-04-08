package star.programmers.annaabi.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

public interface UploadRepository extends PagingAndSortingRepository<Upload, Long>
{
    List<Upload> findByFileName(String fileName);
    List<Upload> findAllByOrderByIdDesc();

    List<Upload> findByFileNameContainingIgnoreCaseOrderByIdDesc(String query);
    List<Upload> findByFileNameContainingIgnoreCaseAndCategoryIdOrderByIdDesc(String query, Long categoryId);

    List<Upload> findByCategoryIdOrderByIdDesc(Long categoryId);
}