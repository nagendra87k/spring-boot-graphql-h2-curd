package com.nagendra.graphqlh2db.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.nagendra.graphqlh2db.exception.DataNotFoundException;
import com.nagendra.graphqlh2db.model.Author;
import com.nagendra.graphqlh2db.model.Book;
import com.nagendra.graphqlh2db.repositories.AuthorRepository;
import com.nagendra.graphqlh2db.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookMutation implements GraphQLMutationResolver {
	private AuthorRepository authorRepository;
	private BookRepository bookRepository;

	@Autowired
	public BookMutation(AuthorRepository authorRepository, BookRepository bookRepository) {
		this.authorRepository = authorRepository;
		this.bookRepository = bookRepository;
	}

	public Author createAuthor(String name, Integer age) {
		Author author = new Author();
		author.setName(name);
		author.setAge(age);

		authorRepository.save(author);

		return author;
	}

	public Book createBook(String title, String description, Long authorId) {
		Book book = new Book();
		book.setAuthor(new Author(authorId));
		book.setTitle(title);
		book.setDescription(description);

		bookRepository.save(book);

		return book;
	}

	public boolean deleteBook(Long id) {
		bookRepository.deleteById(id);
		return true;
	}

	public Book updateBook(Long id, String title, String description) throws DataNotFoundException {
		Optional<Book> optBook = bookRepository.findById(id);

		if (optBook.isPresent()) {
			Book Book = optBook.get();

			if (title != null)
				Book.setTitle(title);
			if (description != null)
				Book.setDescription(description);

			bookRepository.save(Book);
			return Book;
		}

		throw new DataNotFoundException("Not found Book to update!");
	}
}