package ru.otus.entity;

import ru.otus.annotation.Column;
import ru.otus.annotation.Id;
import ru.otus.annotation.Size;
import ru.otus.reflection.ReflectionHelper;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Entity<T> {
    private Class clazz;
    private String tableName;
    private List<Field> fieldList;

    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS public.%s (%s);";
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

    public List<Field> getFieldAnnotadedList() {
        List<Field> list = ReflectionHelper.getObjectFieldsList(clazz);
        return list.stream().filter(field -> field.isAnnotationPresent(Column.class)).collect(Collectors.toList());
    }

    public List<String> getFieldNameList() {
        return fieldList.stream().map(Field::getName).collect(Collectors.toList());
    }

    public List<String> getFieldNameList(List<Field> fieldsToIgnore) {
        return fieldList.stream().filter(field -> !fieldsToIgnore.contains(field)).map(Field::getName).collect(Collectors.toList());
    }

    public List<Object> getFieldListValue(T t) {
        return fieldList.stream().map(field -> ReflectionHelper.getFieldValue(t, field.getName())).collect(Collectors.toList());
    }

    public List<Object> getInsertFieldListValue(T t) {
        return fieldList.stream().filter(field -> !(isColumnId(field) && isColumnAutoIncrement(field))).map(field -> ReflectionHelper.getFieldValue(t, field.getName())).collect(Collectors.toList());

    }


    public String getDelimitedFieldNames(List<Field> fieldsToIgnore) {
        return String.join(",", getFieldNameList(fieldsToIgnore));

    }


    private String getTableDelimitedColumnString() {

        return String.join(", ", fieldList.stream().map(f -> getColumnDefinitionString(f)).collect(Collectors.toSet()));

    }

    private String getColumnDefinitionString(Field f) {

        if (isColumnId(f)) {
            return f.getName() + " " + getColumnType(f).getValue() + getColumnSizeString(f) + " NOT NULL " + getColumnAutoIncrementString(f);
        } else
            return f.getName() + " " + getColumnType(f).getValue() + getColumnSizeString(f);

    }

    private String getColumnSizeString(Field field) {
        if (field.isAnnotationPresent(Size.class)) {
            int size = field.getAnnotation(Size.class).min();
            return size != 0 ? "(" + size + ")" : "";
        } else return "";

    }

    private DbType getColumnType(Field field) {
        return field.getAnnotation(Column.class).type();
    }

    private String getColumnAutoIncrementString(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            boolean isAutoIncrement = field.getAnnotation(Id.class).isAutoIncrement();
            return isAutoIncrement ? "AUTO_INCREMENT" : "";
        } else return "";
    }

    private boolean isColumnId(Field field) {
        return field.getAnnotation(Id.class) != null;
    }

    private boolean isColumnAutoIncrement(Field field) {
        if (field.isAnnotationPresent(Id.class)) {
            boolean isAutoIncrement = field.getAnnotation(Id.class).isAutoIncrement();
            return isAutoIncrement;
        } else return false;
    }


    private String getDelimitedFieldValues(List<Field> ignoreFields) {
        return String.join(",", Stream.iterate("?", n -> n).limit(getFieldNameList(ignoreFields).size()).collect(Collectors.toList()));
    }

    public String getMetaCreateStatment() {
        return String.format(CREATE_TABLE, tableName, getTableDelimitedColumnString());

    }

    public String getMetaDeleteStatment() {
        return String.format(DROP_TABLE, tableName);

    }

    public String insertPreparedStatement() {
        List<Field> ignoreFields;
        ignoreFields = fieldList.stream().filter(field -> isColumnId(field) && isColumnAutoIncrement(field)).collect(Collectors.toList());
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                getTableName(),
                getDelimitedFieldNames(ignoreFields),
                getDelimitedFieldValues(ignoreFields));
    }


}
