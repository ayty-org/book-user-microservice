package br.com.biblioteca.bookuser.UserApp;

import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import br.com.biblioteca.bookuser.user.services.ListPageUserAppServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static br.com.biblioteca.bookuser.UserApp.builders.UserAppBuilder.createUserApp;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("service")
@DisplayName("Valida funcionalidade do serviço responsável por pesquisar UserApp por paginação")
public class ListPageUserAppServiceTest {

    @Mock
    private UserAppRepository userAppRepository;

    private ListPageUserAppServiceImpl listPageUserApp;

    @BeforeEach
    public void setUp() {
        this.listPageUserApp = new ListPageUserAppServiceImpl(userAppRepository);
    }

    @Test
    @DisplayName("Deve retornar todos os livros com paginação")
    void shouldFindAllBook() {

        Pageable pageable = PageRequest.of(0, 2);

        when(listPageUserApp.findPage(pageable)).thenReturn(new PageImpl<>(Collections.nCopies(2, createUserApp().build())));

        Page<UserApp> userAppPage = listPageUserApp.findPage(pageable);

        //verificação
        assertAll("userApp",
                () -> assertThat(userAppPage.getNumber(), is(0)),
                () -> assertThat(userAppPage.getSize(), is(2)),
                () -> assertThat(userAppPage.getContent().get(0).getName(), is("teste nome")),
                () -> assertThat(userAppPage.getContent().get(0).getAge(), is(20)),
                () -> assertThat(userAppPage.getContent().get(0).getFone(), is("teste fone")),
                () -> assertThat(userAppPage.getContent().get(0).getSpecificID(), is("001")),
                () -> assertThat(userAppPage.getContent().get(0).getLoanSpecificID(), is("null")),
                () -> assertThat(userAppPage.getContent().get(1).getName(), is("teste nome")),
                () -> assertThat(userAppPage.getContent().get(1).getAge(), is(20)),
                () -> assertThat(userAppPage.getContent().get(1).getFone(), is("teste fone")),
                () -> assertThat(userAppPage.getContent().get(1).getSpecificID(), is("001")),
                () -> assertThat(userAppPage.getContent().get(1).getLoanSpecificID(), is("null"))
        );
    }

}
