package star.programmers.annaabi.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

// Based on: https://spring.io/guides/gs/uploading-files/
@ConfigurationProperties("storage")
public class StorageProperties
{
    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
}
