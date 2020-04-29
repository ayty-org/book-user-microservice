package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookRepository;
import br.com.biblioteca.bookuser.book.LoanBookSpecificIdDTO;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateBookSpecificIdLoanImpl implements UpdateBookSpecificIdLoan {

    private final BookRepository bookRepository;

    @Override
    public void updateSpecificId(LoanBookSpecificIdDTO loanBookSpecificIdDTO, String id) {
        Book bookApp = bookRepository.findBySpecificID(id).orElseThrow(BookNotFoundException::new);
        bookApp.setLoanSpecificID(loanBookSpecificIdDTO.getLoanSpecificID());
        bookRepository.save(bookApp);
    }
}
