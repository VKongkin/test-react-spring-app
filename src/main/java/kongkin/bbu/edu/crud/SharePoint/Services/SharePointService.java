package kongkin.bbu.edu.crud.SharePoint.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SharePointService {

    @Value("${sharepoint.tenant-id}")
    private String tenantId;

    @Value("${sharepoint.client-id}")
    private String clientId;

    @Value("${sharepoint.client-secret}")
    private String clientSecret;

    @Value("${sharepoint.site-id}")
    private String siteId;

    @Value("${sharepoint.drive-id}")
    private String driveId;

    private final SharePointAuthService authService;

    public SharePointService(SharePointAuthService authService) {
        this.authService = authService;
    }

    public String uploadFile(MultipartFile file, String filename) throws IOException {
        String accessToken = authService.getAccessToken().getAccess_token();
        String url = "https://graph.microsoft.com/v1.0/sites/" + siteId + "/drives/" + driveId + "/root:/Shared Documents/images/" + filename + ":/content";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "image/jpeg");

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        return response.getBody(); // You can return the response body or handle it as needed.
    }

    public byte[] getImageFile(String fileName) {
        String accessToken = authService.getAccessToken().getAccess_token();
        String fileUrl = "https://graph.microsoft.com/v1.0/sites/" + siteId + "/drives/" + driveId + "/root:/Shared Documents/images/" + fileName + ":/content";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Accept", "image/jpeg");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ByteArrayResource> response = restTemplate.exchange(fileUrl, HttpMethod.GET, entity, ByteArrayResource.class);

        return response.getBody().getByteArray();
    }
}