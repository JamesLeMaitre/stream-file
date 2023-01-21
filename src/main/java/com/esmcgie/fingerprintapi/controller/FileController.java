package com.esmcgie.fingerprintapi.controller;

import com.esmcgie.fingerprintapi.service.FileService;
import com.esmcgie.fingerprintapi.utiles.DataFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api")
public class FileController extends DataFormatter<String> {
    private final FileService fileService;
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @GetMapping("/file")
//    public ResponseEntity<Resource> getFile(@RequestParam String fileName) {
//        File file = fileService.getFile(fileName);
//        if(file.exists()) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
//            return ResponseEntity.ok().headers(headers).body((Resource) new FileSystemResource(file));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/rename")
    public Object renameFile() {
        try{
            fileService.getFile();
            return renderData(true, fileService.getFile(), "Done !");
        } catch (Exception e){
            return renderStringData(false, String.valueOf(e),"Error");
        }

    }

    @GetMapping("/getFileName")
    public Object getFile() {
        DataFormatter<Flux<String>> df = new DataFormatter<>();
        try{
            return df.renderData(true,fileService.getFileNames(),"Done");
        }catch (Exception e){
            return renderStringData(false, String.valueOf(e),"Error");
        }
    }

    @PutMapping("/renameFile")
    public Object renameFilesInFolder() {
        DataFormatter<Mono<Void>> df = new DataFormatter<>();
        try{
            return df.renderData(true,fileService.renameFilesInFolder(),"Done");
        }catch (Exception e){
            return renderStringData(false, String.valueOf(e),"Not Done");
        }
    }

    @PutMapping("/renameAndMove")
    public Mono<Void> renameAndMoveFilesInFolder() {
        return fileService.renameAndMoveFilesInFolder();
    }
}

