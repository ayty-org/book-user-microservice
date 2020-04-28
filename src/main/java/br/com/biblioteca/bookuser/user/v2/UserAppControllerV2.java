package br.com.biblioteca.bookuser.user.v2;

import br.com.biblioteca.bookuser.user.LoanUserAppSpecificIdDTO;
import br.com.biblioteca.bookuser.user.UserAppDTO;
import br.com.biblioteca.bookuser.user.services.GetSpecificIdUserAppService;
import br.com.biblioteca.bookuser.user.services.ListUserAppSpecificIdService;
import br.com.biblioteca.bookuser.user.services.UpdateUserAppSpecificIdLoan;
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
@RequestMapping(value = "/v2/api/user")
public class UserAppControllerV2 {

    private final GetSpecificIdUserAppService getSpecificIdUserAppService;
    private final UpdateUserAppSpecificIdLoan updateUserAppSpecificIdLoan;
    private final ListUserAppSpecificIdService listUserAppSpecificIdService;


    @GetMapping(value = "/{id}")
    public UserAppDTO find(@PathVariable String id) {
        return UserAppDTO.from(getSpecificIdUserAppService.findBySpecificID(id));
    }

    @GetMapping(value = "/loanSpecific/{id}")
    public List<UserAppDTO> findAll(@PathVariable String id) {
        return UserAppDTO.fromAll(listUserAppSpecificIdService.findAllSpecificId(id));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //atualiza variavel loan em usuário
    public void update(@Valid @PathVariable String id, @RequestBody LoanUserAppSpecificIdDTO loanUserAppSpecificIdDTO) {
        updateUserAppSpecificIdLoan.update(loanUserAppSpecificIdDTO, id);
    }

}
