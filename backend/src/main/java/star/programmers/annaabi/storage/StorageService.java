package star.programmers.annaabi.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

// Based on: https://spring.io/guides/gs/uploading-files/
public interface StorageService
{

    void init();

    void store(MultipartFile file, String filename);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

    void delete(String filename);

}
