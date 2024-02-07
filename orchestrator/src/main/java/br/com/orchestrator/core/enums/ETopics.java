package br.com.orchestrator.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETopics {

    START_SAGA("start-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAIL("finish-fail"),
    ADRESS_VALIDATION_SUCCESS("adress-validation-success"),
    ADRESS_VALIDATION_FAIL("adress-validation-fail"),
    VALIDATED_SUCCESS("validated-success"),
    VALIDATED_FAIL("validated-fail"),
    REGISTRATION_SUCCESS("registration-success"),
    REGISTRATION_FAIL("registration-fail"),
    NOTIFY_ENDING("notify-ending");

    private final String topic;
}
