package ru.otus.dbservice;

import java.sql.SQLException;

public interface DdlSevice<T> {
    void createMeta(T t) throws SQLException;
    void deleteMeta(T t) throws SQLException;

}
