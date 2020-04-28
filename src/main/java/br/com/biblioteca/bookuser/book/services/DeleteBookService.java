package br.com.biblioteca.bookuser.book.services;

@FunctionalInterface
public interface DeleteBookService {

    void delete(Long id);
}
