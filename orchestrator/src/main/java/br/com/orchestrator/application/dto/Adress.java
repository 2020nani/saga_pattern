package br.com.orchestrator.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adress {
    private Integer id;
    private String code;
    private String street;
    private String number;
    private String district;
}

