package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateBookSpecificIdLoanServiceImpl implements UpdateBookSpecificIdLoanService {

    private final BookRepository bookRepository;

    @Override
    public void updateSpecificId(String loanBookSpecificIdDTO, String id) {
        Book bookApp = bookRepository.findBySpecificID(id).orElseThrow(BookNotFoundException::new);
        bookApp.setLoanSpecificID(loanBookSpecificIdDTO);
        bookRepository.save(bookApp);
    }
}
