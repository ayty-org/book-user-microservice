package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.exceptions.UserAppNotDeletedException;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.DeleteUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por deletar um UserApp")
public class DeleteUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    @Mock
    private DeleteUserAppServiceImpl deleteUserApp;

    @BeforeEach
    public void setUp() {
        this.deleteUserApp = new DeleteUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void shouldUserAppDeleted() {
        when(userAppRepository.existsById(1L)).thenReturn(true);
        deleteUserApp.delete(1L);
        verify(userAppRepository).existsById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não puder ser excluido")
    void shouldThrowUserAppNotDeletedException() {
        lenient().when(userAppRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserAppNotDeletedException.class, () -> this.deleteUserApp.delete(1L));
    }
}
