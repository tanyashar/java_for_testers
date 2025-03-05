package ru.stqa.mantis.manager;

import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

public class JamesCliHelper extends HelperBase{
    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) {
        // запустить почтовый сервер
        // cd "C:\Users\tanya\james-server-jpa-guice"
        // + см. примечания к лекции 8.4 при ошибке авторизации

        // в первый раз: java -Dworking.directory=. -jar james-server-jpa-app.jar --generate-keystore
        // далее: java -Dworking.directory=. -jar james-server-jpa-app.jar

        // пример команды в cmd - добавить юзера:
        // java -cp "james-server-jpa-app.lib/*" org.apache.james.cli.ServerCmd AddUser email@localhost password

        // получить список юзеров
        // java -cp "james-server-jpa-app.lib/*" org.apache.james.cli.ServerCmd ListUsers

        CommandLine cmd = new CommandLine(
                "java",
                "-cp",
                "\"james-server-jpa-app.lib/*\"",
                "org.apache.james.cli.ServerCmd",
                "AddUser", email, password);

        cmd.setWorkingDirectory(manager.property("james.workingDir"));
        // сохранить вывод из консоли, к-й получили после исполнения команды
        CircularOutputStream out = new CircularOutputStream();
        cmd.copyOutputTo(out);

        cmd.execute();
        cmd.waitFor();

        System.out.println(out);
    }
}
