package json_serializer;

import classes.TestClass;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw15.json.JsonObjectSerializer;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonSimpleTest {

    private JsonObjectSerializer serializer;
    private Gson gson;

    @BeforeEach
    public void init() {
        serializer = new JsonObjectSerializer();
        gson = new Gson();
    }


    @Test
    public void booleanTest() {
        final boolean b = true;
        final String stringShouldBe = "true";
        final String stringResult = serializer.toJson(b);
        assertEquals(stringShouldBe, stringResult);
    }


    @Test
    public void arrayTest() {
        final String[] arr = {"a", "b"};
        final String stringShouldBe = "[\"a\",\"b\"]";
        final String stringResult = serializer.toJson(arr);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void collectionTest() {
        final Collection<String> collection = Arrays.asList("A", "B");
        final String stringShouldBe = "[\"A\",\"B\"]";
        final String stringResult = serializer.toJson(collection);
        System.out.println();
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void mapTest() {
        final Map<Integer, String> map = new HashMap<>();
        map.put(1, "S1");
        map.put(2, "S2");

        final String stringShouldBe = "{\"1\":\"S1\",\"2\":\"S2\"}";
        final String stringResult = serializer.toJson(map);
        assertEquals(stringShouldBe, stringResult);
    }

    @Test
    public void classExtendedTest() {
        final TestClass testClass = new TestClass();

        final String stringResult = serializer.toJson(testClass);
        assertTrue(stringResult.contains("\"str\":\"Super\""));
    }

    @Test
    public void nullTest() {

        assertEquals(gson.toJson(null), serializer.toJson(null));
    }

    @Test
    public void byteTest() {

        assertEquals(gson.toJson((byte) 1), serializer.toJson((byte) 1));
    }


    @Test
    public void intTest() {

        assertEquals(gson.toJson(1), serializer.toJson(1));
    }

    @Test
    public void longTest() {

        assertEquals(gson.toJson(1L), serializer.toJson(1L));
    }

    @Test
    public void floatTest() {

        assertEquals(gson.toJson(1f), serializer.toJson(1f));

    }

    @Test
    public void doubleTest() {

        assertEquals(gson.toJson(1d), serializer.toJson(1d));
    }

    @Test
    public void stringTest() {

        assertEquals(gson.toJson("aaa"), serializer.toJson("aaa"));
    }

    @Test
    public void charTest() {

        assertEquals(gson.toJson('a'), serializer.toJson('a'));
    }

    @Test
    public void intArrayTest() {

        assertEquals(gson.toJson(new int[]{1, 2, 3}), serializer.toJson(new int[]{1, 2, 3}));
    }

    @Test
    public void listTest() {

        assertEquals(gson.toJson(List.of(1, 2, 3)), serializer.toJson(List.of(1, 2, 3)));
    }

    @Test
    public void listSingletonTest() {

        assertEquals(gson.toJson(Collections.singletonList(1)), serializer.toJson(Collections.singletonList(1)));
    }

}
