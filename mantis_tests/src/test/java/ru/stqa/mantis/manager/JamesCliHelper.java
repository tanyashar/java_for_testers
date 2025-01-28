package ru.stqa.mantis.manager;

import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

public class JamesCliHelper extends HelperBase{
    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) {
        // пример команды в cmd - добавить юзера:
        // java -cp "james-server-jpa-app.lib/*" org.apache.james.cli.ServerCmd AddUser user1@localhost password

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
