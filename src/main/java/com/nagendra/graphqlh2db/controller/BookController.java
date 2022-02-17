package com.nagendra.graphqlh2db.controller;

import com.nagendra.graphqlh2db.model.Book;
import com.nagendra.graphqlh2db.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book")
    public ResponseEntity<List<Book>> findAllBooks(){
        List<Book> books = bookRepository.findAll();
        try{
            if(books.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books,HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable("id") Long id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        try{
            Book _book = bookRepository.save(book);
            return new ResponseEntity<>(_book, HttpStatus.CREATED);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody Book book){
        Optional<Book> _book = bookRepository.findById(id);
        if(_book.isPresent()){
            Book updateBook = _book.get();
            updateBook.setAuthor(book.getAuthor());
            updateBook.setTitle(book.getTitle());
            updateBook.setDescription(book.getDescription());
            return new ResponseEntity<>(bookRepository.save(updateBook), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable("id") Long id){

        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception ex){
            logger.error(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
