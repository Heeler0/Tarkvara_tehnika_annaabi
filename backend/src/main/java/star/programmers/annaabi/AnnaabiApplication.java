package star.programmers.annaabi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import star.programmers.annaabi.storage.FileSystemStorageService;
import star.programmers.annaabi.storage.StorageProperties;
import star.programmers.annaabi.storage.StorageService;

@SpringBootApplication
@ComponentScan({"star.programmers.annaabi", "star.programmers.annaabi.database",
        "star.programmers.annaabi.storage", "star.programmers.annaabi.storage.exceptions"})
public class AnnaabiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(AnnaabiApplication.class, args);
    }

    @Bean
    public StorageProperties storageProperties()
    {
        return new StorageProperties();
    }
}
