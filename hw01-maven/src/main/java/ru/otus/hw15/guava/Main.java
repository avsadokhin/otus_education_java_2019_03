package ru.otus.hw15.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Predicate<String> predicate = input -> input.contains("@");

     /*   Function<String, String> funcEmailDomain = new Function<String, String>() {
            @Override
            public String apply(String input) {
                return input.substring(input.indexOf("@") + 1);
            }
        };*/
        Function<String, String> funcEmailDomain = input-> input.substring(input.indexOf("@") + 1);

        List<String> names = Lists.newArrayList("vasya@mail.ru", "vanya@yandex.ru", "john@google.com", "max@yandex.ru");

        Collection<String> result;
        result = FluentIterable.from(names)
                .filter(predicate)
                .transform(funcEmailDomain)
                .toSet();

        System.out.println("Список почтовых доменов:");
        for (String s : result) {
            System.out.println(s);
        }
    }
}