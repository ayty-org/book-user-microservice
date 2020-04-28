package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookIntegrityException;
import br.com.biblioteca.bookuser.exceptions.BookNotDeletedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DeleteBookServiceImpl implements DeleteBookService {

    private final BookRepository bookRepository;

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotDeletedException();
        }
        Optional<Book> bookApp = bookRepository.findById(id);
        if (bookApp.isPresent()){
            Book book = bookApp.get();
            if (book.getLoanSpecificID() != null){
                throw new BookIntegrityException();
            }
        }
        bookRepository.deleteById(id);
    }
}
