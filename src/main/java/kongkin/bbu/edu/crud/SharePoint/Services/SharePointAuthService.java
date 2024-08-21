package kongkin.bbu.edu.crud.SharePoint.Services;


import kongkin.bbu.edu.crud.SharePoint.Models.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class SharePointAuthService {

    @Value("${sharepoint.tenant-id}")
    private String tenantId;

    @Value("${sharepoint.client-id}")
    private String clientId;

    @Value("${sharepoint.client-secret}")
    private String clientSecret;

    public AccessTokenResponse getAccessToken() {
        String url = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/token";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "https://graph.microsoft.com/.default");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<AccessTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, entity, AccessTokenResponse.class);

        return response.getBody();
    }
}