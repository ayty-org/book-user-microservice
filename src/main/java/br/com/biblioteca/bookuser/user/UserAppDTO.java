package br.com.biblioteca.bookuser.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderClassName = "Builder")
public class UserAppDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private int age;

    @NotEmpty
    private String fone;

    private String specificID;

    private String loanSpecificID;

    public static UserAppDTO from(UserApp userApp) {
        return UserAppDTO
                .builder()
                .id(userApp.getId())
                .name(userApp.getName())
                .age(userApp.getAge())
                .fone(userApp.getFone())
                .specificID(userApp.getSpecificID())
                .loanSpecificID(userApp.getLoanSpecificID())
                .build();
    }

    public static List<UserAppDTO> fromAll(List<br.com.biblioteca.bookuser.user.UserApp> userApps) {
        return userApps.stream().map(UserAppDTO::from).collect(Collectors.toList());
    }

    public static Page<UserAppDTO> fromPage(Page<br.com.biblioteca.bookuser.user.UserApp> pages) {
        return pages.map(UserAppDTO::from);
    }

}
