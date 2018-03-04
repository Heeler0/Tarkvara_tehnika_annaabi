package star.programmers.annaabi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;

import java.util.List;

@RestController
public class MainController
{
    @Autowired
    UploadRepository uploadRepository;

    @RequestMapping("/api/getFileList")
    public String getFileList()
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            // get all rows from uploads table
            List<Upload> uploads = uploadRepository.findAll();

            // serialize data in json format for javascript to parse
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uploads);
        }
        catch (JsonProcessingException e)
        {
            return e.getMessage();
        }
    }
}
