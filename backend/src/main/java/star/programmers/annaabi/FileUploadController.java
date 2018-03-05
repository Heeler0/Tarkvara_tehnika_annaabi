// Based on: https://spring.io/guides/gs/uploading-files/

package star.programmers.annaabi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;
import star.programmers.annaabi.storage.StorageService;
import star.programmers.annaabi.storage.exceptions.StorageFileNotFoundException;

// Based on: https://spring.io/guides/gs/uploading-files/
@Controller
public class FileUploadController
{
    private final StorageService storageService;

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    public FileUploadController(StorageService storageService)
    {
        this.storageService = storageService;
    }

    // Get a file with a specific name
    @CrossOrigin
    @GetMapping("/api/getFile/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename)
    {
        Resource file = storageService.loadAsResource(filename);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    // Upload a file
    @CrossOrigin
    @PostMapping("/api/uploadFile")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes)
    {
        // make sure the file is valid before saving
        if (!isValidFile(file))
        {
            System.out.println("File: " + file.getOriginalFilename() + " is not valid with content type: " + file.getContentType());
            return "";
        }

        // save file to storage
        storageService.store(file);

        // save file to database
        Upload upload = new Upload();
        upload.setFileName(file.getOriginalFilename());
        upload.setFileSize((int) file.getSize());
        upload.setFileDescription("File: " + file.getOriginalFilename());
        upload.setUploadDate(System.currentTimeMillis() / 1000);
        uploadRepository.save(upload);
        System.out.println("Saved file: " + upload.getFileName());

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/uploadPage";
    }

    public boolean isValidFile(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (fileName.endsWith(".pdf") && contentType.equals("application/pdf"))
            return true;

        if (fileName.endsWith(".docx") && contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            return true;

        return false;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }
}
