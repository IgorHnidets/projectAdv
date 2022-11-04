package com.logos.projectadv.service.Impl;

import com.logos.projectadv.models.Item;
import com.logos.projectadv.repository.ProductRepository;
import com.logos.projectadv.service.ProductService;
import com.logos.projectadv.utills.FileUtills;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Item> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Item getItemById(int id) {
        Optional<Item> byId = productRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public Item save(Item item) {
        return productRepository.save(item);
    }

    @Override
    public void delete(int itemId) {
        Item itemById = getItemById(itemId);
        productRepository.deleteFromBucket(itemId);
        productRepository.delete(itemById);
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
