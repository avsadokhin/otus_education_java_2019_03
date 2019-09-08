package ru.otus.hw15.jtunit.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JTUnitRequest {

    private List<JTClassSelector> classList = new ArrayList();

    private JTUnitRequest() {
    }

    public List<JTClassSelector> getClassList() {
        return classList;
    }

    public static JTClassSelector selectClass(Class<?> clazz) {
        return new JTClassSelector(clazz);
    }

    public static JTRequestBuilder makeRequest() {
        return new JTUnitRequest().new JTRequestBuilder();
    }

    public class JTRequestBuilder {
        private JTRequestBuilder() {

        }


        public JTRequestBuilder setClassList(JTClassSelector... classSelectors) {
            classList = Arrays.asList(classSelectors);
            return this;
        }

        public JTUnitRequest build() {
            return JTUnitRequest.this;
        }
    }


}
