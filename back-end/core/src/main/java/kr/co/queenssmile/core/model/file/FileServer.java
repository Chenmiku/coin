package kr.co.queenssmile.core.model.file;

import lombok.Getter;

public enum FileServer {
    LOCAL("자체서버"),
    S3("AWS S3"),
    FTP("FTP 이미지 서버");

    @Getter
    private final String value;

    FileServer(final String value) {
        this.value = value;
    }
}
