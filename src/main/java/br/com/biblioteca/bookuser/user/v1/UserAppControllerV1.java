package br.com.biblioteca.bookuser.user.v1;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppDTO;
import br.com.biblioteca.bookuser.user.services.DeleteUserAppService;
import br.com.biblioteca.bookuser.user.services.GetUserAppService;
import br.com.biblioteca.bookuser.user.services.ListPageUserAppService;
import br.com.biblioteca.bookuser.user.services.ListUserAppService;
import br.com.biblioteca.bookuser.user.services.SaveUserAppService;
import br.com.biblioteca.bookuser.user.services.UpdateUserAppService;
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
@RequestMapping(value = "/v1/api/user")
public class UserAppControllerV1 {

    private final GetUserAppService getUserAppService;
    private final ListUserAppService listUserAppService;
    private final ListPageUserAppService listPageUserAppService;
    private final SaveUserAppService saveUserAppService;
    private final UpdateUserAppService updateUserAppService;
    private final DeleteUserAppService deleteUserAppService;

    @GetMapping(value = "/{id}") //lista usuário por id
    public UserAppDTO find(@PathVariable Long id) {
        return UserAppDTO.from(getUserAppService.find(id));
    }

    @GetMapping //lista todos os usuários
    public List<UserAppDTO> findAll() {
        return UserAppDTO.fromAll(listUserAppService.findAll());
    }

    @GetMapping(params = {"page", "size"}) //lista todas os usuários com paginação
    public Page<UserAppDTO> findPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return UserAppDTO.fromPage(listPageUserAppService.findPage(page, size));
    }

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping //adiciona um usuário
    public void insert(@Valid @RequestBody UserAppDTO userAppDTO) {
        saveUserAppService.insert(UserApp.to(userAppDTO));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PutMapping(value = "/{id}") //atualiza usuário
    public void update(@Valid @RequestBody UserAppDTO userAppDTO, @PathVariable Long id) {
        updateUserAppService.update(UserApp.to(userAppDTO), id);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}") //Deleta usuário
    public void delete(@PathVariable Long id) {
        deleteUserAppService.delete(id);
    }
}
