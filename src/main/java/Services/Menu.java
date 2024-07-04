package Services;

import Model.FlowerShop;

import static Services.Input.readInt;

public class Menu {

    public static void menu() {
        // Show products byt quantity
        int option = -1;
        boolean working = true;

        do {
            System.out.println("Welcome, how may I help you?");
            System.out.println("1. Create flower shop\n " +
                    "2.Add product\n " +
                    "3. Remove product\n " +
                    "4. Show stock\n " +
                    "5. Show products byt quantity\n " +
                    "6. Value of the flower shop\n " +
                    "7. Create ticket\n " +
                    "8. Show history\n " +
                    "9. Money earned from all sales\n " +
                    "0. Exit");
            option = readInt("Enter your option : ");

            switch (option){
                case 1 :
                    break;
                case 2 :
                    break;
                case 3 :
                    break;
                case 4 :
                    break;
                case 5 :
                    break;
                case 6 :
                    break;
                case 7 :
                    break;
                case 8 :
                    break;
                case 9 :
                    break;
                case 0 :
                    break;
                default:
                    System.out.println("Invalid option");
            }
        } while (working);
    }
}
