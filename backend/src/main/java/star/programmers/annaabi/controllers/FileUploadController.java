// Based on: https://spring.io/guides/gs/uploading-files/

package star.programmers.annaabi.controllers;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;
import star.programmers.annaabi.database.Account;
import star.programmers.annaabi.database.AccountRepository;
import star.programmers.annaabi.database.Upload;
import star.programmers.annaabi.database.UploadRepository;
import star.programmers.annaabi.storage.StorageService;
import star.programmers.annaabi.storage.exceptions.StorageFileNotFoundException;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

// Based on: https://spring.io/guides/gs/uploading-files/
@Controller
public class FileUploadController
{
    private final StorageService storageService;

    @Autowired
    private UploadRepository uploadRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountController accountController;

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
    @RequestMapping(value = "/api/uploadFile", method = { RequestMethod.GET, RequestMethod.POST })
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
                                   @RequestParam(value = "title") String title,
                                   @RequestParam(value = "categoryId") Long categoryId,
                                   @RequestParam(value = "token") String token)
    {
        Account account = accountController.getAccountFromToken(token);

        if (account == null)
        {
            System.out.println("File: " + file.getOriginalFilename() + " tried to be uploaded from an invalid account");
            return "";
        }

        if (!isValidTitle(title))
        {
            System.out.println("File: " + file.getOriginalFilename() + " has invalid title");
            return "";
        }

        // make sure the file is valid before saving
        if (!isValidFile(file))
        {
            System.out.println("File: " + file.getOriginalFilename() + " is not valid with content type: " + file.getContentType());
            return "";
        }

        // parse the file for description
        String description = generateFileDescription(file);
        if (description == null)
        {
            System.out.println("File: " + file.getOriginalFilename() + " cannot be parsed to generate a description");
            return "";
        }

        // save file to storage
        String fileExtension;
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (i > 0)
        {
            fileExtension = file.getOriginalFilename().substring(i);
        }
        else
        {
            return "Invalid file extension.";
        }

        String filename = AccountController.generateRandomString(64) + fileExtension;
        storageService.store(file, filename);

        // save file to database
        Upload upload = new Upload();
        upload.setFileName(filename);
        upload.setTitle(title);
        upload.setFileSize((int) file.getSize());
        upload.setFileDescription(description);
        upload.setUploadDate(System.currentTimeMillis() / 1000);
        upload.setCategoryId(categoryId);
        upload.setUploaderId(account.getId());
        uploadRepository.save(upload);
        System.out.println("Saved file: " + upload.getFileName());

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:http://194.135.95.77/tarkvaratehnika/";
    }

    public String generateFileDescription(MultipartFile file)
    {
        String fileContentsAsText = getFileContentsAsText(file);

        if (fileContentsAsText == null)
            return null;

        return getRandomPartFromText(fileContentsAsText);
    }

    public String getRandomPartFromText(String text)
    {
        int descriptionLength = 512;
        if (text.length() < descriptionLength)
            return text;

        int descriptionStart = ThreadLocalRandom.current().nextInt(0, text.length() - descriptionLength);

        while (descriptionStart > 0 && text.charAt(descriptionStart) != ' ')
        {
            // loop backwards until we encounter a space
            descriptionStart--;
        }

        int descriptionEnd = descriptionStart;
        int lineBreaks = 0;
        for (int i = descriptionStart; i < descriptionStart + descriptionLength; i++)
        {
            char c = text.charAt(i);

            if (c == '\n')
                lineBreaks++;

            if (lineBreaks >= 10)
            {
                if (c == ' ' || descriptionEnd - descriptionStart > descriptionLength * 1.2)
                    break;
            }

            descriptionEnd++;
        }

        String description = "...\r\n" + text.substring(descriptionStart, descriptionEnd) + "\r\n...";
        description = HtmlUtils.htmlEscape(description);
        System.out.println("Text size: " + text.length() + " and description: " + description);
        return description;
    }

    public String getFileContentsAsText(MultipartFile file)
    {
        if (file.getOriginalFilename().endsWith(".pdf"))
            return getPdfFileContentsAsText(file);

        if (file.getOriginalFilename().endsWith(".docx"))
            return getDocxFileContentsAsText(file);

        if (file.getOriginalFilename().endsWith(".txt"))
            return getTxtFileContentsAsText(file);

        if (file.getOriginalFilename().endsWith(".ppt"))
            return getPptFileContentsAsText(file);

        if (file.getOriginalFilename().endsWith(".xls"))
            return getXlsFileContentsAsText(file);

        return null;
    }

    public String getPdfFileContentsAsText(MultipartFile file)
    {
        try
        {
            PDDocument document = PDDocument.load(file.getBytes());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            return text;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getTxtFileContentsAsText(MultipartFile file)
    {
        try
        {
            return new String(file.getBytes(), "UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getPptFileContentsAsText(MultipartFile file)
    {
        try
        {
            POITextExtractor extractor = ExtractorFactory.createExtractor(file.getInputStream());
            return extractor.getText();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getDocxFileContentsAsText(MultipartFile file)
    {
        try
        {
            POITextExtractor extractor = ExtractorFactory.createExtractor(file.getInputStream());
            return extractor.getText();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public String getXlsFileContentsAsText(MultipartFile file)
    {
        try
        {
            POITextExtractor extractor = ExtractorFactory.createExtractor(file.getInputStream());
            return extractor.getText();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isValidFile(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();

        if (fileName.endsWith(".pdf") && contentType.equals("application/pdf"))
            return true;

        if (fileName.endsWith(".docx") && contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
            return true;

        if (fileName.endsWith(".txt") && contentType.equals("text/plain"))
            return true;

        if (fileName.endsWith(".ppt") && contentType.equals("application/vnd.ms-powerpoint"))
            return true;

        if (fileName.endsWith(".xls") && contentType.equals("application/vnd.ms-excel"))
            return true;

        return false;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc)
    {
        return ResponseEntity.notFound().build();
    }

    public boolean isValidTitle(String title)
    {
        return !title.isEmpty();
    }
}
