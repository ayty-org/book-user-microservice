package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSpecificIdBookServiceImpl implements GetSpecificIdBookService {

    private final BookRepository bookRepository;

    @Override
    public Book findBySpecificID(String specificId) {
        return bookRepository.findBySpecificID(specificId).orElseThrow(BookNotFoundException::new);
    }
}
