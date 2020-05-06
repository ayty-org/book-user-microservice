package br.com.biblioteca.bookuser.book;

import br.com.biblioteca.bookuser.book.services.DeleteBookService;
import br.com.biblioteca.bookuser.book.services.GetBookService;
import br.com.biblioteca.bookuser.book.services.GetSpecificIdBookService;
import br.com.biblioteca.bookuser.book.services.ListBookService;
import br.com.biblioteca.bookuser.book.services.ListBookSpecificIdService;
import br.com.biblioteca.bookuser.book.services.ListPageBookService;
import br.com.biblioteca.bookuser.book.services.SaveBookService;
import br.com.biblioteca.bookuser.book.services.UpdateBookService;
import br.com.biblioteca.bookuser.book.services.UpdateBookSpecificIdLoanService;
import br.com.biblioteca.bookuser.book.services.UpdateStatusBookService;
import br.com.biblioteca.bookuser.book.v1.BookControllerV1;
import br.com.biblioteca.bookuser.exceptions.BookNotFoundException;
import org.assertj.core.util.Lists;
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
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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
    private UpdateBookSpecificIdLoanService updateBookSpecificIdLoanService;
    @MockBean
    private ListBookSpecificIdService listBookSpecificIdService;
    @MockBean
    private UpdateStatusBookService updateStatusBookService;

    @Test
    @DisplayName("Pesquisa livro por id")
    void whenValidGetIdBook_thenReturnsBook() throws Exception {

        when(getBookService.find(anyLong())).thenReturn(createBook().build());

        mockMvc.perform(get("/v1/api/book/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("teste author")))
                .andExpect(jsonPath("$.resume", is("teste resume")))
                .andExpect(jsonPath("$.isbn", is("teste isbn")))
                .andExpect(jsonPath("$.title", is("teste title")))
                .andExpect(jsonPath("$.yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.specificID", is("001")))
                .andExpect(jsonPath("$.loanSpecificID", is("null")));

        verify(getBookService).find(anyLong());
    }

    @Test
    @DisplayName("lança exeção quando livro não for achado")
    void whenValidExeceptionGetIdBookBook_thenThrow400() throws Exception {
        when(getBookService.find(anyLong())).thenThrow(new BookNotFoundException());

        mockMvc.perform(get("/v1/api/book/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getBookService).find(anyLong());
    }

    @Test
    @DisplayName("Pesquisa todos os livros")
    void whenValidListBook_thenReturnsBook() throws Exception {

        when(listBookService.findAll()).thenReturn(Lists.newArrayList(
                createBook().id(1L).build(), createBook().id(2L).specificID("002").build()
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
                .andExpect(jsonPath("$[0].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$[0].status", is(true)))
                .andExpect(jsonPath("$[0].specificID", is("001")))
                .andExpect(jsonPath("$[0].loanSpecificID", is("null")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].author", is("teste author")))
                .andExpect(jsonPath("$[1].resume", is("teste resume")))
                .andExpect(jsonPath("$[1].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[1].title", is("teste title")))
                .andExpect(jsonPath("$[1].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$[1].status", is(true)))
                .andExpect(jsonPath("$[1].specificID", is("002")))
                .andExpect(jsonPath("$[1].loanSpecificID", is("null")));

        verify(listBookService).findAll();
    }

    @Test
    @DisplayName("Pesquisa todos os livros com paginação")
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
                .andExpect(jsonPath("$.content[0].title", is("teste title")))
                .andExpect(jsonPath("$.content[0].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$.content[0].status", is(true)))
                .andExpect(jsonPath("$.content[0].specificID", is("001")))
                .andExpect(jsonPath("$.content[0].loanSpecificID", is("null")));

        verify(listPageBookService).findPage(pageable);
    }

    @Test
    @DisplayName("Pesquisa livro por seu specific id")
    void whenValidGetSpecificIdBook_thenReturnsBook() throws Exception {

        when(getSpecificIdBookService.findBySpecificID(anyString())).thenReturn(
                createBook().specificID("001").loanSpecificID("001").build());

        mockMvc.perform(get("/v1/api/book/getBookSpecificId/{id}", "001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.author", is("teste author")))
                .andExpect(jsonPath("$.resume", is("teste resume")))
                .andExpect(jsonPath("$.isbn", is("teste isbn")))
                .andExpect(jsonPath("$.title", is("teste title")))
                .andExpect(jsonPath("$.yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.specificID", is("001")))
                .andExpect(jsonPath("$.loanSpecificID", is("001")));

        verify(getSpecificIdBookService).findBySpecificID(anyString());
    }

    @Test
    @DisplayName("lança exeção quando livro não for achado, pesquisando por seu specific id")
    void whenValidExceptionGetSpecificIdBook_thenThrow400() throws Exception {

        when(getSpecificIdBookService.findBySpecificID(anyString())).thenThrow(new BookNotFoundException());

        mockMvc.perform(get("/v1/api/book/getBookSpecificId/{id}", "001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(getSpecificIdBookService).findBySpecificID(anyString());
    }


        @Test
    @DisplayName("Pesquisa todos os livros por seu specific id")
    void whenValidListSpecificIdBook_thenReturnsBook() throws Exception {

        when(listBookSpecificIdService.findAllSpecificId(anyString())).thenReturn(Lists.newArrayList(
                createBook().id(1L).build(),
                createBook().id(2L).build()
        ));

        mockMvc.perform(get("/v1/api/book/getAllLoanSpecificId/{id}","001")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].author", is("teste author")))
                .andExpect(jsonPath("$[0].resume", is("teste resume")))
                .andExpect(jsonPath("$[0].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[0].title", is("teste title")))
                .andExpect(jsonPath("$[0].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$[0].status", is(true)))
                .andExpect(jsonPath("$[0].specificID", is("001")))
                .andExpect(jsonPath("$[0].loanSpecificID", is("null")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].author", is("teste author")))
                .andExpect(jsonPath("$[1].resume", is("teste resume")))
                .andExpect(jsonPath("$[1].isbn", is("teste isbn")))
                .andExpect(jsonPath("$[1].title", is("teste title")))
                .andExpect(jsonPath("$[1].yearBook", is("1964-07-12")))
                .andExpect(jsonPath("$[1].status", is(true)))
                .andExpect(jsonPath("$[1].specificID", is("001")))
                .andExpect(jsonPath("$[1].loanSpecificID", is("null")));

        verify(listBookSpecificIdService).findAllSpecificId(anyString());
    }

    @Test
    @DisplayName("Salva um livro")
    void whenValidSaveBook_thenReturns201() throws Exception {
        mockMvc.perform(post("/v1/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("bookDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(saveBookService).insert(any(Book.class));
    }

    @Test
    @DisplayName("Edita um livro")
    void whenValidUpdateBook_thenReturns204() throws Exception {
        mockMvc.perform(put("/v1/api/book/{id}", 1L)
                .content(readJson("bookUpdate.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateBookService).update(any(Book.class), eq(1L));
    }

    @Test
    @DisplayName("Edita o status do livro por seu specific id")
    void whenValidUpdateStatusBook_thenReturns204() throws Exception {
        mockMvc.perform(put("/v1/api/book/updateStatusBook/{id}", "001")
                .content(readJson("updateStatusBook.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateStatusBookService).updateStatusBook(anyBoolean(), eq("001"));
    }

    @Test
    @DisplayName("Edita o loanSpecificID do loan em livro")
    void whenValidUpdateLoanSpecificIdBook_thenReturns204() throws Exception {
        mockMvc.perform(put("/v1/api/book/updateLoanSpecificId/{id}", "001")
                .content(readJson("updateLoanSpecificIdBook.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(updateBookSpecificIdLoanService).updateSpecificId(anyString(), eq("001"));
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
