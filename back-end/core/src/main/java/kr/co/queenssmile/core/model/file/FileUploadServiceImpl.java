package kr.co.queenssmile.core.model.file;

import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.PayloadTooLargeException;
import kr.co.queenssmile.core.config.exception.UnsupportedMediaTypeException;
import kr.co.queenssmile.core.config.property.ProcessEnv;
import kr.co.queenssmile.core.service.aws.AWSS3Service;
import kr.co.queenssmile.core.utils.FileUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

  public static final String UPLOADED_IMAGE_PATH = "images";
  public static final String UPLOADED_FILE_PATH = "files";
  public static final String UPLOADED_VIDEO_PATH = "videos";

  public final static String FILE_PREFIX = "aart";

  public final static FileServer server = FileServer.S3;

  @Autowired
  private ProcessEnv processEnv;

  @Value("${app.image.path}")
  private String imagePath;

  @Value("${aws.s3.url}")
  private String s3Url;

  @Autowired
  private AWSS3Service awss3Service;

  @Override
  public FileMeta uploadImage(MultipartFile file) {

    if (file == null || file.isEmpty()) {
      throw new BadRequestException("파일이 존재하지 않습니다.");
    }

    String fileExt = FileUtils.extension(file.getContentType());

    if (!FileUtils.isImageExt(fileExt)) {
      throw new UnsupportedMediaTypeException("형식이나 크기에 맞지 않은 파일입니다.<br/>(사진은 35MB 이하 jpg만 업로드 가능)");
    }

    if (file.getSize() >= FileUtils.convertByte(35, "MB")) {
      throw new PayloadTooLargeException("형식이나 크기에 맞지 않은 파일입니다.<br/>(사진은 35MB 이하 jpg만 업로드 가능)");
    }
    String originalFilename = file.getOriginalFilename();
    String filename = this.getNewFilename(FileUtils.extension(file.getContentType()));

    URL url = null;
    try {
      url = this.upload(UPLOADED_IMAGE_PATH, filename, file);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    FileMeta fileMeta = new FileMeta();
    fileMeta.setUrl(url);
    fileMeta.setFilename(filename);
    fileMeta.setOriginalFilename(originalFilename);
    fileMeta.setMimeType(file.getContentType());
    fileMeta.setSize(file.getSize());

    log.debug("fileMeta ::: {}", fileMeta);
    return fileMeta;
  }

  @Override
  public FileMeta uploadFile(MultipartFile file) {

    String fileExt = FileUtils.extension(file.getContentType());

    if (!FileUtils.isFileExt(fileExt)) {
      throw new UnsupportedMediaTypeException("지원하지 않는 미디어 타입입니다. (가능 확장자 \"gif\", \"jpeg\", \"jpg\", \"png\", \"svg\", \"blob\", \"pdf\", \"zip\", \"xlsx\")");
    }

    if (file.getSize() >= FileUtils.convertByte(3, "GB")) {
      throw new PayloadTooLargeException("3GB 이상 업로드할 수 없습니다.");
    }
    String originalFilename = file.getOriginalFilename();
    String filename = this.getNewFilename(FileUtils.extension(file.getContentType()));
    URL url = null;
    try {
      url = this.upload(UPLOADED_FILE_PATH, filename, file);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }

    FileMeta fileMeta = new FileMeta();
    fileMeta.setUrl(url);
    fileMeta.setFilename(filename);
    fileMeta.setOriginalFilename(originalFilename);
    fileMeta.setMimeType(file.getContentType());
    fileMeta.setSize(file.getSize());
    return fileMeta;
  }

  @Override
  public FileMeta uploadVideo(MultipartFile file) {

    String fileExt = FileUtils.extension(file.getContentType());

    if (!FileUtils.isMP4Ext(fileExt)) {
      throw new UnsupportedMediaTypeException("지원하지 않는 미디어 타입입니다. (only \"mp4\")");
    }

    if (file.getSize() >= FileUtils.convertByte(50, "MB")) {
      throw new PayloadTooLargeException("50MB 이상 업로드할 수 없습니다.");
    }
    String originalFilename = file.getOriginalFilename();
    String filename = this.getNewFilename(FileUtils.extension(file.getContentType()));
    URL url = null;
    try {
      url = this.upload(UPLOADED_VIDEO_PATH, filename, file);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new BadRequestException("잘못된 URL 생성");
    }

    FileMeta fileMeta = new FileMeta();
    fileMeta.setUrl(url);
    fileMeta.setFilename(filename);
    fileMeta.setOriginalFilename(originalFilename);
    fileMeta.setMimeType(file.getContentType());
    fileMeta.setSize(file.getSize());
    return fileMeta;
  }

  @Override
  public boolean delete(String url) {
    log.debug("DELETE ::: {}", url);
    if (Objects.equals(server, FileServer.S3)) {
      this.deleteS3(url);
      return true;
    } else if (Objects.equals(server, FileServer.FTP)) {
      return false;
    } else {
      return this.deleteDefault(url);
    }
  }

  private void deleteS3(String url) {
    if (StringUtils.isNotEmpty(url)) {

      String keyName = url.replaceAll(s3Url, "");

      awss3Service.deleteS3(keyName);
    }
  }

  private boolean deleteDefault(String url) {

    if (StringUtils.isNotEmpty(url)) {

      String rootPath = "";
      String filename = "";

      if (url.startsWith("/" + UPLOADED_IMAGE_PATH)) {
        rootPath = processEnv.isProduction() ? imagePath : FileUtils.getResourcePath("static", UPLOADED_IMAGE_PATH);
        filename = url.replace("/" + UPLOADED_IMAGE_PATH + "/", "");
      } else if (url.startsWith("/" + UPLOADED_FILE_PATH)) {
        rootPath = processEnv.isProduction() ? imagePath : FileUtils.getResourcePath("static", UPLOADED_FILE_PATH);
        filename = url.replace("/" + UPLOADED_FILE_PATH + "/", "");
      } else if (url.startsWith("/" + UPLOADED_VIDEO_PATH)) {
        rootPath = processEnv.isProduction() ? imagePath : FileUtils.getResourcePath("static", UPLOADED_VIDEO_PATH);
        filename = url.replace("/" + UPLOADED_VIDEO_PATH + "/", "");
      }

      if (StringUtils.isNotEmpty(rootPath)
          && StringUtils.isNotEmpty(filename)) {

        File file = new File(rootPath + filename);

        return file.delete();
      }
    }

    return false;
  }

  private URL upload(String path, String filename, MultipartFile file) throws MalformedURLException {

    if (Objects.equals(server, FileServer.S3)) {
      return this.uploadToS3(path, filename, file);
    } else if (Objects.equals(server, FileServer.FTP)) {
      return this.uploadToFTP(path, filename, file);
    } else {
      return this.uploadLocal(path, filename, file);
    }
  }

  //== Default
  private URL uploadLocal(String path, String filename, MultipartFile file) throws MalformedURLException {

    if (processEnv.isProduction()) {
      FileUtils.hasDirectoryAndMkDir(imagePath);
    }

    String rootPath = processEnv.isProduction() ? imagePath : FileUtils.getResourcePath("static", path);

    try {
      File dir = new File(rootPath);

      if (!dir.exists()) {
        dir.mkdirs();
      }

      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(dir, filename)));
      stream.write(file.getBytes());
      stream.close();

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e.getLocalizedMessage());
    }

    // File.separator 사용하면 안된다. url 전용 "/" 사용
    String uploadedURLPath = "/" + path + "/";
    log.debug("upload uploadedPath ::: {}", uploadedURLPath);
    return new URL(uploadedURLPath + filename);
  }

  private URL uploadToS3(String path, String filename, MultipartFile file) {
    String keyName = path + "/" + filename;
//    return awss3Service.uploadS3(file, keyName);
    return awss3Service.uploadS3Big(file, keyName);
  }

  private URL uploadToFTP(String path, String filename, MultipartFile file) {
    return null;
  }

  private String getRandomStr() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  private String getNewFilename(String fileExtension) {
    return FILE_PREFIX + "_" + this.getRandomStr() + fileExtension;
  }
}
