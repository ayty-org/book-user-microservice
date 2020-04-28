package br.com.biblioteca.bookuser.book.services;

import br.com.biblioteca.bookuser.book.Book;
import org.springframework.data.domain.Page;

@FunctionalInterface
public interface ListPageBookService {

    Page<Book> findPage(Integer page, Integer size);
}
