package kr.co.queenssmile.admin.web.api;

import kr.co.queenssmile.core.model.file.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/api/upload")
public class AdminAPIFileUploadController {

  @Autowired
  private FileUploadService fileUploadService;

  @PostMapping("/file")
  public ResponseEntity<?> file(@RequestParam MultipartFile file) {
    return ResponseEntity.ok(fileUploadService.uploadFile(file));
  }

  @PostMapping("/video")
  public ResponseEntity<?> video(@RequestParam MultipartFile file) {
    return ResponseEntity.ok(fileUploadService.uploadVideo(file));
  }

  @PostMapping("/image")
  public ResponseEntity<?> image(@RequestParam MultipartFile file) {
    return ResponseEntity.ok(fileUploadService.uploadImage(file));
  }

  @DeleteMapping("/file")
  public ResponseEntity<?> delete(@RequestBody java.util.Map<String, String> requestBody) {
    String path = requestBody.get("path");
    fileUploadService.delete(path);
    return ResponseEntity.ok().build();
  }
}
