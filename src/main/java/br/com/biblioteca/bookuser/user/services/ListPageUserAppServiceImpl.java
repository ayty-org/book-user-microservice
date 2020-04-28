package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ListPageUserAppServiceImpl implements ListPageUserAppService {

    private final UserAppRepository userAppRepository;

    @Override
    public Page<UserApp> findPage(Pageable pageable) {
        return userAppRepository.findAll(pageable);
    }
}
