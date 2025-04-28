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
import com.google.gson.stream.JsonToken;
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
            if (src == null) {
                return null;
            }

            return new JsonPrimitive(src.toString());
        }

        @Override
        public Duration deserialize(final JsonElement json, final Type typeOfT,
                                    final JsonDeserializationContext context) throws JsonParseException {
            if (json == null || json.isJsonNull() || json.getAsString() == null || json.getAsString().isEmpty()) {
                return null;
            }

            return Duration.parse(json.getAsString());
        }
    }

    public static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        /**
         * Форматирование даты в формат ISO.
         */
        private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        /**
         *  Запись даты в формат JSON.
         * @param out .
         * @param value the Java object to write. May be null.
         * @throws IOException .
         */
        @Override
        public void write(final JsonWriter out, final LocalDateTime value) throws IOException {
            out.value(value != null ? formatter.format(value) : null);
        }

        /**
         * Чтение даты из формата JSON.
         * @param in .
         * @return .
         * @throws IOException .
         */
        @Override
        public LocalDateTime read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            return LocalDateTime.parse(in.nextString(), formatter);
        }
    }
}
