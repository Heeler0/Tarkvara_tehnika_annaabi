package star.programmers.annaabi.database;

import java.util.List;

import javax.persistence.*;

@Entity
public class Upload
{
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long id;

    private String fileName;
    private String title;
    @Column(length=1024)
    private String fileDescription;
    private int fileSize;
    private Long uploadDate;
    private Long categoryId;
    private Long uploaderId;
    @Transient
    private Long voteCount = 0L;
    @Transient
    private String categoryName;
    @Transient
    private String uploaderName;

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public int getFileSize()
    {
        return fileSize;
    }

    public void setFileSize(int fileSize)
    {
        this.fileSize = fileSize;
    }

    public Long getUploadDate()
    {
        return uploadDate;
    }

    public void setUploadDate(Long uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFileDescription()
    {
        return fileDescription;
    }

    public void setFileDescription(String fileDescription)
    {
        this.fileDescription = fileDescription;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getUploaderId()
    {
        return uploaderId;
    }

    public void setUploaderId(Long uploaderId)
    {
        this.uploaderId = uploaderId;
    }

    public Long getVoteCount()
    {
        return voteCount;
    }

    public void setVoteCount(Long voteCount)
    {
        this.voteCount = voteCount;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getUploaderName()
    {
        return uploaderName;
    }

    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }
}
