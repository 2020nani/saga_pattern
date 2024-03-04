package br.com.validateddocumentservice.application.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class DocumentGraphql {
    private val document: String? = null

    fun getDocument(): String? {return this.document}
}