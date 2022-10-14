package com.hiwijaya.springlogging.controller;

import com.hiwijaya.springlogging.entity.Book;
import com.hiwijaya.springlogging.model.LoginRequest;
import com.hiwijaya.springlogging.model.SaveBookRequest;
import com.hiwijaya.springlogging.service.AuthService;
import com.hiwijaya.springlogging.service.BookService;
import com.hiwijaya.springlogging.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ApiController {

    @Autowired
    private AuthService authService;

    @Autowired
    private BookService bookService;


    @GetMapping("/")
    public ResponseEntity<Object> index() {
        return ResponseHandler.createResponse("SPRING-LOGGING");
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest body) {

        String token = authService.login(body);

        return ResponseHandler.createResponse(token);
    }

    @GetMapping("/book")
    public ResponseEntity<Object> getBook(@RequestParam(required = false) Integer id){

        if(id == null){

            List<Book> books = bookService.getAllBooks();

            return ResponseHandler.createResponse(books);
        }

        Book book = bookService.getBookById(id);

        return ResponseHandler.createResponse(book);
    }


    @PostMapping("/book")
    public ResponseEntity<Object> save(@RequestBody SaveBookRequest body){

        bookService.save(body);

        return ResponseHandler.ok();
    }

    @PutMapping("/book")
    public ResponseEntity<Object> update(@RequestBody SaveBookRequest body){

        bookService.save(body);

        return ResponseHandler.ok();
    }

    @DeleteMapping("book/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){

        bookService.delete(id);

        return ResponseHandler.ok();
    }

    
}
