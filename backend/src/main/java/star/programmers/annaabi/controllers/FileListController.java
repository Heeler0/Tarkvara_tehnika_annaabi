package star.programmers.annaabi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;
import star.programmers.annaabi.database.Vote;
import star.programmers.annaabi.database.VoteRepository;

import java.util.List;

@RestController
public class FileListController
{
    @Autowired
    UploadRepository uploadRepository;
    @Autowired
    VoteRepository voteRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/getFileList", method = RequestMethod.GET)
    @ResponseBody
    public String getFileList(
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "categoryId", required = false) Long categoryId)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            // get requested rows from upload table
            List<Upload> uploads;

            if (query != null && categoryId != null)
            {
                uploads = uploadRepository.findByFileNameContainingIgnoreCaseAndCategoryIdOrderByIdDesc(query, categoryId);
            }
            else if (query != null)
            {
                uploads = uploadRepository.findByFileNameContainingIgnoreCaseOrderByIdDesc(query);
            }
            else if (categoryId != null)
            {
                uploads = uploadRepository.findByCategoryIdOrderByIdDesc(categoryId);
            }
            else
            {
                uploads = uploadRepository.findAllByOrderByIdDesc();
            }

            // fetch vote counts for every upload
            for (Upload upload : uploads)
            {
                Long voteCount = voteRepository.findSumOfScore(upload.getId());

                if (voteCount != null)
                {
                    upload.setVoteCount(voteCount);
                }
            }

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
                "\t\t<form method=\"POST\" enctype=\"multipart/form-data\" action=\"/api/uploadFile?title=Test\">\n" +
                "\t\t\t<table>\n" +
                "\t\t\t\t<tr><td>File to upload:</td><td><input type=\"file\" name=\"file\" /></td></tr>\n" +
                "\t\t\t\t<tr><td></td><td><input type=\"submit\" value=\"Upload\" /></td></tr>\n" +
                "\t\t\t</table>\n" +
                "\t\t</form>";
    }
}
