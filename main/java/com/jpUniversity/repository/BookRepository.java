package com.jpUniversity.repository;

import com.jpUniversity.domain.Book;
import org.springframework.data.repository.CrudRepository;


public interface BookRepository extends CrudRepository<Book, Long> {

}
