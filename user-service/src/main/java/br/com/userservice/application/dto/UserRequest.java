package br.com.userservice.application.dto;

import br.com.userservice.application.annotations.ValidatedCep;
import br.com.userservice.application.annotations.ValidatedDocument;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest{
        @NotBlank(message = "the field name cant be empty or null")
        private String name;
        @ValidatedDocument
        private String document;
        @ValidatedCep
        private String cep;
}

;