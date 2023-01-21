package com.esmcgie.fingerprintapi.interfaces;

import com.esmcgie.fingerprintapi.request.FileRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FileInterface {
    public String getFile();
//    Mono<?> getFilesNames(Mono<FileRequest> requestMono);

    Flux<String> getFileNames();
    public Mono<Void> renameFilesInFolder();
}
