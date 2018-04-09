package star.programmers.annaabi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.programmers.annaabi.database.*;

import java.util.List;
import java.util.Optional;

@RestController
public class FileListController
{
    @Autowired
    UploadRepository uploadRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AccountRepository accountRepository;

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
                uploads = uploadRepository.findByTitleContainingIgnoreCaseAndCategoryIdOrderByIdDesc(query, categoryId);
            }
            else if (query != null)
            {
                uploads = uploadRepository.findByTitleContainingIgnoreCaseOrderByIdDesc(query);
            }
            else if (categoryId != null)
            {
                uploads = uploadRepository.findByCategoryIdOrderByIdDesc(categoryId);
            }
            else
            {
                uploads = uploadRepository.findAllByOrderByIdDesc();
            }

            // fetch vote counts for every upload and update category
            for (Upload upload : uploads)
            {
                Long voteCount = voteRepository.findSumOfScore(upload.getId());

                if (voteCount != null)
                {
                    upload.setVoteCount(voteCount);
                }

                Long uploadCategoryId = upload.getCategoryId();
                if (uploadCategoryId != null)
                {
                    Optional<Category> category = categoryRepository.findById(uploadCategoryId);

                    if (category.isPresent())
                    {
                        upload.setCategoryName(category.get().getName());
                    }
                }

                Long uploaderId = upload.getUploaderId();
                if (uploaderId != null)
                {
                    Optional<Account> account = accountRepository.findById(uploaderId);

                    if (account.isPresent())
                    {
                        upload.setUploaderName(account.get().getName());
                    }
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
