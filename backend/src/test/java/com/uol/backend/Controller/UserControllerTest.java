package com.uol.backend.Controller;

import com.uol.backend.Services.UserService;
import com.uol.backend.domain.User;
import com.uol.backend.domain.dto.UserDTO;
import com.uol.backend.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class UserControllerTest {


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

    private User user = new User();
    private UserDTO userDTO = new UserDTO();

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = controller.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals(PHONE, response.getBody().getPhone());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(CPF, response.getBody().get(INDEX).getCpf());
        assertEquals(PHONE, response.getBody().get(INDEX).getPhone());
        assertEquals(STATUS, response.getBody().get(INDEX).getStatus());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, CPF, PHONE, STATUS);
        userDTO = new UserDTO(ID, NAME, EMAIL, CPF, PHONE, STATUS);
    }
}