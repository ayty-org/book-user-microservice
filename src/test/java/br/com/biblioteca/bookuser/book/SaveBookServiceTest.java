package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.SaveBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por salvar um book")
public class SaveBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private SaveBookServiceImpl saveBook;

    @BeforeEach
    public void setUp() {
        this.saveBook = new SaveBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    void shouldSaveBook() { //testando save book

        //execução
        saveBook.insert(createUserApp().build());

        //preparação
        ArgumentCaptor<Book> captorBook = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository, times(2)).save(captorBook.capture());

        Book result = captorBook.getValue();

        //verificação
        assertAll("book",
                () -> assertThat(result.getAuthor(), is("teste author")),
                () -> assertThat(result.getResume(), is("teste resume")),
                () -> assertThat(result.getIsbn(), is("teste isbn")),
                () -> assertThat(result.getTitle(), is("teste title"))
        );
    }
}
