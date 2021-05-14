package ua.project.services.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.UserDto;
import ua.project.entity.enums.Role;
import ua.project.entity.User;
import ua.project.exceptions.UserAlreadyExistException;
import ua.project.repos.UserRepository;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService{

    final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public void createAndSaveUser(UserDto userDto) {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }
        User userToCreate = new User();
        userToCreate.setFirsName(userDto.getFirstName());
        userToCreate.setLastName(userDto.getLastName());
        userToCreate.setActive(true);
        userToCreate.setPassword(userDto.getPassword());
        userToCreate.setUsername(userDto.getEmail());
        userToCreate.setRoles(Collections.singleton(Role.USER));
        userRepository.save(userToCreate);
    }
    private boolean emailExists(String email){
        return userRepository.findByUsername(email) != null;
    }

}
