package com.uol.backend.Services;

import com.uol.backend.Services.exceptions.DataIntegratyViolationException;
import com.uol.backend.Services.exceptions.ObjectNotFoundException;
import com.uol.backend.domain.User;
import com.uol.backend.domain.dto.UserDTO;
import com.uol.backend.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper mapper;


    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User create(UserDTO obj) {
        validateData(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    public User update(UserDTO obj) {
        validateData(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validateData(UserDTO obj) {
        Optional<User> userByEmail = repository.findByEmail(obj.getEmail());
        if(userByEmail.isPresent() && !userByEmail.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }

        Optional<User> userByCpf = repository.findByCpf(obj.getCpf());
        if(userByCpf.isPresent() && !userByCpf.get().getId().equals(obj.getId())) {
            throw new DataIntegratyViolationException("CPF já cadastrado no sistema");
        }
    }
}
