package br.com.validateddocumentservice.application.integration.graphql.document

import br.com.validateddocumentservice.application.dto.DocumentDto
import br.com.validateddocumentservice.application.dto.GraphqlRequestBody
import br.com.validateddocumentservice.core.utils.GraphqlSchemaReaderUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.io.IOException

@Component
class DocumentClient(
    @Value("\${graphql.uri}")
    private val url: String
) {

    @Throws(IOException::class)
    fun getDocument(documentNumber: String?): DocumentDto? {

        val webClient = WebClient.builder().build()
        val graphQLRequestBody = GraphqlRequestBody()
        val query: String = GraphqlSchemaReaderUtil.getSchemaFromFileName("getDocuments")
        val variables: String = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables")
        graphQLRequestBody.setQuery(query)
        graphQLRequestBody.setVariables(variables.replace("documentNumber", documentNumber!!))
        try {
            val teste = webClient.post()
                .uri(url)
                .bodyValue(graphQLRequestBody)
                .retrieve()
                .bodyToMono(DocumentDto::class.java)
                .block()
            return teste;
        } catch (e: Exception){

            println(e.localizedMessage)
            println(e.message)
            println(e.cause)
        }
        return null;
    }
}