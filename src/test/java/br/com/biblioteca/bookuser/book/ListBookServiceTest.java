package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.ListBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar todos os books")
public class ListBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ListBookServiceImpl findAllBook;

    @BeforeEach
    public void setUp() {
        this.findAllBook = new ListBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os livros")
    void shouldFindAllBook() { // testando buscar livro por id

        when(bookRepository.findAll()).thenReturn(
                Stream.of(createBook().author("Author Teste GET 01").build(),
                        createBook().author("Author Teste GET 02").build(),
                        createBook().author("Author Teste GET 03").build()).collect(Collectors.toList())
        );

        List<Book> result = this.findAllBook.findAll();

        //verificação
        assertAll("book",
                () -> assertThat(result.size(), is(3)),
                () -> assertThat(result.get(0).getAuthor(), is("Author Teste GET 01")),
                () -> assertThat(result.get(1).getAuthor(), is("Author Teste GET 02")),
                () -> assertThat(result.get(2).getAuthor(), is("Author Teste GET 03"))
        );
    }
}
