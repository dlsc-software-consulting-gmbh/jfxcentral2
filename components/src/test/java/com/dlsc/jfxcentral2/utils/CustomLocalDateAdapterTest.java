package com.dlsc.jfxcentral2.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomLocalDateAdapterTest {

    private final CustomLocalDateAdapter customLocalDateAdapter = new CustomLocalDateAdapter();
    private final JsonDeserializationContext jsonDeserializationContext = null; // not utilized in tested method
    private final Type typeOfT = null; // not utilized in tested method

    // Test the successful operation of the deserialize method with a correctly formatted date string
    @Test
    void deserialize_normalCase() {
        JsonElement json = new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }

            @Override
            public String getAsString() {
                return  "2023-08-20T00:00:00";
            }
        };

        LocalDate localDate = customLocalDateAdapter.deserialize(json, typeOfT, jsonDeserializationContext);
        assertEquals(LocalDate.of(2023, 8, 20), localDate);
    }

    // Test the failed operation (JsonParseException) of the deserialize method with a incorrectly formatted date string
    @Test
    void deserialize_exceptionCase() {
        JsonElement json = new JsonElement() {
            @Override
            public JsonElement deepCopy() {
                return null;
            }

            @Override
            public String getAsString() {
                return  "invalid-date-string";
            }
        };

        assertThrows(JsonParseException.class, () ->
                customLocalDateAdapter.deserialize(json, typeOfT, jsonDeserializationContext)
        );
    }
}