package org.jeecg.modules.demo.utils;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.clientlib.ApiClient;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.TwitterCredentialsOAuth2;
import com.twitter.clientlib.api.UsersApi;
import com.twitter.clientlib.model.Get2UsersMeResponse;
import com.twitter.clientlib.model.User;
import org.apache.commons.io.FileUtils;
import org.jeecg.modules.demo.twitter.TweetsData;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TwitterClient {

    public static String[] users = new String[]{
            "0xcryptowizard",
            "jinruiliu7",
            "PeterG2100"
    };
    private static String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAMi3xQEAAAAA%2Ffh1d5w0Z66gg26xYrP3NZ44AIY%3DxJEA09cty2TEaWvzf90xSdvuj5jiwPhJPRaPsMTwkEOeSSTxRb"; // add bearer token here

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        TweetsData tweetsByUserId = TwitterUtils.getTweetsByUserId("1525742352006287361");

        System.out.println(tweetsByUserId);
//        Arrays.stream(users).forEach(v -> {
//            String userInfo = null;
//            try {
//                userInfo = TwitterUtils.getUserByUserName(v);
//                FileUtils.writeLines(new File("./ccc.txt"), Arrays.asList(userInfo), true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        });

//        String search = TwitterUtils.search("@klys7788");
//        System.out.println(search);

//        String usageTweets = TwitterUtils.getUsers(Arrays.stream(users).collect(Collectors.joining(",")));
//        System.out.println(usageTweets);

//        String vxNlcjoxMTUzOTg1NzEzODM2Mzg0MjU2 = TwitterUtils.getFollowers("VXNlcjoxMTUzOTg1NzEzODM2Mzg0MjU2");
//
//        System.out.println(vxNlcjoxMTUzOTg1NzEzODM2Mzg0MjU2);

//        String collect = Arrays.stream(users).collect(Collectors.joining(","));
//        String users1 = TwitterUtils.getUsers(collect);
////
//        String string = JSONObject.parseObject(users1).toString(SerializerFeature.PrettyFormat);
//        System.out.println(string);
//        TwitterCredentialsBearer bearer = new TwitterCredentialsBearer(BEARER_TOKEN);
//        TwitterCredentialsOAuth2 twitterCredentialsOAuth2 = new TwitterCredentialsOAuth2("KkFVNPdd0KTmfnw4j400oWIVe", "0iaJevRKFtACirXG8Q1tdQtG7YppPyRbELOmvcRRYQRamP4v5z",
//                "1754708209166884864-iFJEGbwKsbHLX8VbxdCvwyALuLKGID", "ZxRpxmn9rCbXuyMXCPt4KGOUIYHtFCJrFlI9964bzghjx");
//        ApiClient apiClient = new ApiClient();
////        apiClient.setTwitterCredentials(bearer);
//        apiClient.setTwitterCredentials(twitterCredentialsOAuth2);
////        apiClient.setApiKey("KkFVNPdd0KTmfnw4j400oWIVe");
////        apiClient.setAccessToken("1754708209166884864-iFJEGbwKsbHLX8VbxdCvwyALuLKGID");
//
//        UsersApi usersApi = new UsersApi();
//        UsersApi.APIfindMyUserRequest myUser = usersApi.findMyUser();
//        try {
//            Get2UsersMeResponse execute = myUser.execute();
//            if (execute != null) {
//                User user = execute.getData();
//                System.out.println(JSONObject.toJSONString(user));
//                UsersApi.APIlistGetFollowersRequest apIlistGetFollowersRequest = usersApi.listGetFollowers(user.getId());
//                System.out.println(JSONObject.toJSONString(apIlistGetFollowersRequest.execute().getData()));
//
//            }
//        } catch (ApiException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//

    }
}