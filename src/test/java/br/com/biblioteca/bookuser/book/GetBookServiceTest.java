package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.GetBookServiceImpl;
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

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar um book")
public class GetBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private GetBookServiceImpl findBook;

    @BeforeEach
    public void setUp() {
        this.findBook = new GetBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve retornar um livro")
    void shouldFindByIdBook() { // testando buscar livro por id

        when(bookRepository.findById(anyLong())).thenReturn(
                Optional.of(createBook().id(1L).author("Author Teste GET").build())
        );

        Book result = this.findBook.find(1L);

        //verificação
        assertAll("book",
                () -> assertThat(result.getId(), is(1L)),
                () -> assertThat(result.getAuthor(), is("Author Teste GET")),
                () -> assertThat(result.getResume(), is("teste resume")),
                () -> assertThat(result.getIsbn(), is("teste isbn")),
                () -> assertThat(result.getTitle(), is("teste title")),
                () -> assertThat(result.getYearBook(), is(LocalDate.ofEpochDay(25 - 04 - 2020))),
                () -> assertThat(result.isStatus(), is(true)),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertThat(result.getLoanSpecificID(), is("null"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não for encontrado")
    void shouldThrowBookNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> this.findBook.find(1L));
    }
}
