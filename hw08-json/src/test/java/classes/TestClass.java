package classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TestClass extends TestMain {
    private int i = 123;
    private long l = 1232L;
    private boolean b = false;
    private char c = 'A';
    private Integer integer = 321;
    private String string = "Test String";
    private double[] array = {1.2324, 3.3324, 9};
    private Set<String> set = new HashSet<>();
    private Map<Integer, String> map = new HashMap<>();

    public TestClass() {
        super("Super");
        set.add("Set1");
        set.add("Set2");

        map.put(1, "Map1");
        map.put(2, "Map2");
    }
}
