package Shop;

import Shop.menu.ClothingStoreManager;
import Shop.menu.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new ClothingStoreManager();
        menu.run();
    }
}