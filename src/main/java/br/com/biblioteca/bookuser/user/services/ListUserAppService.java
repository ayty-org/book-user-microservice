package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;

import java.util.List;

@FunctionalInterface
public interface ListUserAppService {

    List<UserApp> findAll();

}
