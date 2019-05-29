package com.san.alfresco.fileupload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NodeMetaData {
    private String id;

    private String name;

    private String nodeType;

    private String parentId;

    private Content content;

    @Override
    public String toString() {
        return "NodeMetaData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nodeType='" + nodeType + '\'' +
                ", parentId='" + parentId + '\'' +
                ", content=" + (content != null? content.toString() : "") +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "NodeMetaData{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", nodeType='" + nodeType + '\'' +
//                ", parentId='" + parentId + '\'' +
//                '}';
//    }

}
