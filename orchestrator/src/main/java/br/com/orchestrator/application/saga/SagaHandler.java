package br.com.orchestrator.application.saga;


import static br.com.orchestrator.core.enums.EEventSource.*;
import static br.com.orchestrator.core.enums.ESagaStatus.*;
import static br.com.orchestrator.core.enums.ETopics.*;

public final class SagaHandler {

    private SagaHandler() {

    }

    public static final Object[][] SAGA_HANDLER = {
        { ORCHESTRATOR, SUCCESS, ADRESS_VALIDATION_SUCCESS },
        { ORCHESTRATOR, FAIL, FINISH_FAIL },

        { ADRESS_VALIDATION_SERVICE, ROLLBACK_PENDING, ADRESS_VALIDATION_FAIL },
        { ADRESS_VALIDATION_SERVICE, FAIL, FINISH_FAIL },
        { ADRESS_VALIDATION_SERVICE, SUCCESS, VALIDATED_SUCCESS },

        { VALIDATED_SERVICE, ROLLBACK_PENDING, VALIDATED_FAIL },
        { VALIDATED_SERVICE, FAIL, ADRESS_VALIDATION_FAIL },
        { VALIDATED_SERVICE, SUCCESS, REGISTRATION_SUCCESS },

        { REGISTRATION_SERVICE, ROLLBACK_PENDING, REGISTRATION_FAIL },
        { REGISTRATION_SERVICE, FAIL, REGISTRATION_FAIL },
        { REGISTRATION_SERVICE, SUCCESS, FINISH_SUCCESS }
    };

    public static final int EVENT_SOURCE_INDEX = 0;
    public static final int SAGA_STATUS_INDEX = 1;
    public static final int TOPIC_INDEX = 2;
}
