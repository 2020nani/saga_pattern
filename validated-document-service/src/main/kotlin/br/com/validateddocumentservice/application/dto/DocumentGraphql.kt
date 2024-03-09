package br.com.validateddocumentservice.application.dto

class DocumentGraphql {
    private val document: String? = null

    fun getDocument(): String? {return this.document}
    override fun toString(): String {
        return "DocumentGraphql(document=$document)"
    }
}