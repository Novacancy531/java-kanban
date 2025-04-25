package http;

import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class TypoAdapters {

    private TypoAdapters() {
    }

    static class DurationTypeAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration> {
        @Override
        public JsonElement serialize(final Duration src, final Type typeOfSrc, final JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public Duration deserialize(final JsonElement json, final Type typeOfT,
                                    final JsonDeserializationContext context) throws JsonParseException {
            return Duration.parse(json.getAsString());
        }
    }

    static class LocalDateAdapter extends TypeAdapter<LocalDateTime> {
        /**
         * Форматирование даты в формат ISO.
         */
        private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
            if (localDateTime == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(localDateTime.format(dateTimeFormatter));
            }
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            String dateString = jsonReader.nextString();
            if (dateString == null || dateString.isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(dateString, dateTimeFormatter);
        }
    }
}
