package com.example.giaybongda.service;

import com.example.giaybongda.model.Category;
import com.example.giaybongda.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository cateRepository;

    public List<Category> findAll() {
        return cateRepository.findAll();
    }

}
