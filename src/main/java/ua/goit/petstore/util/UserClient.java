package ua.goit.petstore.util;

import ua.goit.petstore.model.ApiResponse;
import ua.goit.petstore.model.User;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UserClient extends HttpUtil<User> {
    private static final String CREATE_USER = "/user";
    private static final String CREATE_WITH_LIST = "/user/createWithList";
    private static final String READ_USER = "/user/";
    private static final String UPDATE_USER = "/user/";
    private static final String DELETE_USER = "/user/";


    public static ApiResponse createUser(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST", String.format("%s%s", HOST, CREATE_USER), user);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static ApiResponse createUserArray(List<User> users) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST", String.format("%s%s", HOST, CREATE_WITH_LIST), users);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static User getUserByUserName(String userName) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.sendGet(String.format("%s%s%s", HOST, READ_USER, userName));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), User.class);
    }

    public static ApiResponse updateUser(String userName, User newUser) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("PUT", String.format("%s%s%s", HOST, UPDATE_USER,
                userName), newUser);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static ApiResponse delete(String userName) throws IOException, InterruptedException {
        HttpRequest request = sendDelete(String.format("%s%s%s", HOST, DELETE_USER, userName));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }
}
