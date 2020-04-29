package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.ListBookServiceImpl;
import br.com.biblioteca.bookuser.book.services.ListBookSpecificIdServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar todos os books")
public class ListBookSpecificIdServiceTest {

    @Mock
    private BookRepository bookRepository;

    private ListBookSpecificIdServiceImpl findAllBook;

    @BeforeEach
    public void setUp() {
        this.findAllBook = new ListBookSpecificIdServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os livros por seu specific id")
    void shouldFindAllBook() {

        when(bookRepository.findAllSpecificID(anyString())).thenReturn(
                Stream.of(createBook().author("Author Teste GET 01").specificID("001").loanSpecificID("001").build(),
                        createBook().author("Author Teste GET 02").specificID("002").loanSpecificID("002").build()).collect(Collectors.toList())
        );

        List<Book> result = this.findAllBook.findAllSpecificId("001");

        assertAll("books",
                () -> assertThat(result.size(), is(2)),
                () -> assertThat(result.get(0).getAuthor(), is("Author Teste GET 01")),
                () -> assertThat(result.get(0).getResume(), is("teste resume")),
                () -> assertThat(result.get(0).getIsbn(), is("teste isbn")),
                () -> assertThat(result.get(0).getTitle(), is("teste title")),
                () -> assertThat(result.get(0).getYearBook(), is(LocalDate.ofEpochDay(25 - 04 - 2020))),
                () -> assertThat(result.get(0).isStatus(), is(true)),
                () -> assertThat(result.get(0).getSpecificID(), is("001")),
                () -> assertThat(result.get(0).getLoanSpecificID(), is("001")),
                () -> assertThat(result.get(1).getAuthor(), is("Author Teste GET 02")),
                () -> assertThat(result.get(1).getResume(), is("teste resume")),
                () -> assertThat(result.get(1).getIsbn(), is("teste isbn")),
                () -> assertThat(result.get(1).getTitle(), is("teste title")),
                () -> assertThat(result.get(1).getYearBook(), is(LocalDate.ofEpochDay(25 - 04 - 2020))),
                () -> assertThat(result.get(1).isStatus(), is(true)),
                () -> assertThat(result.get(1).getSpecificID(), is("002")),
                () -> assertThat(result.get(1).getLoanSpecificID(), is("002"))
        );
    }
}
