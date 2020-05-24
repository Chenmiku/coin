package kr.co.queenssmile.core.service.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class AWSCredentialsServiceImpl implements AWSCredentialsService {

    @Value("${aws.key.accesskey}")
    private String accessKey;

    @Value("${aws.key.secretkey}")
    private String secretKey;

    @Override
    public AWSCredentialsProvider credentials() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }
}
