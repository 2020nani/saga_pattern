package br.com.validateddocumentservice.application.dto

import br.com.validateddocumentservice.core.enums.ESagaStatus
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class History (
    private val source: String,
    private val status: ESagaStatus,
    private val message: String,
    private val createdAt: LocalDateTime?
){
}