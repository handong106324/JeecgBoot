package org.jeecg.modules.demo.utils;

import cn.hutool.core.io.IoUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class TwitterUtils {

    public static String bearerToken = "AAAAAAAAAAAAAAAAAAAAAMi3xQEAAAAA%2Ffh1d5w0Z66gg26xYrP3NZ44AIY%3DxJEA09cty2TEaWvzf90xSdvuj5jiwPhJPRaPsMTwkEOeSSTxRb";

//    public static void main(String... args){
//        Twitter twitter = Twitter.newBuilder()
//                .build();
//        TweetsResources tweets = twitter.v1().tweets();
//
//        FriendsFollowersResources friendsFollowersResources = twitter.v1().friendsFollowers();
//
//        try {
//            IDs noRetweetsFriendships = friendsFollowersResources.getNoRetweetsFriendships();
//            for (long id : noRetweetsFriendships.getIDs()) {
//                System.out.print(tweets.getRetweets(id));
//            }
//        } catch (TwitterException e) {
//            throw new RuntimeException(e);
//        }
//    }



    /*
     * This method calls the recent search endpoint with a the search term passed to it as a query parameter
     * */
    public static String search(String searchString) throws IOException, URISyntaxException {
        String searchResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/recent");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("query", searchString));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            searchResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return searchResponse;
    }



    public static String getUsers(String usernames) throws IOException, URISyntaxException {

        FileUtils.readLines(new File("./timeLimit.text")).forEach(v -> {
            String[] split = StringUtils.split(v, "=");
            if (split[0].equals("getUsers")) {
                long l = Long.parseLong(split[1]);
                if (System.currentTimeMillis() - l < 24 * 3600000) {
                    throw new RuntimeException("本方法24小时才能调用一次哦");
                }
            }
        });
        FileUtils.write(new File("./timeLimit.text"), "getUsers=" + System.currentTimeMillis());

        String userResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", usernames));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return userResponse;
    }


    /*
     * This method calls the v2 followers lookup endpoint by user ID
     * */
    public static String getFollowers(String userId) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/followers", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }
    /*
     * This method calls the v2 Tweets endpoint with ids as query parameter
     * */
    public static String getTweets(String ids) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("ids", ids));
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }



    public static String getTweetsByUserId(String userId) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/tweets", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        queryParameters.add(new BasicNameValuePair("max_results", "100"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }



    public static String getUserByUserName(String userName) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/by/username/"+userName));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }



    /*
     * This method calls the v2 User Mentions timeline endpoint by user ID
     * */
    public static String getTweetsMetions(String userId) throws IOException, URISyntaxException {
        String tweetResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(String.format("https://api.twitter.com/2/users/%s/mentions", userId));
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            tweetResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return tweetResponse;
    }

    public static String getUsageTweets(String bearerToken) throws IOException, URISyntaxException {
        String usageTweetsResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/usage/tweets");
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            usageTweetsResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return usageTweetsResponse;
    }
}
