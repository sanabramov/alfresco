package com.san.alfresco.fileupload;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = NodeContentService.class)
public class NodeContentServiceTest {

    private final String testFolderName = "TestFolder";

    private final String RUNID = System.currentTimeMillis()+"";

    private final String parentFolderId = "c36f163c-17a9-40ab-bdf7-9adb66c63ae3";

    private String testFolderId;

    private final String testNodeName = "TestNode";

    private final String fileName = "IMG_20190317_185329.jpg";


    @Autowired
    private NodeContentService nodeContentService;

    @Before
    public void shouldAddAlfrescoFolder() {
        testFolderId = nodeContentService.addAlfrescoElement(parentFolderId, testFolderName, true);

        System.out.println("NodeId: " + testFolderId);

        assertThat(testFolderId, not(isEmptyOrNullString()));
    }

    @Test
    public void shouldAddAlfrescoNode() throws IOException, URISyntaxException {
        String newElementId = nodeContentService.addAlfrescoElement(testFolderId, testNodeName, false);
        System.out.println("NodeId: " + newElementId);
        assertThat(newElementId, not(isEmptyOrNullString()));
        final Path path = Paths.get(this.getClass().getResource("/" + fileName).toURI());
        nodeContentService.setNodeTextContent(newElementId, path, MediaType.IMAGE_JPEG);
        assertThat(newElementId, not(isEmptyOrNullString()));
    }


}