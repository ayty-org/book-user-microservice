package br.com.biblioteca.bookuser.book.v1;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookDTO;
import br.com.biblioteca.bookuser.book.services.DeleteBookService;
import br.com.biblioteca.bookuser.book.services.GetBookService;
import br.com.biblioteca.bookuser.book.services.GetSpecificIdBookService;
import br.com.biblioteca.bookuser.book.services.ListBookService;
import br.com.biblioteca.bookuser.book.services.ListPageBookService;
import br.com.biblioteca.bookuser.book.services.SaveBookService;
import br.com.biblioteca.bookuser.book.services.UpdateBookService;
import br.com.biblioteca.bookuser.user.UserAppDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api/book")
public class BookControllerV1 {

    private final GetBookService getBookService;
    private final ListBookService listBookService;
    private final ListPageBookService listPageBookService;
    private final SaveBookService saveBookService;
    private final UpdateBookService updateBookService;
    private final DeleteBookService deleteBookService;

    @GetMapping(value = "/{id}") //lista livros por id
    public BookDTO find(@PathVariable("id") Long id) {
        return BookDTO.from(getBookService.find(id));
    }

    @GetMapping //lista todos os livros
    public List<BookDTO> findAll() {
        return BookDTO.fromAll(listBookService.findAll());
    }

    @GetMapping(params = {"page", "size"}) //lista todas os livros com paginação
    public Page<BookDTO> findPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return BookDTO.fromPage(listPageBookService.findPage(page, size));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping //adiciona um novo Book
    public void insert(@Valid @RequestBody BookDTO bookDTO) {
        saveBookService.insert(Book.to(bookDTO));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //atualizar uma Book
    public void update(@Valid @RequestBody BookDTO bookDTO, @PathVariable Long id) {
        updateBookService.update(Book.to(bookDTO), id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //Deleta Book
    public void delete(@PathVariable Long id) {
        deleteBookService.delete(id);
    }
}