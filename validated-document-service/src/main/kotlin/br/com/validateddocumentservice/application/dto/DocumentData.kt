package br.com.validateddocumentservice.application.dto

class DocumentData {
    private val doc: DocumentGraphql? = null
    fun getDoc(): DocumentGraphql? {return this.doc}
    override fun toString(): String {
        return "DocumentData(doc=$doc)"
    }
}