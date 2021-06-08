package ua.goit.petstore.command;

import ua.goit.petstore.model.ApiResponse;
import ua.goit.petstore.model.Pet;
import ua.goit.petstore.model.PetStatus;
import ua.goit.petstore.model.User;
import ua.goit.petstore.util.PetClient;
import ua.goit.petstore.util.UserClient;
import ua.goit.petstore.view.View;

import java.io.IOException;
import java.util.List;

public class Update extends AbstractCommand implements Command {
    private static final String MENU = """
            Please, enter the number according to list below
            1 - to update user
            2 - to update pet
            3 - add photos for pet
            return - go back to main menu
            """;
    private final View view;

    public Update(View view) {
        super(view);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "update";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write(MENU);
            String section = view.read();
            switch (section) {
                case "1" -> updateUser();
                case "2" -> updatePet();
                case "3" -> addPhotos();
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private void updateUser() {
        view.write("Enter user name you would like to update");
        String userName = view.read();
        try {
            UserClient.getUserByUserName(userName);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
        User user = readUserFromConsole();
        try {
            ApiResponse apiResponse = UserClient.updateUser(userName, user);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void updatePet() {
        int id = getIntegerFromConsole("Enter pet id you would like to update");
        try {
            Pet petToUpdate = PetClient.getPetById(id);
            view.write("Enter pet new name");
            String newName = view.read();
            PetStatus newStatus = getPetStatusFromConsole();
            petToUpdate.setName(newName);
            petToUpdate.setStatus(newStatus);
            ApiResponse apiResponse = PetClient.updatePet(id, petToUpdate);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }

    private void addPhotos() {
        int id = getIntegerFromConsole("Enter pet id you would like to update");
        List<String> photoUrls = getPhotoUrlsFromConsole();
        try {
            ApiResponse apiResponse = PetClient.uploadImage(id, photoUrls);
            resultOutput(apiResponse);
        } catch (IOException | InterruptedException ex) {
            view.write(ex.getMessage());
        }
    }
}
