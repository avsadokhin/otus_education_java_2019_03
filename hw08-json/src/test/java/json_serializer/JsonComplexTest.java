package json_serializer;

import classes.TestClass;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.json.JsonObjectSerializer;

public class JsonComplexTest {
    private Gson gson;
    private JsonObjectSerializer serializer;

    @BeforeEach
    public void init() {
        gson = new Gson();
        serializer = new JsonObjectSerializer();
    }

    @Test
    public void testClassTest(){
        TestClass testClass = new TestClass();
        String jsonShouldBe = gson.toJson(testClass);
        String jsonResult =  serializer.toJson(testClass);

        Assertions.assertEquals(jsonShouldBe, jsonResult);
    }
}
