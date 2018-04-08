package star.programmers.annaabi.database;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>
{
    List<Comment> findByFileIdOrderById(Long fileId);
}
