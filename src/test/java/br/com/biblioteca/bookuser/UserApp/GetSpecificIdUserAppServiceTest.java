package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder;
import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.GetSpecificIdUserAppServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar um usuário por seu especific id")
public class GetSpecificIdUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    private GetSpecificIdUserAppServiceImpl findUserApp;

    @BeforeEach
    public void setUp() {
        this.findUserApp = new GetSpecificIdUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve retornar um usuário pesquisando por seu specific id")
    void shouldFindByIdBook() {

        when(userAppRepository.findBySpecificID(anyString())).thenReturn(
                Optional.of(createUserApp().name("Nome Teste GET").loanSpecificID("001").build())
        );

        UserApp result = this.findUserApp.findBySpecificID("001");

        //verificação
        assertAll("UserApp",
                () -> assertThat(result.getName(), is("Nome Teste GET")),
                () -> assertThat(result.getAge(), is(20)),
                () -> assertThat(result.getFone(), is("teste fone")),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertThat(result.getLoanSpecificID(), is("001"))
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o UserApp não for encontrado")
    void shouldThrowUserAppNotFoundException() {
        when(userAppRepository.findBySpecificID(anyString())).thenReturn(Optional.empty());
        assertThrows(UserAppNotFoundException.class, () -> this.findUserApp.findBySpecificID(anyString()));
    }

}

