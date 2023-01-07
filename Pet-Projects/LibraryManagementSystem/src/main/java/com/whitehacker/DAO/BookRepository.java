package com.whitehacker.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.whitehacker.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

}
