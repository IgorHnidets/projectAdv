package com.logos.projectadv.service;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.repository.ProductRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Item> getAll();
    Item getItemById(int id);
    Item save(Item item);
    void delete(int id);

    String transferFile(MultipartFile file);
}
