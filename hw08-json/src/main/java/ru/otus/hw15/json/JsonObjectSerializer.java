package ru.otus.hw15.json;

import ru.otus.hw15.reflection.ReflectionHelper;

import javax.json.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class JsonObjectSerializer {
    private List<String> commonTypes = Arrays.asList("INTEGER",
            "BYTE",
            "BOOLEAN",
            "SHORT",
            "CHAR",
            "CHARACTER",
            "STRING",
            "INT",
            "FLOAT",
            "LONG",
            "DOUBLE",
            "NUMBER"
    );

    public String toJson(Object src) {
        return src == null ? JsonObject.NULL.toString() : this.toJsonValue(src).toString();
    }

    private JsonObjectBuilder MapToJsonBuilder(Object src) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        ((Map) src).forEach((key, val) -> jsonObjectBuilder.add(key.toString(), toJsonValue(val)));

        return jsonObjectBuilder;
    }


    private JsonArrayBuilder ArrayToJsonBuilder(Object src) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < Array.getLength(src); i++) {
            Object arrayElement = Array.get(src, i);
            arrayBuilder.add(toJsonValue(arrayElement));
        }
        return arrayBuilder;
    }

    private JsonArrayBuilder CollectionToJsonBuilder(Object src) {
        Object[] objArray = ((Collection) src).toArray();
        return ArrayToJsonBuilder(objArray);
    }

    private JsonObjectBuilder complexObjectToJson(Object src, Class<?> clazz) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        Class<?> objClass = clazz;
        while (objClass != Object.class) {

            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                if (Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                final Object fieldValue = ReflectionHelper.getFieldValue(src, field);
                if (fieldValue != null)
                    jsonObjectBuilder.add(field.getName(), toJsonValue(fieldValue));
            }
            objClass = objClass.getSuperclass();

        }
        return jsonObjectBuilder;
    }

    private JsonValue toJsonValue(Object src) {

        if (src instanceof Number)
            return NumberToJsonValue((Number) src);
        else if (src instanceof Boolean)
            return src.equals(false) ? JsonValue.FALSE : JsonValue.TRUE;
        else if (src.getClass().isArray())
            return ArrayToJsonBuilder(src).build();
        else if (src instanceof Collection)
            return CollectionToJsonBuilder(src).build();
        else if (src instanceof Map)
            return MapToJsonBuilder(src).build();
        else if (src instanceof Character || src instanceof String)
            return Json.createValue(String.valueOf(src));
        else
            return complexObjectToJson(src, src.getClass()).build();

    }


    private JsonValue NumberToJsonValue(Number number) {
        if (number instanceof Float || number instanceof Double)
            return Json.createValue(number.doubleValue());
        else if (number instanceof Long)
            return Json.createValue(number.longValue());
        else if (number instanceof Integer)
            return Json.createValue(number.intValue());
        else if (number instanceof Short)
            return Json.createValue(number.shortValue());
        else if (number instanceof Byte)
            return Json.createValue(number.byteValue());
        return JsonValue.NULL;
    }

}
