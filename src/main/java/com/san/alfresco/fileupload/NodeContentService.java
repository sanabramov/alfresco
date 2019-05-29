package com.san.alfresco.fileupload;

import com.san.alfresco.fileupload.dto.EntryMetaData;
import com.san.alfresco.fileupload.dto.NodeMetaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:/application.properties")
public class NodeContentService {

    @Value("${alfresco.repositoryURL}")
    private String alfrescoRepositoryURL;

    @Value("${alfresco.username}")
    private String username;

    @Value("${alfresco.password}")
    private String password;

    public String setNodeTextContent(final String nodeId, Path path, MediaType mediaType) throws IOException, URISyntaxException {
        String requestUrl = String.format("%s/nodes/%s/content", alfrescoRepositoryURL, nodeId);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("majorVersion", "false");

        byte[] bFile = Files.readAllBytes(path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", getAuthString());
        HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity(bFile, headers);

        NodeMetaData nodeMetaDataResponse = callService(requestUrl, HttpMethod.PUT, httpEntity, paramMap);

        return nodeMetaDataResponse.getId();
    }


    public String addAlfrescoElement(final String parentId, final String folderName, boolean isFolder) {
        String requestUrl = String.format("%s/nodes/%s/children?autoRename=true", alfrescoRepositoryURL, parentId);
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("autoRename", "true");

        NodeMetaData nodeMetaData = new NodeMetaData();
        nodeMetaData.setName(folderName);
        nodeMetaData.setNodeType(isFolder ? "cm:folder" : "cm:content");
//        if (! isFolder) {
//            Content content = new Content();
//            content.setEncoding("UTF-8");
//            content.setMimeType("text/plain");
//            content.setMimeTypeName("Plain Text");
//            content.setSizeInBytes(3);
//            nodeMetaData.setContent(content);
//        }

        NodeMetaData nodeMetaDataResponse = callService(requestUrl, HttpMethod.POST, createHeader(nodeMetaData), paramMap);

        return nodeMetaDataResponse.getId();
    }


    private NodeMetaData callService(String requestUrl, HttpMethod httpMethod, HttpEntity httpEntity, Map<String, String> paramMap) {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<EntryMetaData> responseEntity = restTemplate.exchange(requestUrl, httpMethod, httpEntity, EntryMetaData.class, paramMap);

        System.out.println("node: " + responseEntity.getBody());

        NodeMetaData responseBody = null;
        if (responseEntity.getBody() != null) {
            responseBody = responseEntity.getBody().getEntry();
        }

        return responseBody;
    }

    private HttpEntity<NodeMetaData> createHeader(NodeMetaData body) {
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", getAuthString());
        HttpEntity<NodeMetaData> httpEntity = new HttpEntity(body, headers);
        return httpEntity;
    }

    private String getAuthString() {
        return "Basic " + DatatypeConverter.printBase64Binary((username + ":" + password).getBytes());
    }

}
