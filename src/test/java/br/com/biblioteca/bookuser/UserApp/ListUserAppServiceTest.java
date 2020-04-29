package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.ListUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar todos os UserApp")
public class ListUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    private ListUserAppServiceImpl findAllUserApp;

    @BeforeEach
    public void setUp() {
        this.findAllUserApp = new ListUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os usuários")
    void shouldFindAllUserApp() {

        when(userAppRepository.findAll()).thenReturn(
                Stream.of(createUserApp().name("Nome Teste GET 01").build(),
                        createUserApp().name("Nome Teste GET 02").build(),
                        createUserApp().name("Nome Teste GET 03").build()).collect(Collectors.toList())
        );

        List<UserApp> result = this.findAllUserApp.findAll();

        //verificação
        assertAll("UserApp",
                () -> assertThat(result.size(), is(3)),
                () -> assertThat(result.get(0).getName(), is("Nome Teste GET 01")),
                () -> assertThat(result.get(1).getName(), is("Nome Teste GET 02")),
                () -> assertThat(result.get(2).getName(), is("Nome Teste GET 03"))
        );
    }
}
