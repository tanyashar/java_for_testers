package ru.stqa.mantis.manager;

import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.api.IssuesApi;
import io.swagger.client.auth.ApiKeyAuth;
import io.swagger.client.model.Identifier;
import io.swagger.client.model.Issue;
import ru.stqa.mantis.model.IssueData;

public class RestApiHelper extends HelperBase {
    public RestApiHelper(ApplicationManager manager) {
        super(manager);

        // см. "basePath" в swagger.json - должен быть указан /{локальный адрес сервиса mantis} затем /api/rest
        // используем сгенерированный код, к-й генерируется из описания веб-сервиса при помощи swagger'a'

        // токен для определенной УЗ в mantis bugtracker
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        ApiKeyAuth Authorization = (ApiKeyAuth) defaultClient.getAuthentication("Authorization");
        Authorization.setApiKey(manager.property("apiKey"));
    }

    public void createIssue(IssueData issueData) {
        // эти методы взяты из файла ../mantis_tests/build/swagger-code-mantis/docs/IssuesApi.md
        Issue issue = new Issue(); // Issue | The issue to add.

        // задаем обязательные свойства для issue
        issue.setSummary(issueData.summary());
        issue.setDescription(issueData.description());

        var projectId = new Identifier();
        projectId.setId(issueData.project());
        issue.setProject(projectId);

        var categoryId = new Identifier();
        categoryId.setId(issueData.category());
        issue.setCategory(categoryId);

        IssuesApi apiInstance = new IssuesApi();
        try {
            apiInstance.issueAdd(issue);
        } catch (ApiException e) {
            new RuntimeException(e);
        }
    }
}
