package kg.attractor.instagram.service;

import jakarta.validation.constraints.Email;
import kg.attractor.instagram.dto.UserDto;
import kg.attractor.instagram.exceptions.EmailAlreadyExistsException;
import kg.attractor.instagram.exceptions.UserNotFoundException;
import kg.attractor.instagram.model.Role;
import kg.attractor.instagram.model.User;
import kg.attractor.instagram.repository.UserRepository;
import kg.attractor.instagram.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final FileUtil fileUtil;

    public void register(UserDto userDto) throws EmailAlreadyExistsException {
        boolean isExistsEmail = userRepository.existsUserByEmail(userDto.getEmail());

        Role role = roleService.findRoleByName("ROLE_USER");
        User user = convertUserDtoToUser(userDto);
        user.setRoles(List.of(role));
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEnabled(true);

        user.setAvatar(fileUtil.saveUploadFile(userDto.getAvatar(), "Files"));

        if(isExistsEmail) {
            log.info("{} email already exists", userDto.getEmail());
            throw new EmailAlreadyExistsException();
        }
        userRepository.saveAndFlush(user);
        log.info("{} registered", userDto.getEmail());
    }

    public User convertUserDtoToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .description(userDto.getDescription())
                .build();
    }

    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }
}
