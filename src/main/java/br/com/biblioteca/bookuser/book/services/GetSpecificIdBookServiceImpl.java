package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookNotAvailableException;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSpecificIdBookServiceImpl implements GetSpecificIdBookService {

    private final BookRepository bookRepository;

    @Override
    public Book findBySpecificID(String specificId) {
        Book book = bookRepository.findBySpecificID(specificId).orElseThrow(BookNotFoundException::new);
        if (book.isStatus()){
            throw new BookNotAvailableException();
        }
        return book;
    }
}
