package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveUserAppServiceImpl implements SaveUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public void insert(UserApp userApp) {
        userAppRepository.save(userApp);
        userApp.setSpecificID(gerarSpecificId(userApp.getId()));
        userAppRepository.save(userApp);
    }

    public static String gerarSpecificId(Long id) {
        return "00" + id;
    }

}
