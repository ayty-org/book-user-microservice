package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;

@FunctionalInterface
public interface GetBookService {

    Book find(Long id);

}
