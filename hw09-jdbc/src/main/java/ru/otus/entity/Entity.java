package ru.otus.entity;

import ru.otus.annotation.Column;
import ru.otus.annotation.Id;
import ru.otus.annotation.Size;
import ru.otus.reflection.ReflectionHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Entity<T> {
    private Class clazz;
    private String tableName;
    private List<Field> fieldList;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (%s);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS %s;";


    public String getTableName() {
        return tableName;
    }


    public List<Field> getFieldList() {
        return fieldList;
    }


    public Entity(Class<? extends T> clazz) {
        this.clazz = clazz;
        this.tableName = clazz.getSimpleName().toUpperCase();
        this.fieldList = getFieldAnnotadedList();
    }

    private void checkEntity() {

        if (!Arrays.stream(clazz.getDeclaredFields()).anyMatch(field -> field.isAnnotationPresent(Column.class))) {
            throw new EntityException("Type doesn't correspond database entity. Annotation 'Id' mismatch");
        }

    }

    public String getDelimetedFieldNames() {
        return String.join(",", getFieldNameList());

    }

    public List<Field> getFieldAnnotadedList() {
        List<Field> list = ReflectionHelper.getObjectFieldsList(clazz);
        return list.stream().filter(field -> field.isAnnotationPresent(Column.class)).collect(Collectors.toList());
    }

    public List<String> getFieldNameList() {
        return //String.join(",", getFieldList().stream().map(Field::getName).collect(Collectors.toList()));
                getFieldList().stream().map(Field::getName).collect(Collectors.toList());
    }


    private String getTableColumnString() {

        return String.join(", ", fieldList.stream().map(f -> getColumnString(f)).collect(Collectors.toSet()));

    }

    private String getColumnString(Field f) {

        String res;
        if (isColumnId(f)) {
            return f.getName() + " " + getColumnType(f).getValue() + getColumnSizeString(f) + " NOT NULL " + getColumnAutoIncrementString(f);
        } else
            return f.getName() + " " + getColumnType(f).getValue() + getColumnSizeString(f);

    }

    private String getColumnSizeString(Field field) {
        Annotation a = field.getAnnotation(Size.class);
        if (field.isAnnotationPresent(Size.class))
            return field.getAnnotation(Size.class).min() != 0 ? "(" + field.getAnnotation(Size.class).min() + ")" : "";
        else return "";

    }

    private DbType getColumnType(Field field) {
        return field.getAnnotation(Column.class).type();
    }

    private String getColumnAutoIncrementString(Field field) {
        return field.getAnnotation(Id.class).isAutoIncrement() ? "AUTO_INCREMENT" : "";
    }

    private boolean isColumnId(Field field) {
        return field.getAnnotation(Id.class) != null;
    }


    public String getMetaCreateStatment() {
        return String.format(CREATE_TABLE, tableName, getTableColumnString());

    }

    public String getMetaDeleteStatment() {
        return String.format(DROP_TABLE, tableName);

    }
}
