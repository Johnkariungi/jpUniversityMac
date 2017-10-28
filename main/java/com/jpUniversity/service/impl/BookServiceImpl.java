package com.jpUniversity.service.impl;

import com.jpUniversity.domain.Book;
import com.jpUniversity.repository.BookRepository;
import com.jpUniversity.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRespository;

    public List<Book> findAll() {
        return (List<Book>)bookRespository.findAll();
    }

    public Book findOne(Long id) { return bookRespository.findOne(id); }
}
