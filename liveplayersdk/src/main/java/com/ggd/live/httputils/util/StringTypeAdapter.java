package com.ggd.live.httputils.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Mr.xu on 2018/11/1.
 */

public class StringTypeAdapter extends TypeAdapter<String> {
    @Override
    public void write(JsonWriter jsonWriter, String s) throws IOException {
        try {
            if (s == null) {
                s = "";
            }
            jsonWriter.value(String.valueOf(s));
        } catch (Exception e) {

        }
    }

    @Override
    public String read(JsonReader jsonReader) throws IOException {
        try {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return "";
            } else {
                return jsonReader.nextString();
            }
        } catch (Exception e) {

        }
        return "";
    }
}
