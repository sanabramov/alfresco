package com.san.alfresco.fileupload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntryMetaData {

    private NodeMetaData entry;

    @Override
    public String toString() {
        return "EntryMetaData{" +
                "nodeMetaData=" + (entry != null ? entry.toString() : "") +
                '}';
    }
}
