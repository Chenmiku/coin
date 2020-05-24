package kr.co.queenssmile.core.service.aws;


import kr.co.queenssmile.core.model.aws.SESSender;

public interface AWSSESService {

    void send(SESSender sesSender);
}
