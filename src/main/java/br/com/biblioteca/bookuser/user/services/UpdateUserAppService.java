package  br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.user.UserApp;

@FunctionalInterface
public interface UpdateUserAppService {

    void update(UserApp userApp, Long id);
}
