package com.san.alfresco.fileupload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Content {

    private String encoding;

    private String mimeType;

    private String mimeTypeName;

    private int sizeInBytes;

    @Override
    public String toString() {
        return "Content{" +
                "encoding='" + encoding + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", mimeTypeName='" + mimeTypeName + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                '}';
    }
}
