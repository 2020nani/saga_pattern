package br.com.validateddocumentservice.application.dto


class GraphqlRequestBody {
    private var query: String? = null
    private var variables: Any? = null

    fun setQuery (value: String) {         // setter
        this.query = value
    }

    fun setVariables(value: Any) {         // setter
        this.variables = value
    }

    fun getQuery(): String? { return this.query}
    fun getVariables(): Any? {return this.variables}

    override fun toString(): String {
        return "GraphqlRequestBody(query=$query, variables=$variables)"
    }

}