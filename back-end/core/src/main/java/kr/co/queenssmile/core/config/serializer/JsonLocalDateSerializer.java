package kr.co.queenssmile.core.config.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

@Slf4j
@Component
public class JsonLocalDateSerializer extends JsonSerializer<LocalDate> {

  @Override
  public void serialize(LocalDate localDate,
                        JsonGenerator generator,
                        SerializerProvider provider) throws IOException {

    generator.writeNumber(localDate.atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000);
  }
}