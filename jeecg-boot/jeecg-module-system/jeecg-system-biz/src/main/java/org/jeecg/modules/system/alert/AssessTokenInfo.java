package org.jeecg.modules.system.alert;

/**
 * @author handong
 * @date 2018-11-09 09:59
 */
public class AssessTokenInfo {
    private String assessToken;

    private long expireTime = System.currentTimeMillis();

    public String getAssessToken() {
        return assessToken;
    }

    public void setAssessToken(String assessToken) {
        this.assessToken = assessToken;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
