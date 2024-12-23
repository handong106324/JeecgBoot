package org.jeecg.modules.system.alert;

/**
 * @author handong
 * @date 2018-11-09 09:52
 */
public enum  AlertType {
    JIEMO(1000003,"F4neFsPf4pXjpHAQlJOStRSQQavMmrldX2Fx-Ro0MFk"),
    ADMIN_BOOK(10001,"cqdeVHdrb4Oy14nOYKjhkKxbgaBn8XivP8v_cfH5A90"),
    ETH_ALERT(1000004,"lci-KuptDdS-ZTMv3Hjrgde5RZyKPy34ktddr-9xsUE"),
    BTC_ALERT(1000005,"nqmA-XuYPFscaeiZgoKA7KQUEp3GqomZVJ_0La52FG4"),
    EOS_ALERT(1000006,"ybeTrAx67y0rMg5MWUZG6yHfKVpIewHIb_ju7x71oM0"),
    STRE(1000002,"15bdzj7Ls1Z8bP5_9kaNhioqjEXMTMzKUZpCbUvW_Mo"),;


    private int agentId;
    private String secret;

    AlertType(int agentId, String secret) {
        this.agentId = agentId;
        this.secret = secret;
    }


    public int getAgentId() {
        return agentId;
    }


    public String getSecret() {
        return secret;
    }

}
