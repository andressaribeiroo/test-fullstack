package com.uol.backend.domain.dto;

import com.uol.backend.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private Status status;

}
