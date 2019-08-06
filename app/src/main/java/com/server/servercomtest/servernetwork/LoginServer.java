//package com.server.servercomtest.servernetwork;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//public class LoginServer {
//
//    public void 로그인후개인정보가져오기() {
//        String accessToken = obtainAccessTokenUsingAuthorizationCodeFlow("hidayz", "hidayz");
//
//        Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://hidayz.com:8082/auth/api/user/me");
//
//        System.out.println("result: " + response.asString());
//
//
//    }
//
//    private String obtainAccessTokenUsingAuthorizationCodeFlow(String username, String password) {
//        final String authServerUri = "http://hidayz.com:8082/auth";
//        final String redirectUrl = "http://hidayz.com/";
//        final String authorizeUrl = authServerUri + "/oauth/authorize?response_type=code&client_id=test-client&redirect_uri=" + redirectUrl;
//        final String tokenUrl = authServerUri + "/oauth/token";
//
//        // user login
//        Response response = RestAssured.given().formParams("username", username, "password", password).post(authServerUri + "/login");
//        final String cookieValue = response.getCookie("JSESSIONID");
//
//        // get authorization code
//        RestAssured.given().cookie("JSESSIONID", cookieValue).get(authorizeUrl);
//        response = RestAssured.given().cookie("JSESSIONID", cookieValue).post(authorizeUrl);
//
//        final String location = response.getHeader("Location");
//        final String code = location.substring(location.indexOf("code=") + 5);
//
//        // get access token
//        Map<String, String> params = new HashMap<>();
//        params.put("grant_type", "authorization_code");
//        params.put("code", code);
//        params.put("client_id", "test-client");
//        params.put("redirect_uri", redirectUrl);
//        response = RestAssured.given().auth().basic("test-client", "123").formParams(params).post(tokenUrl);
//
//        System.out.println("result = " + response.asString());
//        System.out.println("token = " + response.jsonPath().getString("access_token"));
//
//        return response.jsonPath().getString("access_token");
//    }
//}
