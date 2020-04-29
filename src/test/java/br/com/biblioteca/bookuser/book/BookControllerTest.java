package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.DeleteBookService;
import br.com.biblioteca.bookuser.book.services.DeleteBookServiceImpl;
import br.com.biblioteca.bookuser.book.services.GetBookService;
import br.com.biblioteca.bookuser.book.services.GetSpecificIdBookService;
import br.com.biblioteca.bookuser.book.services.ListBookService;
import br.com.biblioteca.bookuser.book.services.ListBookSpecificIdService;
import br.com.biblioteca.bookuser.book.services.ListPageBookService;
import br.com.biblioteca.bookuser.book.services.SaveBookService;
import br.com.biblioteca.bookuser.book.services.UpdateBookService;
import br.com.biblioteca.bookuser.book.services.UpdateBookSpecificIdLoan;
import br.com.biblioteca.bookuser.book.v1.BookControllerV1;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createBook;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("Controller")
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BookControllerV1.class)
@DisplayName("Valida funcionalidade do Controller Book")
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetBookService getBookService;
    @MockBean
    private ListBookService listBookService;
    @MockBean
    private ListPageBookService listPageBookService;
    @MockBean
    private SaveBookService saveBookService;
    @MockBean
    private UpdateBookService updateBookService;
    @MockBean
    private DeleteBookService deleteBookService;
    @MockBean
    private GetSpecificIdBookService getSpecificIdBookService;
    @MockBean
    private UpdateBookSpecificIdLoan updateBookSpecificIdLoan;
    @MockBean
    private ListBookSpecificIdService listBookSpecificIdService;

    @Test
    @DisplayName("Pesquisa livro por id")
    void whenValidGetIdBook_thenReturnsBook() throws Exception {

        when(getBookService.find(anyLong())).thenReturn(createBook().id(1L).build());

        mockMvc.perform(get("/v1/api/book/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("teste author")))
                .andExpect(jsonPath("$.resume", is("teste resume")))
                .andExpect(jsonPath("$.isbn", is("teste isbn")))
                .andExpect(jsonPath("$.title", is("teste title")));

        verify(getBookService).find(anyLong());
    }

    @Test
    @DisplayName("Pesquisa lista de livros")
    void whenValidListBook_thenReturnsBook() throws Exception {

        when(listBookService.findAll()).thenReturn(Lists.newArrayList(
                createBook().id(1L).build(), createBook().id(2L).build()
        ));

        mockMvc.perform(get("/v1/api/book")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].author", is("teste author")))
                .andExpect(jsonPath("$[0].resume", is("teste resume")))
                .andExpect(jsonPath("$[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[0].title", is("teste title")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].author", is("teste author")))
                .andExpect(jsonPath("$[1].resume", is("teste resume")))
                .andExpect(jsonPath("$[1].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[1].title", is("teste title")));

        verify(listBookService).findAll();
    }

    @Test
    @DisplayName("Pesquisa livro com paginação")
    void whenValidListPageBook_thenReturnsBookPage() throws Exception {

        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(createBook().id(1L).build()));

        Pageable pageable = PageRequest.of(0, 2);

        when(listPageBookService.findPage(pageable)).thenReturn(bookPage);

        mockMvc.perform(get("/v1/api/book/page?page=0&size=2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].author", is("teste author")))
                .andExpect(jsonPath("$.content[0].resume", is("teste resume")))
                .andExpect(jsonPath("$.content[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$.content[0].title", is("teste title")));

        verify(listPageBookService).findPage(pageable);
    }

    @Test
    @DisplayName("Salva um livro")
    void whenValidSaveBook_thenReturns201() throws Exception {
        mockMvc.perform(post("/v1/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("bookDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        //verify(saveBookService).insert(createBook().build());
    }

    @Test
    @DisplayName("Edita um livro")
    void whenValidUpdateBook_thenReturns204() throws Exception {
        mockMvc.perform(put("/v1/api/book/{id}", 1L)
                .content(readJson("bookUpdate.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        //verify(updateBookService).update(Book.to(BookDTO.builder().build()),1L);
    }

    @Test
    @DisplayName("Deleta livro")
    void whenValidDelete_thenReturns204() throws Exception { // deleta livro
        mockMvc.perform(delete("/v1/api/book/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deleteBookService).delete(1L);
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/java/resources/json/" + file).toAbsolutePath());
        return new String(bytes);
    }
}
