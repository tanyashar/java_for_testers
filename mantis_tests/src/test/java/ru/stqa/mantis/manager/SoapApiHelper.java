package ru.stqa.mantis.manager;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ObjectRef;
import ru.stqa.mantis.model.IssueData;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class SoapApiHelper extends HelperBase {

    // SOAP = Simple Object Access Protocol - технология RPC (Remote Procedure Call) - более древняя, чем REST
    // Основывается на языке разметки XML

    // описание операций для SOAP mantis: http://localhost/mantisbt-2.25.8/api/soap/mantisconnect.php?wsdl
    // .wsdl = файл с описанием веб-сервиса
    // для wsdl тоже есть плагин для генерации кода по описанию веб-сервиса wsdl2java
    // Но! описание .wsdl для mantis у нас старое, а библиотека - новая, поэтому воспользоваться ей не получится
    // поэтому воспользуемся уже готовой библиотекой

    MantisConnectPortType mantis;

    public SoapApiHelper(ApplicationManager manager) {
        super(manager);

        // инициализация клиента
        try {
            mantis = new MantisConnectLocator().getMantisConnectPort(
                    new URL(manager.property("soap.endPoint")));
        } catch (ServiceException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createIssue(IssueData issueData) {
        try {
            var categories = mantis.mc_project_get_categories(
                    manager.property("web.username"),
                    manager.property("web.password"),
                    BigInteger.valueOf(issueData.project())
            );
            var issue = new biz.futureware.mantis.rpc.soap.client.IssueData();

            issue.setSummary(issueData.summary());
            issue.setDescription(issueData.description());

            var projectId = new ObjectRef();
            projectId.setId(BigInteger.valueOf(issueData.project()));
            issue.setProject(projectId);

            issue.setCategory(categories[0]);

            mantis.mc_issue_add(
                    manager.property("web.username"),
                    manager.property("web.password"),
                    issue);


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
