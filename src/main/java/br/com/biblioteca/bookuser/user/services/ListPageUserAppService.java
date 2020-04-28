package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;
import org.springframework.data.domain.Page;

@FunctionalInterface
public interface ListPageUserAppService {

    Page<UserApp> findPage(Integer page, Integer size);
}
