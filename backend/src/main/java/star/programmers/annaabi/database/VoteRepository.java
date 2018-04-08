package star.programmers.annaabi.database;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VoteRepository extends PagingAndSortingRepository<Vote, Long>
{
    List<Vote> findByFileIdAndAccountId(Long fileId, Long accountId);
    List<Vote> findByFileId(Long fileId);

    @Query(value = "select sum(score) from vote where file_id = ?1", nativeQuery = true)
    Long findSumOfScore(Long fileId);
}
