package com.esmcgie.fingerprintapi.service;

import com.esmcgie.fingerprintapi.interfaces.FileInterface;
import com.esmcgie.fingerprintapi.utiles.JavaUtiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;


@Service
@Slf4j
public class FileService implements FileInterface {
    @Value("${file.upload-dir}")
    private String uploadDir;



    @Override
    public String getFile() {
        String path = JavaUtiles.USER_PATH+"user/images/";
        Path folderPath = Paths.get(path);
        try(Stream<Path> paths = Files.list(folderPath)) {
            paths.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }

//    @Override
//    public Mono<String> getFilesNames(Mono<FileRequest> requestMono) {
////        requestMono.mapNotNull(fileRequest -> {
////            File file = new File(JavaUtiles.USER_PATH+"/user/images");
////            return file.listFiles((dir, name) -> name.endsWith(".PNG"));
////        }).flatMapIterable(files -> {
////
////        }).subscribe();
//        return requestMono.map(r->{
//            File folder = new File(JavaUtiles.USER_PATH+"/user/images");
//            File[] files = folder.listFiles((dir, name) -> name.endsWith(".PNG"));
//            HashMap<String,File[]> fileName = new HashMap<>();
//            fileName.put("1",files);
//            return fileName;
//        }).map().entrySet().stream()
//                .map(entry -> entry.getKey() + "=" + entry.getValue())
//                .collect(Collectors.toList());
//    }

    @Override
    public Flux<String> getFileNames() {
        String folderPath = JavaUtiles.USER_PATH+"/user/images/";
        return Flux.fromStream(
                Stream.of(Objects.requireNonNull(new File(folderPath).listFiles()))
                        .filter(File::isFile)
                        .map(File::getName)
        );
    }

    @Override
    public Mono<Void> renameFilesInFolder( ) {
        String folderPath = JavaUtiles.USER_PATH+"/user/images";
        String newNamePrefix = "1__M_Left_middle_finger";
        return Mono.fromRunnable(() -> {
            File folder = new File(folderPath);
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String newFileName = newNamePrefix + fileName;

                    file.renameTo(new File(folderPath + "/" + newFileName));
                    log.info("{}",file);
                }
            }
        });
    }

    public Mono<Void> renameAndMoveFilesInFolder() {
        String folderPath = JavaUtiles.USER_PATH+"/user/images";
        String newNamePrefix = "1__M_Left_middle_finger";
        //String destinationFolder = JavaUtiles.USER_PATH+"/user/rename/";

        return Mono.fromRunnable(() -> {
            File folder = new File(folderPath);
            File dest = new File(JavaUtiles.USER_PATH+"/user/rename/");
            if (!dest.exists()) {
                dest.mkdirs();
            }
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String newFileName = newNamePrefix + fileName;
                    File newFile = new File(dest + "/" + newFileName);
                    file.renameTo(newFile);
                    log.info("{}",file);
                }
            }
        });
    }
}
