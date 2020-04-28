package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.user.UserApp;

@FunctionalInterface
public interface GetSpecificIdBookService {

    Book findBySpecificID(String specificID);
}
