package com.founder.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceVisitLog {
    private String MESSAGE_TYPE;
    private String REQUEST_CONTENT;
    private String RESPONSE_CONTENT;
    private String REQUEST_DATETIME;
    private String RESPONSE_TIME;
    private String SERVICE_NAME;
    private String SERVICE_VERSION;
    private String CONSUMER_IP;
    private String SENDER_ID;
    private String RESPONSE_RESULT_COUNT;
    private String REQUEST_SERIAL;
    private String GZDB_ADDTIME;

}
