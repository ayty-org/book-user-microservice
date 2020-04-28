package br.com.biblioteca.bookuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookIntegrityException extends RuntimeException {

    public BookIntegrityException() {
        super("Não é possivel deletar livro que está emprestado!");
    }
}
