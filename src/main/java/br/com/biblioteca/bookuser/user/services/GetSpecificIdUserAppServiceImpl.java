package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSpecificIdUserAppServiceImpl implements GetSpecificIdUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public UserApp findBySpecificID(String specificId) {
        return userAppRepository.findBySpecificID(specificId).orElseThrow(UserAppNotFoundException::new);
    }
}
