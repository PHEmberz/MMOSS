/**
 * Main system class, contains highest-level system logic.
 *
 * @version ver1.0.0
 */
public class MMOSS {
    private InventoryControl inventoryController;
    private UserControl userController;

    /**
     * Default constructor of MMOSS class.
     */
    public MMOSS() {
        inventoryController = new InventoryControl();
        userController = new UserControl();
    }

    /**
     * Main logic for customer, loop and attend to customer's input until user logout.
     */
    private void customerLogic() {
        ShoppingCartControl shoppingCartController = new ShoppingCartControl();

        while (true) {
            Boundary.printCustomerMainMenu(userController.getCurrentUserName());
            String input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                userController.logout();
                return;
            }

            switch (input) {
                case "1" -> { // view product list
                    int result = inventoryController.viewInventory();
                    switch (result) {
                        case Constant.CODE_BACK -> {
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "2" -> { // add product to shopping cart
                    int result = shoppingCartController.addProductToCart(inventoryController);
                    switch (result) {
                        case Constant.CODE_BACK -> {
                            continue;
                        }
                        case Constant.OPERATION_FAIL -> {
                            Boundary.alertFullCart();
                        }
                        case Constant.OPERATION_SUCCESS -> {
                            Boundary.printOperationSuccess(Constant.OP_TYPE_ADD_TO_CART);
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "3" -> { // view shopping cart
                    int result = shoppingCartController.viewShoppingCart(userController, inventoryController);
                    switch (result) {
                        case Constant.CODE_BACK, Constant.OPERATION_SUCCESS -> {
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "4" -> { //logout
                    //confirmation
                    boolean valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_LOGOUT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_BACK) ||
                                input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            valid = true;
                        }
                        else if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            userController.logout();
                            return;
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case Constant.STRING_BACK -> {
                    Boundary.alertMainMenu();
                }
                default -> {
                    Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                }
            }

        }
    }

    /**
     * Main logic for admin, loop and attend to admin's input until user logout.
     */
    private void adminLogic() {
        // needs to be done
        while (true) {
            Boundary.printAdminMainMenu();
            String input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                userController.logout();
                return;
            }
            switch (input) {
                case "1" -> { // view product list
                    int result = inventoryController.viewInventory();
                    switch (result) {
                        case Constant.CODE_BACK -> {
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "2" -> { // add a product
                    int result = inventoryController.createProduct();
                    switch (result) {
                        case Constant.OPERATION_SUCCESS -> {
                            Boundary.printOperationSuccess(Constant.OP_TYPE_CREATE_PRODUCT);
                        }
                        case Constant.CODE_BACK, Constant.OPERATION_CANCEL -> {
                            Boundary.printOperationCancel(Constant.OP_TYPE_CREATE_PRODUCT);
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "3" -> { // edit a product
                    int result = inventoryController.editProduct();
                    switch (result) {
                        case Constant.OPERATION_SUCCESS, Constant.CODE_BACK -> {
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "4" -> { // remove a product
                    int result = inventoryController.removeProduct();
                    switch (result) {
                        case Constant.OPERATION_SUCCESS -> {
                            Boundary.printOperationSuccess(Constant.OP_TYPE_REMOVE_PRODUCT);
                        }
                        case Constant.OPERATION_CANCEL -> {
                            Boundary.printOperationCancel(Constant.OP_TYPE_REMOVE_PRODUCT);
                        }
                        case Constant.CODE_BACK -> {
                            continue;
                        }
                        case Constant.CODE_LOGOUT -> {
                            //back to guestLogic, set current user as null
                            userController.logout();
                            return;
                        }
                    }
                }
                case "5" -> { // logout
                    //confirmation
                    boolean valid = false;
                    while (!valid) {
                        Boundary.promptConfirm(Constant.OP_TYPE_LOGOUT);
                        input = Input.acceptStringInput();

                        if (input.equalsIgnoreCase(Constant.STRING_BACK) ||
                                input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                            valid = true;
                        }
                        else if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                            userController.logout();
                            return;
                        } else {
                            Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                        }
                    }
                }
                case Constant.STRING_BACK -> {
                    Boundary.alertMainMenu();
                }
                default -> {
                    Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                }
            }
        }
    }

    /**
     * Main logic for guest, loop and attend to guest user's input until user logout.
     * Serve as the start point of the overall system.
     */
    public void guestLogic() {
        // provides login operation to go into customerLogic/adminLogic
        while (true) {
            try {
                Boundary.printGuestMainMenu();
                String input = Input.acceptStringInput();

                // user can not go back or logout in this page.

                switch (input) {
                    case "1" -> {
                        // go into login logic
                        int result = userController.login();;
                        switch (result) {
                            case Constant.OPERATION_SUCCESS -> {
                                Boundary.printOperationSuccess(Constant.OP_TYPE_LOGIN);
                                // account data is correct, redirect to customer page
                                // if return from customer page, this means customer logs out, no need
                                // for additional operation.
                                if (userController.getCurrentUser().isAdmin()) {
                                    adminLogic();
                                } else {
                                    customerLogic();
                                }
                            }
                            case Constant.OPERATION_FAIL -> {
                                // account data is incorrect
                                Boundary.alertInvalidValue(Constant.FIELD_ACCOUNT);
                            }
                            case Constant.CODE_BACK -> {
                                continue;
                            }
                        }
                    }
                    case "2" -> {
                        //confirmation
                        boolean valid = false;
                        while (!valid) {
                            Boundary.promptConfirm(Constant.OP_TYPE_EXIT);
                            input = Input.acceptStringInput();

                            if (input.equalsIgnoreCase(Constant.STRING_BACK) ||
                                    input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                                valid = true;
                            }
                            else if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                                System.exit(0);
                            } else {
                                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
                            }
                        }
                    }
                    default -> {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }
                }
            }
            catch (Exception e){
                Boundary.alertException(e.getMessage());
            }
        }
    }

    /**
     * Method to be the program.
     *
     * @param args       The terminal command arguments as an
     *                   array of strings.
     *
     */
    public static void main(String[] args) {
        MMOSS control = new MMOSS();
        Boundary.printSystemUsage();
        control.guestLogic();
    }
}
