package ru.otus.entity;

import ru.otus.annotation.Id;
import ru.otus.reflection.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class Entity {
    private Class clazz;
    private String name;
    private List<Field> fieldList;

    public Entity(Class<?> clazz) {
        this.clazz = clazz;
        this.name = clazz.getName().toUpperCase();
        this.fieldList = ReflectionHelper.getObjectFieldsList(clazz);
    }

    public void checkEntity() {

        if (!Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(Id.class))) {
            throw new EntityException("Type doesn't correspond database entity. Annotation 'Id' mismatch");
        }

    }


}
