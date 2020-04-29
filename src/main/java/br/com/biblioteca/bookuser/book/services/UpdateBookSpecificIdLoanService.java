package br.com.biblioteca.bookuser.book.services;

@FunctionalInterface
public interface UpdateBookSpecificIdLoanService {

    void updateSpecificId(String loanBookSpecificIdDTO, String id);
}
