package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.ListPageBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar books por paginação")
public class ListPageBookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ListPageBookServiceImpl listPageBook;

    @BeforeEach
    public void setUp() {
        this.listPageBook = new ListPageBookServiceImpl(bookRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os livros com paginação")
    void shouldFindAllBook() {

        Pageable pageable = PageRequest.of(0, 2);

        when(listPageBook.findPage(pageable)).thenReturn(new PageImpl<>(Collections.nCopies(2, createBook().build())));

        Page<Book> userAppPage = listPageBook.findPage(pageable);

        //verificação
        assertAll("book",
                () -> assertThat(userAppPage.getNumber(), is(0)),
                () -> assertThat(userAppPage.getSize(), is(2)),
                () -> assertThat(userAppPage.getContent().get(0).getTitle(), is("teste title")),
                () -> assertThat(userAppPage.getContent().get(0).getResume(), is("teste resume")),
                () -> assertThat(userAppPage.getContent().get(0).getIsbn(), is("teste isbn")),
                () -> assertThat(userAppPage.getContent().get(0).getAuthor(), is("teste author")),
                () -> assertThat(userAppPage.getContent().get(1).getTitle(), is("teste title")),
                () -> assertThat(userAppPage.getContent().get(1).getResume(), is("teste resume")),
                () -> assertThat(userAppPage.getContent().get(1).getIsbn(), is("teste isbn")),
                () -> assertThat(userAppPage.getContent().get(1).getAuthor(), is("teste author"))
        );
    }

}
