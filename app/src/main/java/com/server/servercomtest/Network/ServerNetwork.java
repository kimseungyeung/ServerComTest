package com.server.servercomtest.Network;

import android.util.Base64;
import android.util.Log;

import com.server.servercomtest.Data.UserData;
import com.server.servercomtest.Interface.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ServerNetwork implements NetworkService {
    private static final String CLIENT_ID = "test-client";
    private static final String CLIENT_SECRET = "123";
    private static final String REDIRECT_URL = "http://hidayz.com/";

    private static final String AUTH_SERVER_URI = "http://hidayz.com:8082/auth";
    private static final String AUTHORIZE_URL = AUTH_SERVER_URI + "/oauth/authorize";
    private static final String TOKEN_URL = AUTH_SERVER_URI + "/oauth/token";

    @Override
    public String connect(String username, String password) {
        Log.d("d", "============ CONNECT");
        String sessionId = null;
        try {
            URL url = new URL(AUTH_SERVER_URI + "/login");

            StringBuilder params = new StringBuilder();
            params.append("username=").append(URLEncoder.encode(username, "UTF-8")).append("&");
            params.append("password=").append(URLEncoder.encode(password, "UTF-8"));
            String parameters = params.toString();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=ISO-8859-1");
            connection.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
            connection.setInstanceFollowRedirects(false);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);

            Log.d("d", "Sending '{}' request to URL: {}" + connection.getRequestMethod() + url);
            Log.d("d", "Post parameters: {}" + parameters);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(params.toString());
            writer.flush();

            String cookies = connection.getHeaderField("Set-Cookie");
            sessionId = cookies.substring(11, cookies.indexOf(";"));
        } catch (Exception e) {
            Log.e("error", e.getMessage().toString());
        }
        return sessionId;
    }

    @Override
    public String signup(String token, JSONObject json) {
        Log.d("d", "============ SIGNUP");
        String result = null;
        try {
            URL url = new URL(AUTH_SERVER_URI + "/api/user/signup");

            String authorization = "Bearer " + token;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Authorization", authorization);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);

            Log.d("d", "Sending '{}' request to URL: {}" + connection.getRequestMethod() + "" + url);
            Log.d("d", "Authorization: {}" + authorization);

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(json.toString());
            writer.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // result = br.lines().collect(Collectors.joining());
            String buffer = "";
            String text = "";
            while ((buffer = br.readLine()) != null) {
                text += buffer;
            }
            result = text;
            Log.d("d", "{}" + result);
        } catch (Exception e) {
            Log.e("error",e.getMessage().toString());
        }
        return result;
    }

    @Override
    public void signup(UserData ud) {

    }

    @Override
    public void signin(String username,String password) {

    }



    @Override
    public String gettoken(String id, String password) {
        String token = null;
        try {
            String sessionId = connect(id, password);
            //  Log.d("d","SessionID = {}", sessionId);

            String code = authorize(sessionId);
            //  Log.d("d","Code = {}", code);

            String granted = token(sessionId, code);
            //  Log.d("d","Granted = {}", granted);

            JSONObject json = new JSONObject(granted);
            token = json.getString("access_token");
            //  Log.d("d","Token = {}", token);
        } catch (JSONException | IOException e) {
            Log.e("error", e.getMessage().toString());
        }
        return token;
    }

    @Override
    public String authorize(String sessionId) throws IOException {
        Log.d("d", "============ AUTHORIZE");

        URL url = new URL(AUTHORIZE_URL);

        StringBuilder params = new StringBuilder();
        params.append("response_type=code&");
        params.append("client_id=").append(URLEncoder.encode(CLIENT_ID, "UTF-8")).append("&");
        params.append("redirect_uri=").append(URLEncoder.encode(REDIRECT_URL, "UTF-8"));
        String parameters = params.toString();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=ISO-8859-1");
        connection.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
        connection.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
        connection.setInstanceFollowRedirects(false);
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);

        //   Log.d("d","Sending '{}' request to URL: {}", connection.getRequestMethod(), url);
        //    Log.d("d","Post parameters: {}", parameters);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(params.toString());
        writer.flush();

        String location = connection.getHeaderField("Location");
        String code = location.substring(location.indexOf("code=") + 5);

        return code;
    }

    @Override
    public String token(String sessionId, String code) throws IOException {
        Log.d("d", "============ TOKEN");

        URL url = new URL(TOKEN_URL);

        String authorization = "Basic " + Base64.encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(), Base64.DEFAULT);

        StringBuilder params = new StringBuilder();
        params.append("code=").append(URLEncoder.encode(code, "UTF-8")).append("&");
        params.append("grant_type=authorization_code&");
        params.append("client_id=").append(URLEncoder.encode(CLIENT_ID, "UTF-8")).append("&");
        params.append("redirect_uri=").append(URLEncoder.encode(REDIRECT_URL, "UTF-8"));
        String parameters = params.toString();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=ISO-8859-1");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Content-Length", String.valueOf(parameters.length()));
        connection.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
        connection.setRequestProperty("Authorization", authorization);
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoOutput(true);

        //  Log.d("d","Sending '{}' request to URL: {}", connection.getRequestMethod(), url);
        // Log.d("d","Authorization: {}", authorization);
        //  Log.d("d","Post parameters: {}", parameters);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        writer.write(params.toString());
        writer.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //   String result = br.lines().collect(Collectors.joining());
//        log.debug("{}", result);

        String buffer = "";
        String text = "";
        while ((buffer = br.readLine()) != null) {
            text += buffer;
        }
        String result = text;
        return result;
    }
}
