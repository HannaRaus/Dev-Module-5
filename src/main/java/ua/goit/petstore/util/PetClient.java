package ua.goit.petstore.util;

import com.google.gson.reflect.TypeToken;
import ua.goit.petstore.model.ApiResponse;
import ua.goit.petstore.model.Pet;
import ua.goit.petstore.model.PetStatus;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PetClient extends HttpUtil<Pet> {
    private static final String CREATE_PET = "/pet";
    private static final String UPLOAD_IMAGE = "/uploadImage";
    private static final String UPDATE_PET = "/pet/";
    private static final String READ_PET = "/pet/";
    private static final String READ_PET_BY_STATUS = "/pet/findByStatus?status=";
    private static final String DELETE_PET = "/pet/";

    public static Pet createPet(Pet pet) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST", String.format("%s%s", HOST, CREATE_PET), pet);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static ApiResponse uploadImage(int id, List<String> images) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST", String.format("%s%s%d%S", HOST, UPDATE_PET,
                id, UPLOAD_IMAGE), images);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static ApiResponse updatePet(int id, Pet newPet) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.requestWithBody("POST", String.format("%s%s%d", HOST, UPDATE_PET, id),
                newPet);
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }

    public static Pet getPetById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.sendGet(String.format("%s%s%s", HOST, READ_PET, id));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), Pet.class);
    }

    public static List<Pet> getPetByStatus(PetStatus status) throws IOException, InterruptedException {
        HttpRequest request = HttpUtil.sendGet(String.format("%s%s%s", HOST, READ_PET_BY_STATUS,
                status.toString().toLowerCase()));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), new TypeToken<List<Pet>>() {
        }.getType());
    }

    public static ApiResponse delete(int id) throws IOException, InterruptedException {
        HttpRequest request = sendDelete(String.format("%s%s%d", HOST, DELETE_PET, id));
        HttpResponse<String> response = HttpUtil.getResponse(request);
        return GSON.fromJson(response.body(), ApiResponse.class);
    }
}
