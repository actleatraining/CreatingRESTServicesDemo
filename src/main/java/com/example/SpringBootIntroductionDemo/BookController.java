package com.example.SpringBootIntroductionDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookRepository repository;

    @GetMapping("/book/{id}")
    public Book books(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/book")
    public Book post(@RequestBody Book book) {
        return repository.save(book);
    }
}
