package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListUserAppSpecificIdServiceImpl implements ListUserAppSpecificIdService {

    private final UserAppRepository userAppRepository;

    @Override
    public List<UserApp> findAllSpecificId(String id) {
        return userAppRepository.findAllSpecificID(id);
    }
}
