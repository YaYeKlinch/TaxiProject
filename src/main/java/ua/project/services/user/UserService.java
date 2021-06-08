package ua.project.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.project.controller.dto.UserDto;
import ua.project.entity.User;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    void createAndSaveUser(UserDto userDto);
}
