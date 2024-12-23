package org.jeecg.modules.system.alert;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.system.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WeiXinAlert {
    public static Logger logger = LoggerFactory.getLogger(WeiXinAlert.class);

    private static class WeiXinAlertHolder {
        private static WeiXinAlert INSTANCE = new WeiXinAlert();

    }

    private Map<AlertType,AssessTokenInfo> tokenInfoMap = new HashMap<>();


    private  String corpId = "ww24c1594fa483a2ea";

    public static void main(String[] args) throws Exception {

        WeiXinAlert.getInstance().sendMessage( "333",AlertType.EOS_ALERT);
    }

    public static WeiXinAlert getInstance() {
        return WeiXinAlertHolder.INSTANCE;
    }

    public  void sendMessage(String msg, AlertType alertType) {
        AssessTokenInfo asseessTokenInfo = tokenInfoMap.get(alertType);
        if (null == asseessTokenInfo) {
            asseessTokenInfo = getAssetsToken(alertType.getSecret());
        }
        long expireTime = asseessTokenInfo.getExpireTime();
        if (System.currentTimeMillis() > expireTime) {
            asseessTokenInfo = getAssetsToken(alertType.getSecret());
        }

        if (null == asseessTokenInfo) {
            return;
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + asseessTokenInfo.getAssessToken();

        JSONObject map = new JSONObject();
        map.put("touser", "@all");
        map.put("agentid", alertType.getAgentId());
        map.put("msgtype", "text");
        Map<String,String> text = new HashMap<>();
        text.put("content", msg);
        map.put("text", text);
        String res = HttpUtil.doPostSSL(url,map);
    }
    public  void sendMessageToParty(String msg, AlertType alertType, String partId) {
        AssessTokenInfo asseessTokenInfo = tokenInfoMap.get(alertType);
        if (null == asseessTokenInfo) {
            asseessTokenInfo = getAssetsToken(alertType.getSecret());
        }
        long expireTime = asseessTokenInfo.getExpireTime();
        if (System.currentTimeMillis() > expireTime) {
            asseessTokenInfo = getAssetsToken(alertType.getSecret());
        }

        if (null == asseessTokenInfo) {
            return;
        }
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + asseessTokenInfo.getAssessToken();

        JSONObject map = new JSONObject();
        map.put("toparty", partId);
        map.put("agentid", alertType.getAgentId());
        map.put("msgtype", "text");
        Map<String,String> text = new HashMap<>();
        text.put("content", msg);
        map.put("text", text);
        String res = HttpUtil.doPostSSL(url,map);
    }

    public  AssessTokenInfo getAssetsToken(String secret) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpId+"&corpsecret=" + secret;

        String res = HttpUtil.doGet(url);

        JSONObject jsonObject = JSONObject.parseObject(res);

        if (null != jsonObject) {
            AssessTokenInfo asseessTokenInfo = new AssessTokenInfo();
            String asseessToken = jsonObject.getString("access_token");
            long expireTime = System.currentTimeMillis() + 7195;
            asseessTokenInfo.setAssessToken(asseessToken);
            asseessTokenInfo.setExpireTime(expireTime);
            return asseessTokenInfo;
        }

        return null;
    }

    /**
     *  {
     *    "errcode" : 0,
     *    "errmsg" : "ok",
     *    "chatid" : "CHATID"
     *  }
     * @param assessTokenInfo
     * @param name
     * @param owner
     * @param chatId
     * @param userList
     * @return
     */
    public JSONObject createChatGroup(String assessTokenInfo, String name, String owner, String chatId, String[] userList) {
       String url =  "https://qyapi.weixin.qq.com/cgi-bin/appchat/create?access_token="+ assessTokenInfo;
       JSONObject jsonObject = new JSONObject();
       jsonObject.put("name", name);
       jsonObject.put("owner", owner);
       jsonObject.put("userlist", userList);
       jsonObject.put("chatid", chatId);

       String res = HttpUtil.doPostSSL(url, jsonObject);

       JSONObject chatJson = JSONObject.parseObject(res);
       return chatJson;
    }

    public JSONArray getDepList(String assessToken) {
        String url ="https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+assessToken;
        String  res = HttpUtil.doGet(url);
        JSONObject data = JSONObject.parseObject(res);
        return data.getJSONArray("department");

    }

    public JSONObject createDep(String token, String name){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+token;

        JSONObject param = new JSONObject();
        param.put("name",name);
        param.put("parentid",1);
        String res = HttpUtil.doPostSSL(url, param);

        return JSONObject.parseObject(res);
    }

    public JSONArray getDepUserList(String assessToken,String depId) {
        String url ="https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token="+assessToken+"&department_id="+depId;
        String  res = HttpUtil.doGet(url);
        JSONObject data = JSONObject.parseObject(res);
        return data.getJSONArray("userlist");

    }
}
