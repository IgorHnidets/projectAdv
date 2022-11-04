package com.logos.projectadv.service.Impl;

import com.logos.projectadv.models.User;
import com.logos.projectadv.repository.UserRepository;
import com.logos.projectadv.service.UserService;
import com.logos.projectadv.utills.FileUtills;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
