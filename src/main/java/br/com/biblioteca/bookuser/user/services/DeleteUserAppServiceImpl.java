package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.exceptions.UserAppIntegrityException;
import br.com.biblioteca.bookuser.exceptions.UserAppNotDeletedException;
import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteUserAppServiceImpl implements DeleteUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public void delete(Long id) {
        if (!userAppRepository.existsById(id)) {
            throw new UserAppNotDeletedException();
        }
        UserApp userApp = userAppRepository.findById(id).orElseThrow(UserAppNotFoundException::new);
        if (!userApp.getLoanSpecificID().equals("null")) {
            throw new UserAppIntegrityException();
        }
        userAppRepository.deleteById(id);
    }
}
