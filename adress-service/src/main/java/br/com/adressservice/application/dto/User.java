package br.com.adressservice.application.dto;

import br.com.adressservice.core.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;
    private String transactionId;
    private String name;
    private String document;
    private String cep;
    private Adress adress;
    private LocalDateTime createdAt;
}
