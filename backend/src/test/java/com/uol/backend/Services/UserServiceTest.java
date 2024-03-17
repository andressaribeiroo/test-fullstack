package com.uol.backend.Services;

import com.uol.backend.Services.exceptions.ObjectNotFoundException;
import com.uol.backend.domain.User;
import com.uol.backend.domain.dto.UserDTO;
import com.uol.backend.domain.enums.Status;
import com.uol.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    private static final Integer ID      = 1;
    private static final String NAME     = "teste";
    private static final String EMAIL    = "teste@mail.com";
    private static final String CPF = "123.456.789-00";
    private static final String PHONE = "(00)00000-00123";
    private static final Status STATUS = Status.ATIVO;
    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";


    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> optionalUser;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);

        assertNotNull(response);

        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(CPF, response.getCpf());
        assertEquals(PHONE, response.getPhone());
        assertEquals(STATUS, response.getStatus());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {

        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }
    private void startUser() {
        user = new User(ID, NAME, EMAIL, CPF, PHONE, STATUS);
        userDTO = new UserDTO(ID, NAME, EMAIL, CPF, PHONE, STATUS);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, CPF, PHONE, STATUS));
    }
}