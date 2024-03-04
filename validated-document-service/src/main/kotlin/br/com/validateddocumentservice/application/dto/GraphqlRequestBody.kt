package br.com.validateddocumentservice.application.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming


@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
class GraphqlRequestBody {
    private var query: String? = null
    private var variables: Any? = null

    fun setQuery (value: String) {         // setter
        this.query = value
    }

    fun setVariables(value: Any) {         // setter
        this.variables = value
    }

    override fun toString(): String {
        return "GraphqlRequestBody(query=$query, variables=$variables)"
    }

}