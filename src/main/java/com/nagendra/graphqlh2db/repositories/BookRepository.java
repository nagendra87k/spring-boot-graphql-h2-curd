package com.nagendra.graphqlh2db.repositories;

import com.nagendra.graphqlh2db.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
