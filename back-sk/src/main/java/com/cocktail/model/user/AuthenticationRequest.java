package com.cocktail.model.user;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {

    String email;
    String password;
    String name;

}
