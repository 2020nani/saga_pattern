package br.com.adressservice.application.dto;

import br.com.adressservice.core.model.Adress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdressGraphql {
    private String id;
    private String cep;
    private String street;
    private String number;
    private String bairro;

    public Adress mapperAdress() {
        return Adress.builder()
                .code(this.cep)
                .district(this.bairro)
                .number(this.number)
                .street(this.street)
                .build();
    }
}
