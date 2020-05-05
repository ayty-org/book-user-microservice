package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookIntegrityException;
import br.com.biblioteca.bookuser.exceptions.BookNotDeletedException;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteBookServiceImpl implements DeleteBookService {

    private final BookRepository bookRepository;

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotDeletedException();
        }
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        if (!book.getLoanSpecificID().equals("null")) {
            throw new BookIntegrityException();
        }
        bookRepository.deleteById(id);
    }

}
