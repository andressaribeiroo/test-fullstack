package com.uol.backend.services;

import com.uol.backend.services.exceptions.DataIntegratyViolationException;
import com.uol.backend.services.exceptions.ObjectNotFoundException;
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

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private static final Integer ID      = 1;
    private static final Integer INDEX   = 0;
    private static final String NAME     = "teste";
    private static final String EMAIL    = "teste@mail.com";
    private static final String CPF = "123.456.789-00";
    private static final String PHONE = "(00)00000-00123";
    private static final Status STATUS = Status.ATIVO;
    private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    private static final String EMAIL_JA_CADASTRADO = "E-mail já cadastrado no sistema";
    private static final String CPF_JA_CADASTRADO = "CPF já cadastrado no sistema";


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

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(PHONE, response.get(INDEX).getPhone());
        assertEquals(STATUS, response.get(INDEX).getStatus());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDTO);

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
    void whenCreateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_JA_CADASTRADO, ex.getMessage());
        }
    }

    @Test
    void whenCreateThenCPFReturnAnDataIntegrityViolationException() {
        when(repository.findByCpf(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(3);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(CPF_JA_CADASTRADO, ex.getMessage());
        }
    }
    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDTO);

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
    void whenUpdateThenReturnAnDataIntegrityViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(EMAIL_JA_CADASTRADO, ex.getMessage());
        }
    }

    @Test
    void whenUpdateCPFThenReturnAnDataIntegrityViolationException() {
        when(repository.findByCpf(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(3);
            service.create(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(CPF_JA_CADASTRADO, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void whenDeleteThenReturnObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            service.delete(ID);
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