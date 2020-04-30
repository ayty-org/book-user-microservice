package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.services.DeleteUserAppService;
import br.com.biblioteca.bookuser.user.services.GetSpecificIdUserAppService;
import br.com.biblioteca.bookuser.user.services.GetUserAppService;
import br.com.biblioteca.bookuser.user.services.ListPageUserAppService;
import br.com.biblioteca.bookuser.user.services.ListUserAppService;
import br.com.biblioteca.bookuser.user.services.SaveUserAppService;
import br.com.biblioteca.bookuser.user.services.UpdateUserAppService;
import br.com.biblioteca.bookuser.user.services.UpdateUserAppSpecificIdLoanService;
import br.com.biblioteca.bookuser.user.v1.UserAppControllerV1;
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

import static br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder.createUserApp;
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
@WebMvcTest(controllers = UserAppControllerV1.class)
@DisplayName("Valida funcionalidade do Controller UserApp")
public class UserAppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetUserAppService getUserAppService;
    @MockBean
    private ListUserAppService listUserAppService;
    @MockBean
    private ListPageUserAppService listPageUserAppService;
    @MockBean
    private SaveUserAppService saveUserAppService;
    @MockBean
    private UpdateUserAppService updateUserAppService;
    @MockBean
    private DeleteUserAppService deleteUserAppService;
    @MockBean
    private GetSpecificIdUserAppService getSpecificIdUserAppService;
    @MockBean
    private UpdateUserAppSpecificIdLoanService updateUserAppSpecificIdLoanService;

    @Test
    @DisplayName("Pesquisa usuário por id")
    void whenValidGetIdUserApp_thenReturnsUserApp() throws Exception {

        when(getUserAppService.find(anyLong())).thenReturn(createUserApp().id(1L).build());

        mockMvc.perform(get("/v1/api/user/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("teste nome")))
                .andExpect(jsonPath("$.age", is(20)))
                .andExpect(jsonPath("$.fone", is("teste fone")));

        verify(getUserAppService).find(anyLong());
    }

    @Test
    @DisplayName("Pesquisa lista de usuários")
    void whenValidListUserApp_thenReturnsUserApp() throws Exception {

        when(listUserAppService.findAll()).thenReturn(Lists.newArrayList(
                createUserApp().id(1L).build(), createUserApp().id(2L).build()
        ));

        mockMvc.perform(get("/v1/api/user")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("teste nome")))
                .andExpect(jsonPath("[0].age", is(20)))
                .andExpect(jsonPath("$[0].fone", is("teste fone")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("teste nome")))
                .andExpect(jsonPath("$[1].age", is(20)))
                .andExpect(jsonPath("$[1].fone", is("teste fone")));

        verify(listUserAppService).findAll();
    }


    @Test
    @DisplayName("Pesquisa usuários com paginação")
    void whenValidListPageUserApp_thenReturnsUserAppPage() throws Exception {

        Page<UserApp> userAppPage = new PageImpl<>(Collections.singletonList(createUserApp().id(1L).build()));

        Pageable pageable = PageRequest.of(0, 2);

        when(listPageUserAppService.findPage(pageable)).thenReturn(userAppPage);

        mockMvc.perform(get("/v1/api/user/page/?page=0&size=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].name", is("teste nome")))
                .andExpect(jsonPath("$.content[0].age", is(20)))
                .andExpect(jsonPath("$.content[0].fone", is("teste fone")));

        verify(listPageUserAppService).findPage(pageable);
    }

    @Test
    @DisplayName("Sava um usuário")
    void whenValidSaveUserApp_thenReturns201() throws Exception {
        mockMvc.perform(post("/v1/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(readJson("userAppDTO.json")))
                .andDo(print())
                .andExpect(status().isCreated());

        //verify(saveUserAppService).insert(createUserApp().build());
    }

    @Test
    @DisplayName("Edita um usuário")
    void whenValidUpdateUserApp_thenReturnsUserApp() throws Exception { //edita UserApp
        mockMvc.perform(put("/v1/api/user/{id}", 1L)
                .content(readJson("userUpdate.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        //verify(updateUserAppService).update(createUserApp().build(), 1L);
    }

    @Test
    @DisplayName("Deleta usuário")
    void whenValidDeleteUserApp_thenReturns204() throws Exception {
        mockMvc.perform(delete("/v1/api/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deleteUserAppService).delete(anyLong());
    }

    public static String readJson(String file) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get("src/test/java/resources/json/" + file).toAbsolutePath());
        return new String(bytes);
    }
}
