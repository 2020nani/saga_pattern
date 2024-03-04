package br.com.validateddocumentservice.core.utils

import java.io.IOException


object GraphqlSchemaReaderUtil {
    @Throws(IOException::class)
    fun getSchemaFromFileName(filename: String): String {
        return String(
            GraphqlSchemaReaderUtil::class.java.classLoader.getResourceAsStream("graphql/$filename.graphql")!!
                .readAllBytes()
        )
    }
}
