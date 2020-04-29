package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.LoanBookSpecificIdDTO;

@FunctionalInterface
public interface UpdateBookSpecificIdLoan {

    void updateSpecificId(LoanBookSpecificIdDTO loanBookSpecificIdDTO, String id);
}
