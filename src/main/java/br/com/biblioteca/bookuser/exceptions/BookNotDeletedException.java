package br.com.biblioteca.bookuser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotDeletedException extends RuntimeException{

    public BookNotDeletedException() {
        super("Livro não deletado!");
    }
}
