package kr.co.queenssmile.core.service.aws;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;

public interface AWSS3Service {

    /**
     * 이미지 업로드
     *
     * @param file        멀티 파트
     * @param keyname     S3 경로
     * @return Keyname (full path)
     */
    URL uploadS3(MultipartFile file, String keyname);

    // 파트로 나누어 객체 업로드 ‑ 멀티파트 업로드 API를 사용하여 최대 5TB의 대형 객체를 업로드할 수 있습니다.
    URL uploadS3Big(MultipartFile file, String keyname);

    void deleteS3(String keyName);

    URL uploadS3(InputStream inputStream, ObjectMetadata metadata, String keyName);


}
