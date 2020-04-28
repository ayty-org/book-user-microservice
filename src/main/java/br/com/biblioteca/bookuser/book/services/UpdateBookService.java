package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;

@FunctionalInterface
public interface UpdateBookService {

    void update(Book book, Long id);
}
