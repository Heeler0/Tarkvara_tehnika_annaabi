package star.programmers.annaabi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;
import star.programmers.annaabi.database.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CommentController
{
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountController accountController;

    @Autowired
    UploadRepository uploadRepository;

    @CrossOrigin
    @RequestMapping("/api/getComments")
    public String getComments(@RequestParam(value = "fileId") Long fileId)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            // get all rows from the category table
            List<Comment> comments = commentRepository.findByFileIdOrderById(fileId);

            for (Comment comment : comments)
            {
                Optional<Account> account = accountRepository.findById(comment.getAccountId());

                if (account.isPresent())
                {
                    comment.setAuthorName(account.get().getName());
                }
            }

            // serialize data in json format for javascript to parse
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(comments);
        }
        catch (JsonProcessingException e)
        {
            return e.getMessage();
        }
    }

    @CrossOrigin
    @RequestMapping("/api/postComment")
    public String postComment(@RequestParam(value = "fileId") Long fileId,
                              @RequestParam(value = "comment") String content,
                              @RequestParam(value = "token") String token)
    {
        Account account = accountController.getAccountFromToken(token);

        if (account == null)
        {
            return "Invalid token.";
        }

        Optional<Upload> upload = uploadRepository.findById(fileId);

        if (!upload.isPresent())
            return "Invalid file id.";

        if (content.isEmpty())
            return "Invalid content.";

        // save new vote to database
        Comment comment = new Comment();
        comment.setFileId(fileId);
        comment.setComment(content);
        comment.setCommentDate(System.currentTimeMillis() / 1000);
        comment.setAccountId(account.getId());
        commentRepository.save(comment);

        return "Successfully commented!";
    }
}
