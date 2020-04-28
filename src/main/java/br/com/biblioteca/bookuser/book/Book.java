package br.com.biblioteca.bookuser.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(builderClassName = "Builder")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; //titulo

    private String resume; //resumo

    private String isbn;

    private String author; //autor

    private LocalDate yearBook; //ano

    private String specificID;

    private String loanSpecificID;

   public Book(String title, String resume, String isbn, String author) {
        this.title = title;
        this.resume = resume;
        this.isbn = isbn;
        this.author = author;
    }

    public static Book to(BookDTO bookDTO) {
        return Book
                .builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .resume(bookDTO.getResume())
                .isbn(bookDTO.getIsbn())
                .author(bookDTO.getAuthor())
                .yearBook(bookDTO.getYearBook())
                .loanSpecificID(bookDTO.getLoanSpecificID())
                .build();
    }
}
