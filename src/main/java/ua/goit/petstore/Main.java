package ua.goit.petstore;

import ua.goit.petstore.controller.MainController;
import ua.goit.petstore.view.Console;
import ua.goit.petstore.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console(System.in, System.out);
        MainController controller = new MainController(view);
        controller.run();
    }
}
