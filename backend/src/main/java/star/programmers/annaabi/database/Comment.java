package star.programmers.annaabi.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private Long accountId;
    private String comment;
    private Long fileId;
    private Long commentDate;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Long accountId)
    {
        this.accountId = accountId;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Long getFileId()
    {
        return fileId;
    }

    public void setFileId(Long fileId)
    {
        this.fileId = fileId;
    }

    public Long getCommentDate()
    {
        return commentDate;
    }

    public void setCommentDate(Long commentDate)
    {
        this.commentDate = commentDate;
    }
}
