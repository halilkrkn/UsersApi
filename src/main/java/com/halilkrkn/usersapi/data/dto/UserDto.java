package com.halilkrkn.usersapi.data.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    private Integer id;
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    private String name;
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    private String surname;
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    private String email;
    @NotNull(message = "Id cannot be null")
    @NotBlank(message = "Id cannot be blank")
    private String password;
}
