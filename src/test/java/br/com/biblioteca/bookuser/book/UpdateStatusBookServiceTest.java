package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.UpdateBookSpecificIdLoanServiceImpl;
import br.com.biblioteca.bookuser.book.services.UpdateStatusBookServiceImpl;
import br.com.biblioteca.bookuser.exceptions.BookNotAvailableException;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por atualizar o status do book")
public class UpdateStatusBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private UpdateStatusBookServiceImpl updateStatusBookService;

    @BeforeEach
    public void setUp() {
        this.updateStatusBookService = new UpdateStatusBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve atualizar o loan specific id do livro")
    void shouldUpdateBook() {

        when(bookRepository.findBySpecificID(anyString())).thenReturn(Optional.of(createBook().id(1L).loanSpecificID("001").build()));

        updateStatusBookService.updateStatusBook(false, "001");

        ArgumentCaptor<Book> captorBook = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captorBook.capture());

        Book result = captorBook.getValue();

        assertAll("book",
                () -> assertThat(result.getId(), is(1L)),
                () -> assertThat(result.getAuthor(), is("teste author")),
                () -> assertThat(result.getResume(), is("teste resume")),
                () -> assertThat(result.getIsbn(), is("teste isbn")),
                () -> assertThat(result.getTitle(), is("teste title")),
                () -> assertThat(result.getYearBook(), is(LocalDate.ofEpochDay(25 - 04 - 2020))),
                () -> assertThat(result.isStatus(), is(false)),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertThat(result.getLoanSpecificID(), is("001"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não for encontrado ao tentar atualizar o specificic id de loan no livro")
    void shouldThrowBookNotFoundException() {
        when(bookRepository.findBySpecificID(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotAvailableException.class, () -> this.updateStatusBookService.updateStatusBook(anyBoolean(),"001"));
    }
}
