package com.uol.backend.Config;

import com.uol.backend.domain.User;
import com.uol.backend.domain.enums.Status;
import com.uol.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UserRepository repository;

    @Bean
    public List<User> startDB() {
        User u1 = new User(null, "Test", "teste@mail.com", "123.456.789-08", "(00)91234-5678", Status.ATIVO);
        User u2 = new User(null, "Test2", "test2@mail.com", "123.000.000-00", "(00)90000-0000", Status.INATIVO);

        return repository.saveAll(Arrays.asList(u1, u2));
    }
}
