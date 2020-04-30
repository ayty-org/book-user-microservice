package br.com.biblioteca.bookuser.user.services;

import br.com.biblioteca.bookuser.exceptions.UserAppNotFoundException;
import br.com.biblioteca.bookuser.user.UserApp;
import br.com.biblioteca.bookuser.user.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UpdateUserAppSpecificIdLoanServiceImpl implements UpdateUserAppSpecificIdLoanService {

    private final UserAppRepository userAppRepository;

    @Override
    public void update(String loanUserAppSpecificIdDTO, String id) {
        UserApp user = userAppRepository.findBySpecificID(id).orElseThrow(UserAppNotFoundException::new);
        user.setLoanSpecificID(loanUserAppSpecificIdDTO);
        userAppRepository.save(user);
    }
}
