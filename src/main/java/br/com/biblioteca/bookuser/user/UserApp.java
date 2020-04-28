package br.com.biblioteca.bookuser.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(builderClassName = "Builder")
public class UserApp implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String specificID;

    private String name;

    private int age;

    private String fone;

    private String loanSpecificID;

    public UserApp(String name, int age, String fone) {
        this.name = name;
        this.age = age;
        this.fone = fone;
    }

    public static UserApp to(UserAppDTO userAppDTO) {
        return UserApp
                .builder()
                .id(userAppDTO.getId())
                .name(userAppDTO.getName())
                .age(userAppDTO.getAge())
                .fone(userAppDTO.getFone())
                .loanSpecificID(userAppDTO.getLoanSpecificID())
                .build();
    }

}
