package ua.goit.petstore.command;

import ua.goit.petstore.model.*;
import ua.goit.petstore.view.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCommand {
    private final View view;

    public AbstractCommand(View view) {
        this.view = view;
    }

    protected int getIntegerFromConsole(String message) {
        int number = 0;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write(message);
                number = Integer.parseInt(view.read());
                if (number < 0) {
                    view.write("Number is less, than zero, please, enter the correct one");
                } else {
                    isFieldBlank = false;
                }
            } catch (Exception e) {
                view.write("Wrong format, please, enter integer.");
            }
        }
        return number;
    }

    protected User readUserFromConsole() {
        int id = getIntegerFromConsole("Enter user id");
        view.write("Enter user name");
        String userName = view.read();
        view.write("Enter user first name");
        String firstName = view.read();
        view.write("Enter user last name");
        String lastName = view.read();
        view.write("Enter user email");
        String email = view.read();
        view.write("Enter user password");
        String password = view.read();
        view.write("Enter user phone number");
        String phone = view.read();
        int status = getIntegerFromConsole("Enter user status");
        return new User(id, userName, firstName, lastName, email, password, phone, status);
    }

    protected Pet readPetFromConsole() {
        int id = getIntegerFromConsole("Enter pet id");
        Category category = getCategoryFromConsole();
        view.write("Enter pet name");
        String name = view.read();
        List<String> photoUrls = getPhotoUrlsFromConsole();
        List<Tag> tags = getTagsFromConsole();
        PetStatus status = getPetStatusFromConsole();
        return new Pet(id, category, name, photoUrls, tags, status);
    }

    private Category getCategoryFromConsole() {
        int id = getIntegerFromConsole("Enter category id");
        view.write("Enter category name");
        String name = view.read();
        return new Category(id, name);
    }

    private List<Tag> getTagsFromConsole() {
        List<Tag> tags = new ArrayList<>();
        boolean running = true;
        while (running) {
            tags.add(getTagFromConsole());
            view.write("Successfully added.Press 'enter' to continue\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return tags;
    }

    protected List<String> getPhotoUrlsFromConsole() {
        List<String> photoUrls = new ArrayList<>();
        boolean running = true;
        while (running) {
            view.write("Enter photo url");
            photoUrls.add(view.read());
            view.write("Successfully added. Press 'enter' to add another photo url\nEnter 'ok' when finish");
            if (view.read().equalsIgnoreCase("ok")) {
                running = false;
            }
        }
        return photoUrls;
    }

    private Tag getTagFromConsole() {
        int id = getIntegerFromConsole("Enter tag id");
        view.write("Enter tag name");
        String name = view.read();
        return new Tag(id, name);
    }

    protected Order readOrderFromConsole() {
        int id = getIntegerFromConsole("Enter order id");
        int petId = getIntegerFromConsole("Enter pet id");
        int quantity = getIntegerFromConsole("Enter quantity");
        String shipDate = LocalDate.now().toString();
        OrderStatus status = getOrderStatusFromConsole();
        boolean complete = getBooleanFromConsole();
        return new Order(id, petId, quantity, shipDate, status, complete);
    }

    protected OrderStatus getOrderStatusFromConsole() {
        OrderStatus orderStatus = null;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter the order status");
                orderStatus = OrderStatus.valueOf(view.read().toUpperCase());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong data, choose from list below");
                Arrays.stream(OrderStatus.values()).map(OrderStatus::name).forEach(System.out::println);
            }
        }
        return orderStatus;
    }

    protected PetStatus getPetStatusFromConsole() {
        PetStatus orderStatus = null;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter the order status");
                orderStatus = PetStatus.valueOf(view.read().toUpperCase());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong data, choose from list below");
                Arrays.stream(PetStatus.values()).map(PetStatus::name).forEach(System.out::println);
            }
        }
        return orderStatus;
    }

    protected boolean getBooleanFromConsole() {
        boolean complete = false;
        boolean isFieldBlank = true;
        while (isFieldBlank) {
            try {
                view.write("Enter 'true' if order is completed, else enter 'false'");
                complete = Boolean.parseBoolean(view.read());
                isFieldBlank = false;
            } catch (Exception e) {
                view.write("Wrong format, please, enter 'true' or 'false'.");
            }
        }
        return complete;
    }

    protected void resultOutput(ApiResponse response) {
        if (response.getCode() == 200) {
            view.write("Updated successfully");
        } else {
            view.write("""
                        Failed to update
                        Response -""" + response.getMessage());
        }
    }
}
