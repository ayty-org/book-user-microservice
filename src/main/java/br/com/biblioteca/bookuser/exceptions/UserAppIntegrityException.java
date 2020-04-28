package br.com.biblioteca.bookuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserAppIntegrityException extends RuntimeException {

    public UserAppIntegrityException() {
        super("Não é possivel deletar usuário que tem emprestimos!");
    }
}
