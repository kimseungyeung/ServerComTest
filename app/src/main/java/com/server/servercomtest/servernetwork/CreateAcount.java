//package com.server.servercomtest.servernetwork;
//
//
//import android.content.Context;
//import android.widget.Toast;
//
//import com.server.servercomtest.Data.UserData;
//
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.response.Response;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//public class CreateAcount {
//
//
//    public void signup(Context con,UserData ud) {
//        // 회원가입이나 로그인이 안된 상태에서는 hidayz/hidayz 로 로그인 후 토큰 획득
//        String accessToken = getToken(ud.getEmail(),ud.getPassword());
//
//
//
//        // 회원정보를 JSON 타입으로 가입API로 전송
//        Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).contentType(ContentType.JSON).body(ud).post("http://localhost:8082/auth/api/user/signup");
//
//       Toast.makeText(con,"result: " + response.asString(),Toast.LENGTH_LONG).show();
//
//
//    }
//
//    private String getToken(String username, String password) {
//        final String authServerUri = "http://hidayz.com:8082/auth";
//        final String redirectUrl = "http://hidayz.com/";
//        final String authorizeUrl = authServerUri + "/oauth/authorize?response_type=code&client_id=test-client&redirect_uri=" + redirectUrl;
//        final String tokenUrl = authServerUri + "/oauth/token";
//
//        // user login
//        Response response = RestAssured.given().formParams("username", username, "password", password).post(authServerUri + "/login");
//        final String cookieValue = response.getCookie("JSESSIONID");
//
//        System.out.println("JSESSIONID = " + cookieValue);
//
//        // get authorization code
//        RestAssured.given().cookie("JSESSIONID", cookieValue).get(authorizeUrl);
//        response = RestAssured.given().cookie("JSESSIONID", cookieValue).post(authorizeUrl);
//
//
//
//        final String location = response.getHeader("Location");
//        final String code = location.substring(location.indexOf("code=") + 5);
//
//        System.out.println("code = " + code);
//
//        // get access token
//        Map<String, String> params = new HashMap<String, String>();
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
//
//}