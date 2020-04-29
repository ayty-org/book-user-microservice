package br.com.biblioteca.bookuser.book.v1;

import br.com.biblioteca.bookuser.book.Book;
import br.com.biblioteca.bookuser.book.BookDTO;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private final GetSpecificIdBookService getSpecificIdBookService;
    private final UpdateBookSpecificIdLoanService updateBookSpecificIdLoanService;
    private final ListBookSpecificIdService listBookSpecificIdService;
    private final UpdateStatusBookService updateStatusBookService;

    @GetMapping(value = "/{id}") //lista livros por id
    public BookDTO find(@PathVariable Long id) {
        return BookDTO.from(getBookService.find(id));
    }

    @GetMapping //lista todos os livros
    public List<BookDTO> findAll() {
        return BookDTO.fromAll(listBookService.findAll());
    }

    @GetMapping(path = "/page") //lista todas os livros com paginação
    public Page<BookDTO> findPage(Pageable pageable) {
        return BookDTO.fromPage(listPageBookService.findPage(pageable));
    }

    @GetMapping(value = "/getBookSpecificId/{id}") //lista usuário por seu specificId
    public BookDTO find(@PathVariable String id) {
        return BookDTO.from(getSpecificIdBookService.findBySpecificID(id));
    }

    @GetMapping(value = "/getAllLoanSpecificId/{id}")  //retorna lista de livros por seu especificID
    public List<BookDTO> findAll(@PathVariable String id) {
        return BookDTO.fromAll(listBookSpecificIdService.findAllSpecificId(id));
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
    @PutMapping(value = "/updateStatusBook/{id}")
    public void update(@Valid @RequestBody boolean status, @PathVariable String id) {
        updateStatusBookService.updateStatusBook(status, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/updateLoanSpecificId/{id}") //atualiza loan do livro
    public void update(@Valid @RequestBody String loanBookSpecificIdDTO, @PathVariable String id) {
        updateBookSpecificIdLoanService.updateSpecificId(loanBookSpecificIdDTO, id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //Deleta Book
    public void delete(@PathVariable Long id) {
        deleteBookService.delete(id);
    }
}