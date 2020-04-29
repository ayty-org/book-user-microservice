package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookNotAvailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateStatusBookImpl implements UpdateStatusBook {

    private final BookRepository bookRepository;

    @Override
    public void updateStatusBook(boolean status, String id) {
        Book book = bookRepository.findBySpecificID(id).orElseThrow(BookNotAvailableException::new);
        book.setStatus(status);
        bookRepository.save(book);
    }
}
