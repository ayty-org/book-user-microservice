package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder;
import br.com.biblioteca.bookuser.exceptions.BookIntegrityException;
import br.com.biblioteca.bookuser.exceptions.UserAppIntegrityException;
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

import static br.com.biblioteca.bookuser.book.builders.BookBuilder.createUserApp;
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

    private DeleteUserAppServiceImpl deleteUserApp;

    @BeforeEach
    public void setUp() {
        this.deleteUserApp = new DeleteUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve deletar um usuário")
    void shouldUserAppDeleted() {
        when(userAppRepository.existsById(anyLong())).thenReturn(true);
        deleteUserApp.delete(1L);
        verify(userAppRepository).existsById(anyLong());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não puder ser excluido")
    void shouldThrowUserAppNotDeletedException() {
        lenient().when(userAppRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserAppNotDeletedException.class, () -> this.deleteUserApp.delete(1L));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário tiver emprestimos")
    void shouldThrowUserAppIntegrityException() {
        when(userAppRepository.existsById(anyLong())).thenReturn(true);
        when(userAppRepository.findById(anyLong())).thenReturn(
                Optional.of(UserAppBuilder.createUserApp().loanSpecificID("002").build())
        );
        assertThrows(UserAppIntegrityException.class, () -> this.deleteUserApp.delete(2L));
    }
}
