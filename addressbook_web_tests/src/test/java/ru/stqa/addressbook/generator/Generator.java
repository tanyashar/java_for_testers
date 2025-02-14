package ru.stqa.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import ru.stqa.addressbook.common.CommonFunctions;
import ru.stqa.addressbook.model.ContactData;
import ru.stqa.addressbook.model.GroupData;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generator {
    // Generator - запускаемый класс (runnable class), поэтому внутри него будет функция main
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
        else if ("yaml".equals(format)) {
            var mapper = new YAMLMapper();
            mapper.writeValue(new File(output), data);
        }
        else if ("xml".equals(format)) {
            var mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(output), data);
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

    private Object generateData(Supplier<Object> dataSupplier) {
        return Stream
                .generate(dataSupplier)
                .limit(count)
                .collect(Collectors.toList());
    }

    private Object generateContact() {
        return generateData(() -> new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
        );
    }

    private Object generateGroup() {
        Supplier<Object> supplier =
          () -> new GroupData()
                .withName(CommonFunctions.randomString(10))
                .withHeader(CommonFunctions.randomString(10))
                .withFooter(CommonFunctions.randomString(10))
        ;
        return generateData(supplier);
    }
}
