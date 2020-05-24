package kr.co.queenssmile.core.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class JsonLocalTimeSerializer extends JsonSerializer<LocalTime> {

  @Override
  public void serialize(LocalTime localTime,
                        JsonGenerator generator,
                        SerializerProvider provider) throws IOException {

    if (localTime != null) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a").withLocale(java.util.Locale.US);
      generator.writeString(localTime.format(dtf));
    }
  }
}