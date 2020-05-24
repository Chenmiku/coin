package kr.co.queenssmile.core.service.aws;

import com.amazonaws.auth.AWSCredentialsProvider;

public interface AWSCredentialsService {

    AWSCredentialsProvider credentials();
}
