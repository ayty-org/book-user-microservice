package br.com.biblioteca.bookuser.book.services;

@FunctionalInterface
public interface UpdateStatusBookService {

    void updateStatusBook(boolean status, String id);
}
