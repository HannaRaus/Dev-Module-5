package ua.goit.petstore.util;

import com.google.gson.reflect.TypeToken;
import ua.goit.petstore.model.ApiResponse;
import ua.goit.petstore.model.Order;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class OrderClient extends HttpUtil<Order> {
    private static final String CREATE_ORDER = "/store/order";
    private static final String READ_ORDER = "/store/order/";
    private static final String READ_ORDER_BY_STATUS = "/store/inventory";
    private static final String DELETE_ORDER = "/store/order/";

    public static Order createOrder(Order order) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST",
                String.format("%s%s", HOST, CREATE_ORDER), order);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), Order.class);
    }

    public static Order getOrderById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.sendGet(String.format("%s%s%d", HOST, READ_ORDER, id));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), Order.class);
    }

    public static ApiResponse delete(int id) throws IOException, InterruptedException {
        HttpRequest request = sendDelete(String.format("%s%s%d", HOST, DELETE_ORDER, id));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static HashMap<String, Integer> getInventories() throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.sendGet(String.format("%s%s", HOST, READ_ORDER_BY_STATUS));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), new TypeToken<HashMap<String, Integer>>() {
        }.getType());
    }

}
