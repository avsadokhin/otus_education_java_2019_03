package json_serializer;

import classes.TestClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.json.JsonObjectSerializer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonSimpleTest {

    private JsonObjectSerializer serializer;

    @BeforeEach
    public void init() {
        serializer = new JsonObjectSerializer();
    }

    @Test
    public void stringTest() {
        String s = "String";
        final String stringShouldBe = "{\"singleElm\":\"String\"}";
        final String stringResult = serializer.toJson(s);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void booleanTest() {
        final boolean b = true;
        final String stringShouldBe = "{\"singleElm\":true}";
        final String stringResult = serializer.toJson(b);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void longTest() {
        final long l = 8768787567L;
        final String stringShouldBe = "{\"singleElm\":8768787567}";
        final String stringResult = serializer.toJson(l);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void arrayTest() {
        final String[] arr = {"a", "b"};
        final String stringShouldBe = "{\"singleElm\":[\"a\",\"b\"]}";
        final String stringResult = serializer.toJson(arr);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void collectionTest() {
        final Collection<String> collection = Arrays.asList("A", "B");
        final String stringShouldBe = "{\"singleElm\":[\"A\",\"B\"]}";
        final String stringResult = serializer.toJson(collection);
        System.out.println();
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void mapTest() {
        final Map<Integer, String> map = new HashMap<>();
        map.put(1, "S1");
        map.put(2, "S2");

        final String stringShouldBe = "{\"singleElm\":{\"1\":\"S1\",\"2\":\"S2\"}}";
        final String stringResult = serializer.toJson(map);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void classExtendedTest() {
        final TestClass testClass = new TestClass();

        final String stringResult = serializer.toJson(testClass);
        assertTrue(stringResult.contains("\"str\":\"Super\""));
    }

}
