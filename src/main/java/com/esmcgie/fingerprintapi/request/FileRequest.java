package com.esmcgie.fingerprintapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRequest {
    private String fileName;
    private String filePath;
    private String newName;
    private String destinationFolder;
}
