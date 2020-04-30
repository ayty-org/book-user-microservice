package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.UpdateUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por atualizar UserApp")
public class UpdateUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    @Mock
    private UpdateUserAppServiceImpl updateUserApp;

    @BeforeEach
    public void setUp() {
        this.updateUserApp = new UpdateUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve atualizar um usuário")
    void shouldUpdateUserApp() {

        when(userAppRepository.findById(anyLong())).thenReturn(Optional.of(createUserApp().id(1L).build()));

        updateUserApp.update(createUserApp().name("teste update").build(), 1L);

        ArgumentCaptor<UserApp> captorUser = ArgumentCaptor.forClass(UserApp.class);
        verify(userAppRepository).save(captorUser.capture());

        UserApp result = captorUser.getValue();

        assertAll("UserApp",
                () -> assertThat(result.getName(), is("teste update")),
                () -> assertThat(result.getAge(), is(20)),
                () -> assertThat(result.getFone(), is("teste fone")),
                () -> assertThat(result.getSpecificID(), is("001")),
                () -> assertNull(result.getLoanSpecificID())
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando o UserApp não for encontrado")
    void shouldThrowUserAppNotFoundException() {
        when(userAppRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserAppNotFoundException.class, () -> this.updateUserApp.update(UserApp.builder().build(), 1L));
    }
}

