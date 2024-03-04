package br.com.adressservice.application.integration.graphql.adress;

import br.com.adressservice.application.dto.AdressDto;
import br.com.adressservice.application.dto.GraphqlRequestBody;
import br.com.adressservice.core.utils.GraphqlSchemaReaderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Component
@Slf4j
public class AdressClient {
    @Value("${graphql.uri}")
    private String url;

    public AdressDto getAdressDetails(final String cepCode) throws IOException {

        WebClient webClient = WebClient.builder().build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

        final String query = GraphqlSchemaReaderUtil.getSchemaFromFileName("getAdress");
        final String variables = GraphqlSchemaReaderUtil.getSchemaFromFileName("variables");

        graphQLRequestBody.setQuery(query);
        graphQLRequestBody.setVariables(variables.replace("cepCode", cepCode));

        return webClient.post()
                .uri(url)
                .bodyValue(graphQLRequestBody)
                .retrieve()
                .bodyToMono(AdressDto.class)
                .block();
    }
}
