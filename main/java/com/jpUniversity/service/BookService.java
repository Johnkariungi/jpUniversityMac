package com.jpUniversity.service;

import com.jpUniversity.domain.Book;

import java.util.List;

public interface BookService {
    List<com.jpUniversity.domain.Book> findAll();  /*method that returns all the books*/
    Book findOne(Long id);
}
