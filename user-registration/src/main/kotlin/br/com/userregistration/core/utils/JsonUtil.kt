package br.com.userregistration.core.utils

import br.com.userregistration.application.dto.Event
import com.fasterxml.jackson.databind.ObjectMapper
import lombok.AllArgsConstructor
import org.springframework.stereotype.Component


@Component
@AllArgsConstructor
class JsonUtil (
    val objectMapper: ObjectMapper
){

    fun toJson(`object`: Any?): String {
        return try {
            objectMapper.writeValueAsString(`object`)
        } catch (ex: Exception) {
            ""
        }
    }

    fun toEvent(json: String?): Event? {
        return try {
            objectMapper.readValue(json, Event::class.java)
        } catch (ex: Exception) {
            null
        }
    }
}
