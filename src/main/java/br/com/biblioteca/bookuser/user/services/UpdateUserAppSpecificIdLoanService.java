package br.com.biblioteca.bookuser.user.services;

@FunctionalInterface
public interface UpdateUserAppSpecificIdLoanService {

    void update(String loanUserAppSpecificIdDTO, String id);
}
