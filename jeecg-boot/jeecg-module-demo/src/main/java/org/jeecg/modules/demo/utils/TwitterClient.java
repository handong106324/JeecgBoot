package org.jeecg.modules.demo.utils;

import cn.hutool.http.HttpRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterClient {

    private static String BEARER_TOKEN = "AAAAAAAAAAAAAAAAAAAAAMi3xQEAAAAAbSo3rqPcm0xk%2BP4PnZrZJWVmLDc%3DFGBEmnlmiMVerFPmLdTikWKkoD5lschCpy30LDHI3ve5plp4uL"; // add bearer token here

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {

        TwitterAPI.getFollowers(BEARER_TOKEN, "0xzerebro");

    }
}