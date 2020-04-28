package br.com.biblioteca.bookuser.UserApp.builders;

import br.com.biblioteca.bookuser.user.UserApp;

public class UserAppBuilder {

    public static UserApp.Builder createUserApp() {
        return UserApp
                .builder()
                .name("teste nome")
                .age(20)
                .fone("teste fone");
    }
}
