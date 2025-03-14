package ru.stqa.mantis.manager.developermail;

import ru.stqa.mantis.model.DeveloperMailUser;

public record AddUserResponse(boolean success, Object errors, DeveloperMailUser user) {

}
