package br.com.validateddocumentservice.application.dto

class DocumentDto {
    private val data: DocumentData? = null

    fun getData(): DocumentData? {return this.data}
    override fun toString(): String {
        return "DocumentDto(data=$data)"
    }
}