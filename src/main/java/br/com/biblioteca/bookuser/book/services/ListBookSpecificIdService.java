package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.user.UserApp;

import java.util.List;

@FunctionalInterface
public interface ListBookSpecificIdService {

    List<Book> findAllSpecificId(String id);
}
