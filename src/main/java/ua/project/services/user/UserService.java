package ua.project.services.user;

import ua.project.controller.dto.UserDto;

public interface UserService {
    void createAndSaveUser(UserDto userDto);
}
