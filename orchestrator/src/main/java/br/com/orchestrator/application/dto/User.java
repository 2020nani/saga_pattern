package br.com.orchestrator.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private String id;
    private String transactionId;
    private String name;
    private String document;
    private String cep;
    private LocalDateTime createdAt;
}
