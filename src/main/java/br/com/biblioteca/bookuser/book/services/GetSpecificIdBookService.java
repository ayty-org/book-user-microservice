package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;

@FunctionalInterface
public interface GetSpecificIdBookService {

    Book findBySpecificID(String specificID);
}
