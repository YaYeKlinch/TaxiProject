package ua.project.services.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.project.controller.dto.UserDto;
import ua.project.repos.UserRepository;

@Service
class UserServiceImplTest implements UserDetailsService,UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public void createAndSaveUser(UserDto userDto) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}