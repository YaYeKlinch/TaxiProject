package ua.project.services.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.UserDto;
import ua.project.entity.enums.Role;
import ua.project.entity.User;
import ua.project.exceptions.UserAlreadyExistException;
import ua.project.repos.UserRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements  UserDetailsService, UserService{

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    UserRepository userRepository;

    private  PasswordEncoder passwordEncoder;



    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("trying to find user with username " + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User createAndSaveUser(UserDto userDto) {
        logger.info("trying to register user with username " + userDto.getEmail());
        if (emailExists(userDto.getEmail())) {
            logger.info("tried to register user with username " + userDto.getEmail() +
                    ", throwing UserAlreadyExistException");
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            +  userDto.getEmail());
        }
        User userToCreate = new User();
        userToCreate.setFirsName(userDto.getFirstName());
        userToCreate.setLastName(userDto.getLastName());
        userToCreate.setActive(true);
        userToCreate.setPassword(encodePassword(userDto.getPassword()));
        userToCreate.setUsername(userDto.getEmail());
        userToCreate.setRoles(Collections.singleton(Role.USER));
        return userRepository.save(userToCreate);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
    private boolean emailExists(String email){
        return userRepository.findByUsername(email) != null;
    }

}
