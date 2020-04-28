package br.com.biblioteca.bookuser.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBySpecificID(String specificId);

    @Query(value = "SELECT * FROM book WHERE loan_specificid = :id", nativeQuery = true)
    List<Book> findAllSpecificID(@Param("id") String id);
}
