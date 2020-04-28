package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetBookServiceImpl implements GetBookService {

    private final BookRepository bookRepository;

    public Book find(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        book.setSpecificID("00" + id);
        return book;
    }

}
