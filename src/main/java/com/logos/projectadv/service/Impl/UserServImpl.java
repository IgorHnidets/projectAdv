package com.logos.projectadv.service.Impl;

import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.UserRepository;
import com.logos.projectadv.service.UserService;
import com.logos.projectadv.utills.FileUtills;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        Optional<User> byId = userRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public User save(User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()){
            throw new RuntimeException("User is already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        User userById = getUserById(id);
        userRepository.delete(userById);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserFromSession() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        Object userId = session.getAttribute("userId");
        Optional<User> byId = userRepository.findById((Integer) userId);
        return byId.orElse(null);
    }

    @Override
    public User getUserFromPrincipal(Principal principal) {
        User byEmailOrderByEmail = userRepository.findByEmailOrderByEmail(principal.getName());
        return byEmailOrderByEmail;
    }

    @Override
    public void setIdInSession(String nameId, int id) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        session.setAttribute(nameId,id);
    }

    @Override
    public String transferFile(MultipartFile file) {
        String pathToFolder = FileUtills.getImagesFolder();
        try {
            file.transferTo(new File(pathToFolder + file.getOriginalFilename()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file.getOriginalFilename();
    }
}
