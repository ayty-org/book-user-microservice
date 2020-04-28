package  br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.ListUserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListUserAppServiceImpl implements ListUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public List<UserApp> findAll() {
        return userAppRepository.findAll();
    }
}
