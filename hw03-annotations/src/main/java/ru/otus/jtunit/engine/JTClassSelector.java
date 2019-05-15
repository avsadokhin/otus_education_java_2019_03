package ru.otus.jtunit.engine;

public class  JTClassSelector{
    private final String className;
    private Class<?> aClass;

    JTClassSelector(Class<?> aClass) {
        this.className = aClass.getName();
        this.aClass = aClass;
    }

    String getClassName() {
        return className;
    }

    Class<?> getJavaClass() {
        return aClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JTClassSelector that = (JTClassSelector) o;

        if (!className.equals(that.className)) return false;
        return aClass.equals(that.aClass);

    }

    @Override
    public int hashCode() {
        int result = className.hashCode();
        result = 31 * result + aClass.hashCode();
        return result;
    }
}
