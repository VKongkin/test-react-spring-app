package kongkin.bbu.edu.crud.SharePoint.Controllers;


import kongkin.bbu.edu.crud.SharePoint.Services.SharePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import kongkin.bbu.edu.crud.Config.webConfig;

@RestController
@RequestMapping("/api/sharepoint")
public class SharePointFileUploadController extends webConfig {

    @Autowired
    private SharePointService sharePointService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Get the original filename or generate a new one
            String filename = file.getOriginalFilename();

            // Upload the file to SharePoint
            return sharePointService.uploadFile(file, filename);
        } catch (IOException e) {
            e.printStackTrace();
            return "File upload failed: " + e.getMessage();
        }
    }

    @GetMapping("/image/{filename}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("filename") String fileName) {

        byte[] imageBytes = sharePointService.getImageFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(new ByteArrayResource(imageBytes), headers, HttpStatus.OK);
    }


}