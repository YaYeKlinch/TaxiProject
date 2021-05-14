package ua.project.services.user;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.UserDto;
import ua.project.repos.UserRepository;

@Service
@AllArgsConstructor
class UserServiceImplTest implements UserDetailsService,UserService {
    UserRepository userRepository;

    @Override
    public void createAndSaveUser(UserDto userDto) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}