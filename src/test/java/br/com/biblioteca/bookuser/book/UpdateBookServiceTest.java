package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.UpdateBookServiceImpl;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por atualizar book")
public class UpdateBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private UpdateBookServiceImpl updateBook;

    @BeforeEach
    public void setUp() {
        this.updateBook = new UpdateBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve atualizar um livro")
    void shouldUpdateBook() { // testando atualizar livro

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(createBook().id(1L).build()));

        updateBook.update(createBook().author("teste update").build(), 1L);

        ArgumentCaptor<Book> captorBook = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captorBook.capture());

        Book result = captorBook.getValue();

        assertAll("book",
                () -> assertThat(result.getAuthor(), is("teste update")),
                () -> assertThat(result.getResume(), is("teste resume")),
                () -> assertThat(result.getIsbn(), is("teste isbn")),
                () -> assertThat(result.getTitle(), is("teste title"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o livro não for encontrado")
    void shouldThrowBookNotFoundException() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> this.updateBook.update(createBook().build(),1L));
    }
}

