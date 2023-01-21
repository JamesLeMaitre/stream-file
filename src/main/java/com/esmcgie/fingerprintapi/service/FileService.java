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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public Mono<Void> renameAndMoveFilesInFolder(String newNamePrefix) {
        String folderPath = JavaUtiles.USER_PATH+"/user/images";
        //String newNamePrefix = "1__M_Left_middle_finger";
        //String destinationFolder = JavaUtiles.USER_PATH+"/user/rename/";
//        Path sourcePath = Paths.get(filePath);
//        Path targetPath = Paths.get(destinationFolder + "/" + sourcePath.getFileName());
//        try {
//            Files.copy(sourcePath, targetPath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return Mono.fromRunnable(() -> {

      return Mono.fromRunnable(() -> {
            File dest = new File(JavaUtiles.USER_PATH+"/user/rename");
            if (!dest.exists()) {
                dest.mkdirs();
            }
            Path sourcePath = Paths.get(folderPath);
            String fileName = sourcePath.getFileName().toString();
//          System.out.println(fileName);
          log.info("This is a file Name {}",fileName);
            String extension = fileName.substring(fileName.lastIndexOf("."));
          log.info("This is an extension {}",extension);
            Path targetPath = Paths.get(dest + "/" + newNamePrefix + extension);

        }).doOnNext(r->{

      }).then();
//    }
//            return Mono.fromRunnable(() -> {
//                File folder = new File(folderPath);
//                File dest = new File(JavaUtiles.USER_PATH+"/user/rename");
//                if (!dest.exists()) {
//                    dest.mkdirs();
//                }
//                Path sourcePath = Paths.get(folderPath);
//                String fileName = sourcePath.getFileName().toString();
//                String extension = fileName.substring(fileName.lastIndexOf("."));
//                Path targetPath = Paths.get(dest + "/" + newNamePrefix + extension);
//                try {
//                    Files.copy(sourcePath, targetPath);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        };
//            for (File file : Objects.requireNonNull(folder.listFiles())) {
//                if (file.isFile()) {
//                    String fileName = file.getName();
//                    File newFile = new File(dest + "/" + newNamePrefix);
//                    file.renameTo(newFile);
//                    try {
//                        Files.copy(newFile.toPath(), dest.toPath());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    log.info("{}",file);
//                }
//            }
//        });
    }

    public Mono<Void> copyAndRenameFileIfExist(String filePath, String newName, String destinationFolder) {
        return Mono.fromRunnable(() -> {
            Path sourcePath = Paths.get(filePath);
            String fileName = sourcePath.getFileName().toString();
            String extension = fileName.substring(fileName.lastIndexOf("."));
            Path targetPath = Paths.get(destinationFolder + "/" + newName + extension);
            if (Files.exists(targetPath)) {
                // target file already exists, you can choose to overwrite it or give it a different name
                // for example, you can add a timestamp to the new name
                String newFileName = newName + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + extension;
                targetPath = Paths.get(destinationFolder + "/" + newFileName);
            }
            try {
                Files.copy(sourcePath, targetPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
