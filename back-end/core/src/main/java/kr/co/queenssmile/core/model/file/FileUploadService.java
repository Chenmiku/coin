package kr.co.queenssmile.core.model.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    FileMeta uploadImage(MultipartFile file);

    FileMeta uploadFile(MultipartFile file);
    FileMeta uploadVideo(MultipartFile file);
    boolean delete(String url);
}
