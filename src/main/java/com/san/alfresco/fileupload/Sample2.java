package com.san.alfresco.fileupload;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;

public class Sample2 {
    @ResponseBody
    @RequestMapping(value = "/upload/", method = RequestMethod.POST,
            produces = "text/plain")
    public String uploadFile(MultipartHttpServletRequest request)
            throws IOException {

        Iterator<String> itr = request.getFileNames();

        MultipartFile file = request.getFile(itr.next());
        MultiValueMap<String, Object> parts =
                new LinkedMultiValueMap<String, Object>();
        parts.add("file", new ByteArrayResource(file.getBytes()));
        parts.add("filename", file.getOriginalFilename());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<MultiValueMap<String, Object>>(parts, headers);

        // file upload path on destination server
        parts.add("destination", "./");

        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:8080/pi",
                        HttpMethod.POST, requestEntity, String.class);

        if (response != null && !response.getBody().trim().equals("")) {
            return response.getBody();
        }

        return "error";
    }
}
