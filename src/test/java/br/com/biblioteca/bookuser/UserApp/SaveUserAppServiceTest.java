package br.com.biblioteca.bookuser.UserApp;


import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.SaveUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por salvar um UserApp")
public class SaveUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    private SaveUserAppServiceImpl saveUserApp;

    @BeforeEach
    public void setUp() {
        this.saveUserApp = new SaveUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve salvar um usuário")
    void shouldSaveUserApp() {

        //execução
        saveUserApp.insert(createUserApp().build());

        //preparação
        ArgumentCaptor<UserApp> captorUserApp = ArgumentCaptor.forClass(UserApp.class);
        verify(userAppRepository, times(2)).save(captorUserApp.capture());

        UserApp result = captorUserApp.getValue();

        assertAll("UserApp",
                () -> assertThat(result.getName(), is("teste nome")),
                () -> assertThat(result.getAge(), is(20)),
                () -> assertThat(result.getFone(), is("teste fone"))

        );
    }
}
