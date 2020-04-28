package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListBookSpecificIdServiceImpl implements ListBookSpecificIdService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllSpecificId(String id) {
        return bookRepository.findAllSpecificID(id);
    }
}
