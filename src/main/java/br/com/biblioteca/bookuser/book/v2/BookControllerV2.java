package br.com.biblioteca.bookuser.book.v2;

import br.com.biblioteca.bookuser.book.BookDTO;
import br.com.biblioteca.bookuser.book.LoanBookSpecificIdDTO;
import br.com.biblioteca.bookuser.book.services.GetSpecificIdBookService;
import br.com.biblioteca.bookuser.book.services.ListBookSpecificIdService;
import br.com.biblioteca.bookuser.book.services.UpdateBookSpecificIdLoan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v2/api/book")
public class BookControllerV2 {

    private final GetSpecificIdBookService getSpecificIdBookService;
    private final UpdateBookSpecificIdLoan updateBookSpecificIdLoan;
    private final ListBookSpecificIdService listBookSpecificIdService;

    @GetMapping(value = "/{id}") //lista usuário por id
    public BookDTO find(@PathVariable String id) {
        return BookDTO.from(getSpecificIdBookService.findBySpecificID(id));
    }

    @GetMapping(value = "/loanSpecific/{id}")
    public List<BookDTO> findAll(@PathVariable String id) {
        return BookDTO.fromAll(listBookSpecificIdService.findAllSpecificId(id));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //atualiza usuário
    public void update(@Valid @RequestBody LoanBookSpecificIdDTO loanBookSpecificIdDTO, @PathVariable String id) {
        updateBookSpecificIdLoan.updateSpecificId(loanBookSpecificIdDTO, id);
    }
}
