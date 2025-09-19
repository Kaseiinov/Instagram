package kg.attractor.instagram.controller;

import jakarta.validation.Valid;
import kg.attractor.instagram.dto.UserDto;
import kg.attractor.instagram.exceptions.EmailAlreadyExistsException;
import kg.attractor.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult, Model model) throws EmailAlreadyExistsException {
        if (userDto.getAvatar() == null || userDto.getAvatar().isEmpty()) {
            bindingResult.rejectValue("avatar", "file.empty", "Please upload an avatar");
        }
        if(!bindingResult.hasErrors() && !userDto.getAvatar().isEmpty() ) {
            userService.register(userDto);
            return "redirect:/auth/login";
        }
        model.addAttribute("userDto", userDto);
        return "auth/register";
    }

    @GetMapping("/login")
    public String showLogin(){
        return "auth/login";
    }

}
