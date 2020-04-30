package br.com.biblioteca.bookuser.book.builders;

import br.com.biblioteca.bookuser.book.Book;

import java.time.LocalDate;

public class BookBuilder {

    public static Book.Builder createUserApp() {
        return Book
                .builder()
                .id(1L)
                .author("teste author")
                .resume("teste resume")
                .isbn("teste isbn")
                .title("teste title")
                .yearBook(LocalDate.ofEpochDay(25 - 04 - 2020))
                .status(true)
                .specificID("001")
                .loanSpecificID(null);
    }

}
