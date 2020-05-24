package kr.co.queenssmile.core.model.aws;

import com.amazonaws.services.simpleemail.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SESSender implements java.io.Serializable {

    private static final long serialVersionUID = -8492599360889432218L;

    private String from;
    private java.util.List<String> to;
    private String subject;
    private String html;

    @Builder
    public SESSender(java.util.List<String> to, String subject, String html) {
        this.to = to;
        this.subject = subject;
        this.html = html;
    }

    // 여러명 일때
    public void addTo(String email) {
        this.to.add(email);
    }

    public SendEmailRequest toSendRequestDto() {

        Destination destination = new Destination().withToAddresses(this.to);

        Message message = new Message()
                .withSubject(createContent(this.subject))
                .withBody(new Body().withHtml(createContent(this.html))); // content body는 HTML 형식으로 보내기 때문에 withHtml을 사용합니다.

        return new SendEmailRequest()
                .withSource(this.from)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(String text) {
        return new Content().withCharset("UTF-8").withData(text);
    }
}