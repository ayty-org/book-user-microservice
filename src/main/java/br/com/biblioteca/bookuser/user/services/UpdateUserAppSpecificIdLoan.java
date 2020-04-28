package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.LoanUserAppSpecificIdDTO;

@FunctionalInterface
public interface UpdateUserAppSpecificIdLoan {

    void update(LoanUserAppSpecificIdDTO loanUserAppSpecificIdDTO, String id);
}
