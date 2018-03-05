package star.programmers.annaabi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;

import java.util.List;

@RestController
public class FileListController
{
    @Autowired
    UploadRepository uploadRepository;

    @CrossOrigin
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

    @CrossOrigin
    @GetMapping("/uploadPage")
    public String uploadPage()
    {
        // temporary
        return "" +
                "\t\t<form method=\"POST\" enctype=\"multipart/form-data\" action=\"/api/uploadFile\">\n" +
                "\t\t\t<table>\n" +
                "\t\t\t\t<tr><td>File to upload:</td><td><input type=\"file\" name=\"file\" /></td></tr>\n" +
                "\t\t\t\t<tr><td></td><td><input type=\"submit\" value=\"Upload\" /></td></tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t</form>";
    }
}
