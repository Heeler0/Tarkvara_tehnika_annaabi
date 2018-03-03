package star.programmers.annaabi.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Upload
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String fileName;
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
}
