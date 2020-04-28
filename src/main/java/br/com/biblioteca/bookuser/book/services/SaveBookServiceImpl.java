package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveBookServiceImpl implements SaveBookService {

    private final BookRepository bookRepository;

    @Override
    public void insert(Book book) {
        bookRepository.save(book);
        book.setSpecificID(gerarSpecificId(book.getId()));
        bookRepository.save(book);
    }

    public static String gerarSpecificId(Long id) {
        return "00" + id;
    }

}

