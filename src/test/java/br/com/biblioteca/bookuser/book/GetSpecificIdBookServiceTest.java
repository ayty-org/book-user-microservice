package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.GetSpecificIdBookServiceImpl;
import br.com.biblioteca.bookuser.exceptions.BookNotAvailableException;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar um book")
public class GetSpecificIdBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private GetSpecificIdBookServiceImpl findBook;

    @BeforeEach
    public void setUp() {
        this.findBook = new GetSpecificIdBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve retornar um livro pesquisando por seu specific id")
    void shouldFindByIdBook() {

        when(bookRepository.findBySpecificID(anyString())).thenReturn(
                Optional.of(createUserApp().status(false).build())
        );

        Book result = this.findBook.findBySpecificID("001");

        //verificação
        assertAll("book",
                () -> assertThat(result.getId(), is(1L)),
                () -> assertThat(result.getAuthor(), is("teste author")),
                () -> assertThat(result.getResume(), is("teste resume")),
                () -> assertThat(result.getIsbn(), is("teste isbn")),
                () -> assertThat(result.getTitle(), is("teste title")),
                () -> assertThat(result.getYearBook(), is(LocalDate.ofEpochDay(25 - 04 - 2020))),
                () -> assertThat(result.isStatus(), is(false)),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertNull(result.getLoanSpecificID())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não for encontrado")
    void shouldThrowBookNotFoundException() {
        when(bookRepository.findBySpecificID(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> this.findBook.findBySpecificID("001"));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não estiver disponivel para emprestimos")
    void shouldThrowBookNotAvailableException() {
        when(bookRepository.findBySpecificID(anyString())).thenReturn(Optional.of(createUserApp().build()));
        assertThrows(BookNotAvailableException.class, () -> this.findBook.findBySpecificID("001"));
    }


}
