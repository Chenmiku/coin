package kr.co.queenssmile.core.service.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Service
public class AWSS3ServiceImpl implements AWSS3Service {

  @Value("${aws.s3.bucketName}")
  private String bucketName;

  @Value("${aws.cloudFront.host}")
  private String host;


  @Autowired
  private AWSCredentialsService awsCredentialsService;

  private AmazonS3 client() {
    return AmazonS3ClientBuilder.standard().withCredentials(awsCredentialsService.credentials()).withRegion(Regions.AP_NORTHEAST_2).build();
  }

  @Override
  public URL uploadS3(MultipartFile file, String keyname) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    log.debug(String.format("bucketName : %s", bucketName));
    log.debug(String.format("keyname : %s", keyname));
    log.debug(String.format("content type : %s", metadata.getContentType()));
    log.debug(String.format("content length : %s", metadata.getContentLength()));

    try (InputStream inputStream = file.getInputStream()) {
//            log.debug(String.format("Uploading a new object to S3 from a file\n"));
//            log.debug(String.format("inputStream : %s", inputStream.available()));

      this.client().putObject(bucketName, "queenssmile-object-key", "Uploaded String Object");

      PutObjectResult pr = this.client().putObject(
          new PutObjectRequest(bucketName, keyname, inputStream, metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead)
      );

//            log.debug(String.format("content type : %s", pr.getMetadata().getContentType()));
//            log.debug(String.format("content length : %s", pr.getMetadata().getContentLength()));
//            log.debug("### PR : {}", pr);
      return new URL(String.format("%s/%s", host, keyname));

    } catch (AmazonServiceException ase) {

      log.error("Caught an AmazonServiceException, which " +
          "means your request made it " +
          "to Amazon S3, but was rejected with an error response" +
          " for some reason.");
      log.error("Error Message:    " + ase.getMessage());
      log.error("HTTP SNSStatus Code: " + ase.getStatusCode());
      log.error("AWS Error Code:   " + ase.getErrorCode());
      log.error("Error Type:       " + ase.getErrorType());
      log.error("Request ID:       " + ase.getRequestId());

      throw ase;
    } catch (AmazonClientException ace) {
      log.error("Caught an AmazonClientException, which " +
          "means the client encountered " +
          "an internal error while trying to " +
          "communicate with S3, " +
          "such as not being able to access the network.");
      log.error("Error Message: " + ace.getMessage());
      throw ace;
    } catch (IOException ie) {
      log.error("Error Message: " + ie.getMessage());
    }
    return null;
  }


  @Override
  public URL uploadS3Big(MultipartFile file, String keyname) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    log.debug(String.format("bucketName : %s", bucketName));
    log.debug(String.format("keyname : %s", keyname));
    log.debug(String.format("content type : %s", metadata.getContentType()));
    log.debug(String.format("content length : %s", metadata.getContentLength()));

    try (InputStream inputStream = file.getInputStream()) {
//            log.debug(String.format("Uploading a new object to S3 from a file\n"));
//            log.debug(String.format("inputStream : %s", inputStream.available()));

//            TransferManager tm = TransferManagerBuilder.standard()
//                .withS3Client(this.client())
//                .build();
//
//            // TransferManager processes all transfers asynchronously,
//            // so this call returns immediately.
//            Upload upload = tm.upload(bucketName, keyname, inputStream, metadata);
//            log.debug("Object upload started");
//
//            // Optionally, wait for the upload to finish before continuing.
//            upload.waitForCompletion();
//            log.debug("Object upload complete");
      TransferManager tm = TransferManagerBuilder.standard()
          .withS3Client(this.client())
          .build();
      PutObjectRequest request =
          new PutObjectRequest(bucketName, keyname, inputStream, metadata);
      request.withCannedAcl(CannedAccessControlList.PublicRead);
      // To receive notifications when bytes are transferred, add a
      // ProgressListener to your request.
      request.setGeneralProgressListener(progressEvent -> {
//        log.debug("Transferred bytes: " + progressEvent.getBytesTransferred();
      });
      // TransferManager processes all transfers asynchronously,
      // so this call returns immediately.
      Upload upload = tm.upload(request);

      // Optionally, you can wait for the upload to finish before continuing.
      upload.waitForCompletion();
      String urlStr = String.format("%s/%s", host, keyname);
      log.debug("> url ::: {}", urlStr);
      URL url = new URL(urlStr);
      log.debug("> host ::: {}", url.getHost());
      log.debug("> path ::: {}", url.getPath());
      return url;

    } catch (AmazonServiceException ase) {

      log.error("Caught an AmazonServiceException, which " +
          "means your request made it " +
          "to Amazon S3, but was rejected with an error response" +
          " for some reason.");
      log.error("Error Message:    " + ase.getMessage());
      log.error("HTTP SNSStatus Code: " + ase.getStatusCode());
      log.error("AWS Error Code:   " + ase.getErrorCode());
      log.error("Error Type:       " + ase.getErrorType());
      log.error("Request ID:       " + ase.getRequestId());

      throw ase;
    } catch (AmazonClientException ace) {
      log.error("Caught an AmazonClientException, which " +
          "means the client encountered " +
          "an internal error while trying to " +
          "communicate with S3, " +
          "such as not being able to access the network.");
      log.error("Error Message: " + ace.getMessage());
      throw ace;
    } catch (IOException ie) {
      log.error("Error Message: " + ie.getMessage());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }


  @Override
  public void deleteS3(String keyName) {

    try {

      log.debug("bucketName ::: {}", bucketName);
      log.debug("keyName ::: {}", keyName);
      this.client().deleteObject(new DeleteObjectRequest(bucketName, keyName));

    } catch (AmazonServiceException e) {
      // The call was transmitted successfully, but Amazon S3 couldn't process
      // it, so it returned an error response.
      e.printStackTrace();
    } catch (SdkClientException e) {
      // Amazon S3 couldn't be contacted for a response, or the client
      // couldn't parse the response from Amazon S3.
      e.printStackTrace();
    }

  }

  @Override
  public URL uploadS3(InputStream inputStream, ObjectMetadata metadata, String keyName) {

    log.debug(String.format("bucketName : %s", bucketName));
    log.debug(String.format("keyname : %s", keyName));
    log.debug(String.format("content type : %s", metadata.getContentType()));
    log.debug(String.format("content length : %s", metadata.getContentLength()));

    try (InputStream is = inputStream) {
//            log.debug(String.format("Uploading a new object to S3 from a file\n"));
//            log.debug(String.format("inputStream : %s", inputStream.available()));
      PutObjectResult pr = this.client().putObject(
          new PutObjectRequest(bucketName, keyName, is, metadata)
              .withCannedAcl(CannedAccessControlList.PublicRead)
      );

//            log.debug(String.format("content type : %s", pr.getMetadata().getContentType()));
//            log.debug(String.format("content length : %s", pr.getMetadata().getContentLength()));
//            log.debug("### PR : {}", pr);
      return new URL(String.format("%s/%s", host, keyName));

    } catch (AmazonServiceException ase) {

      log.error("Caught an AmazonServiceException, which " +
          "means your request made it " +
          "to Amazon S3, but was rejected with an error response" +
          " for some reason.");
      log.error("Error Message:    " + ase.getMessage());
      log.error("HTTP SNSStatus Code: " + ase.getStatusCode());
      log.error("AWS Error Code:   " + ase.getErrorCode());
      log.error("Error Type:       " + ase.getErrorType());
      log.error("Request ID:       " + ase.getRequestId());

      throw ase;
    } catch (AmazonClientException ace) {
      log.error("Caught an AmazonClientException, which " +
          "means the client encountered " +
          "an internal error while trying to " +
          "communicate with S3, " +
          "such as not being able to access the network.");
      log.error("Error Message: " + ace.getMessage());
      throw ace;
    } catch (IOException ie) {
      log.error("Error Message: " + ie.getMessage());
      throw new RuntimeException(ie);
    }
  }

}
