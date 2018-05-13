package star.programmers.annaabi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import star.programmers.annaabi.database.Account;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;

import java.util.Objects;
import java.util.Optional;

@RestController
public class FileDeletionController
{
    @Autowired
    AccountController accountController;

    @Autowired
    UploadRepository uploadRepository;

    @CrossOrigin
    @RequestMapping(value = "/api/deleteFile", method = RequestMethod.GET)
    @ResponseBody
    public String vote(@RequestParam(value = "fileId") Long fileId, @RequestParam(value = "token") String token)
    {
        Account account = accountController.getAccountFromToken(token);

        if (account == null)
        {
            return "Invalid token.";
        }

        Optional<Upload> upload = uploadRepository.findById(fileId);

        if (!upload.isPresent())
        {
            return "Invalid file id.";
        }

        if (!Objects.equals(account.getId(), upload.get().getUploaderId()))
        {
            return "Account id is " + account.getId() + " but file " +
                    upload.get().getId() + " uploader id is " + upload.get().getUploaderId();
        }

        uploadRepository.delete(upload.get());
        return "File deleted.";
    }
}
