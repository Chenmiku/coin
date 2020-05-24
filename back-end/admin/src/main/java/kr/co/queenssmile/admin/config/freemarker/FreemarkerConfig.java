package kr.co.queenssmile.admin.config.freemarker;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;

@Configuration
public class FreemarkerConfig {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void postConstruct() {

        // LocalDateTime formatter 추가 (example: https://github.com/amedia/freemarker-java-8)
        freeMarkerConfigurer.getConfiguration().setObjectWrapper(new Java8ObjectWrapper(freemarker.template.Configuration.VERSION_2_3_25));

        // Free marker Error Handler 설정
        // freeMarkerConfigurer.getConfiguration().setTemplateExceptionHandler(new FreeMarkerTemplateHendler());
        freeMarkerConfigurer.getConfiguration().setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
    }


}
