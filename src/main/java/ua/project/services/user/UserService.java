package ua.project.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import ua.project.controller.dto.UserDto;
import ua.project.entity.User;

public interface UserService extends UserDetailsService {
   User createAndSaveUser(UserDto userDto);
}
