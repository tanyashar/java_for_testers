package ru.stqa.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.stqa.addressbook.common.Common;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    // Generator - запускаемый класс, поэтому внутри него будет функция main
    // args - аргументы (= опции) командной строки = аргументы запуска программы
    // Edit Configurations - Program arguments: --type groups --output groups.json --format json --count 3

    @Parameter(names={"--type", "-t"})
    String type;

    @Parameter(names={"--output", "-p"})
    String output;

    @Parameter(names={"--format", "-f"})
    String format;

    @Parameter(names={"--count", "-n"})
    int count;

    public static void main(String[] args) throws IOException {
        var generator = new Generator();

        // анализ опций командной строки
        // используем библиотеку для анализа опций командной строки - jcommander
        JCommander.newBuilder()
                .addObject(generator)
                .build()
                .parse(args);

        generator.run();
    }

    private void run() throws IOException {
        // генерация данных
        var data = generate();

        // сохранение данных
        save(data);
    }

    // вместо того, чтобы делать обработку try catch для writeValue
    // декларируем возможность такого исключения в нотации метода через throws IOException
    private void save(Object data) throws IOException {

        if ("json".equals(format)) {
            // работа с файлом - сохранение данных при помощи mapper из библы jackson
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print

            // mapper.writeValue(new File(output), data);

            var json = mapper.writeValueAsString(data);
            try (var writer = new FileWriter(output)) {
                writer.write(json);
            }
            // ИЛИ
            // var writer = new FileWriter(output);
            // writer.write(json);
            // writer.close();
        }
        else {
            throw new IllegalArgumentException("Неизвестный формат данных" + format);
        }
    }

    private Object generate() {
        if ("groups".equals(type))
            return generateGroup();
        else if ("contacts".equals(type))
            return generateContact();
        else
            throw new IllegalArgumentException("Неизвестный тип данных " + type);
    }

    private Object generateContact() {
        return null;
    }

    private Object generateGroup() {
        var result = new ArrayList<GroupData>();
        for (int i = 0; i < 5; i++) {
            result.add(new GroupData()
                    .withName(Common.randomString(i * 10))
                    .withHeader(Common.randomString(i * 10))
                    .withFooter(Common.randomString(i * 10)));
        }

        return result;
    }
}
