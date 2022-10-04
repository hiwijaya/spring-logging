package com.hiwijaya.springlogging.service;

import com.hiwijaya.springlogging.entity.Book;
import com.hiwijaya.springlogging.model.SaveBookRequest;
import com.hiwijaya.springlogging.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(SaveBookRequest req){

        Book book = Book.builder()
                .id(req.getId())
                .title(req.getTitle())
                .author(req.getAuthor())
                .price(req.getPrice())
                .build();

        return bookRepository.save(book);
    }

    public void delete(Integer bookId){

        Book book = bookRepository.findById(bookId).orElseThrow();

        bookRepository.delete(book);

    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


}
