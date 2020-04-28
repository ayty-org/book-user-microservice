package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;

@FunctionalInterface
public interface GetSpecificIdUserAppService {

    UserApp findBySpecificID(String specificID);
}
