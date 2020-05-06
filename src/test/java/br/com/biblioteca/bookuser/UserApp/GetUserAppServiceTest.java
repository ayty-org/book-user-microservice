package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.GetUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar um UserApp")
public class GetUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    private GetUserAppServiceImpl findUserApp;

    @BeforeEach
    public void setUp() {
        this.findUserApp = new GetUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve retornar um usuário")
    void shouldFindByIdUserApp() { // testando buscar livro por id

        when(userAppRepository.findById(anyLong())).thenReturn(
                Optional.of(createUserApp().name("Nome Teste GET").build())
        );

        UserApp result = this.findUserApp.find(1L);

        //verificação
        assertAll("UserApp",
                () -> assertThat(result.getName(), is("Nome Teste GET")),
                () -> assertThat(result.getAge(), is(20)),
                () -> assertThat(result.getFone(), is("teste fone")),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertThat(result.getLoanSpecificID(), is("null"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o UserApp não for encontrado")
    void shouldThrowUserAppNotFoundException() {
        when(userAppRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserAppNotFoundException.class, () -> this.findUserApp.find(1L));
    }

}
