package com.logos.projectadv.service;

import com.logos.projectadv.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    User getUserById(int id);
    Optional<User> getByUsername(String username);
    User save(User user);
    void delete(int id);
    User update(User user);

    String transferFile(MultipartFile file);
}
