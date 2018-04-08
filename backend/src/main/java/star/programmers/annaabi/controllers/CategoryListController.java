package star.programmers.annaabi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import star.programmers.annaabi.database.Category;
import star.programmers.annaabi.database.CategoryRepository;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;

import java.util.List;

@RestController
public class CategoryListController
{
    @Autowired
    CategoryRepository categoryRepository;

    @CrossOrigin
    @RequestMapping("/api/getCategoryList")
    public String getCategoryList()
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            // get all rows from the category table
            List<Category> categories = categoryRepository.findAllByOrderByName();

            // serialize data in json format for javascript to parse
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(categories);
        }
        catch (JsonProcessingException e)
        {
            return e.getMessage();
        }
    }
}