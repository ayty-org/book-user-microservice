package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateUserAppServiceImpl implements UpdateUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public void update(UserApp userApp, Long id) {
        UserApp user = userAppRepository.findById(id).orElseThrow(UserAppNotFoundException::new);

        user.setName(userApp.getName());
        user.setAge(userApp.getAge());
        user.setFone(userApp.getFone());
        userAppRepository.save(user);
    }
}
