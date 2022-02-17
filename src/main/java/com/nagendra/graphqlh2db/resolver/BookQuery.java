package com.nagendra.graphqlh2db.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.nagendra.graphqlh2db.exception.DataNotFoundException;
import com.nagendra.graphqlh2db.model.Author;
import com.nagendra.graphqlh2db.model.Book;
import com.nagendra.graphqlh2db.repositories.AuthorRepository;
import com.nagendra.graphqlh2db.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookQuery implements GraphQLQueryResolver {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @Autowired
    public BookQuery(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    public Book findBookByID(Long id) throws DataNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);;
        if (book == null) throw new DataNotFoundException("book record: not found");
        return book;
    }
    public long countAuthors() {
        return authorRepository.count();
    }
    public long countBooks() {
        return bookRepository.count();
    }
}
