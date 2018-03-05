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
    @Column(length=1024)
    private String fileDescription;
    private int fileSize;
    private Long uploadDate;

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
}
