package br.com.biblioteca.bookuser.book.services;

@FunctionalInterface
public interface UpdateStatusBook {

    void updateStatusBook(boolean status, String id);
}
