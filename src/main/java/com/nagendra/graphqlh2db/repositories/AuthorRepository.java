package com.nagendra.graphqlh2db.repositories;

import com.nagendra.graphqlh2db.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}