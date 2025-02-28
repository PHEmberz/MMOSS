/**
 * Class which keeps track of shopping cart and provides management operations.
 *
 * @version ver1.0.0
 */
public class ShoppingCartControl {
    
    private ShoppingCart shoppingCart;

    /**
     * Default constructor of ShoppingCartControl class.
     */
    public ShoppingCartControl() {
        this.shoppingCart = new ShoppingCart();
    }

    /**
     * Control the process of adding a user specified product to shopping cart.
     *
     * @param inventoryController               Controller that manages inventory.
     * @return                                  A code representing the operation's result.
     */
    public int addProductToCart(InventoryControl inventoryController) {

        if (shoppingCart.getSize() == Constant.CART_ITEM_LIMIT) {
            return Constant.OPERATION_FAIL;
        }

        int productID;
        Product product = null;
        String input;

        boolean validID = false;
        while (!validID) {

            Boundary.promptFieldInput(Constant.FIELD_PRODUCT_ID);
            input = Input.acceptStringInput();

            // check whether user inputs keyword
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            if (Util.isInteger(input)) {
                productID = Integer.parseInt(input);
                product = inventoryController.getProductByID(productID);
                if (product == null) {  // id doesn't exist
                    Boundary.alertProductNotExist(productID);
                } else { // check whether we already have 10 of this product in cart

                    Item item = shoppingCart.getItemByProduct(product);
                    if (item != null && item.getQuantity() == Constant.ITEM_QUANTITY_LIMIT) {
                        Boundary.alertAlreadyReachedLimit();
                    }
                    else if (product.getQuantity() == 0) {
                        Boundary.alertOutOfStock();
                    }
                    else if (item != null && item.getQuantity() == product.getQuantity()) {
                        Boundary.alertAlreadyReachedStock(product.getQuantity());
                    }
                    else {// product is fine
                        validID = true;
                    }
                }
            } else {
                // input is neither keyword
                Boundary.alertUnformattedInput(Constant.FIELD_PRODUCT_ID);
            }
        }

        //after getting a valid product, request for quantity
        int currentStock = product.getQuantity();


        boolean validQuantity = false;
        while (!validQuantity) {

            Boundary.promptFieldInput(Constant.FIELD_QUANTITY);
            input = Input.acceptStringInput();

            // check whether user inputs keyword
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            int inCartQuantity = shoppingCart.checkProductQuantity(product);

            if (Util.isInteger(input)) {
                // check whether the quantity is > 10
                int quantity = Integer.parseInt(input);

                if (quantity > Constant.ITEM_QUANTITY_LIMIT || quantity < 0) {
                    Boundary.alertInvalidValue(Constant.FIELD_QUANTITY);
                } else if (inCartQuantity == 0) {
                    if (quantity > currentStock) {
                        // over inventory
                        Boundary.alertOutOfStock(currentStock);
                    } else {
                        validQuantity = true;
                        shoppingCart.addItem(product, quantity);
                    }
                } else {
                    if (inCartQuantity + quantity > Constant.ITEM_QUANTITY_LIMIT) {
                        // already added in cart, the sum is over inventory
                        Boundary.alertSumOutOfLimit(inCartQuantity);
                    } else if (inCartQuantity + quantity > currentStock) {
                        // already added in cart, the sum is over inventory
                        Boundary.alertSumOutOfStock(currentStock, inCartQuantity);
                    } else {
                        validQuantity = true;
                        shoppingCart.setItemQuantity(product, inCartQuantity + quantity);
                    }

                }

            } else {
                Boundary.alertUnformattedInput(Constant.FIELD_QUANTITY);
            }
        }
        return Constant.OPERATION_SUCCESS;
    }

    /**
     * Control the process of viewing the order's summary before checking out shopping cart.
     *
     * @param userController                    Controller that manages users.
     * @param inventoryController               Controller that manages inventory.
     * @return                                  A code representing the operation's result.
     */
    private int viewOrderSummary(UserControl userController, InventoryControl inventoryController){
        //only when there is at least one item in cart can this method be triggered

        int currentPage = 1;
        int totalPages = (int) Math.ceil((double) (shoppingCart.getSize()) / Constant.CART_PAGE_SIZE);

        Customer customer = (Customer)userController.getCurrentUser();

        Boundary.printOrderSummary(shoppingCart, currentPage, customer);
        Boundary.printCheckOutMenu(currentPage, totalPages);

        while (true){
            String input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }

            switch (input) {
                case ">" -> {
                    if (currentPage < totalPages) {
                        Boundary.printOrderSummary(shoppingCart, ++currentPage, customer);
                        Boundary.printCheckOutMenu(currentPage, totalPages);
                    } else {
                        Boundary.alertLastPage();
                    }
                }
                case "<" -> {
                    if (currentPage > 1) {
                        Boundary.printOrderSummary(shoppingCart, --currentPage, customer);
                        Boundary.printCheckOutMenu(currentPage, totalPages);
                    } else {
                        Boundary.alertFirstPage();
                    }
                }
                case "1" -> { //checkout
                    int result = checkOut(userController, inventoryController);
                    switch (result) {
                        case Constant.CODE_LOGOUT -> {
                            return Constant.CODE_LOGOUT;
                        }
                        case Constant.OPERATION_SUCCESS -> {
                            Boundary.printOperationSuccess(Constant.OP_TYPE_CHECKOUT);
                            return Constant.OPERATION_SUCCESS;
                        }
                        case Constant.OPERATION_FAIL -> {
                            Boundary.alertNotEnoughCredit();
                            return Constant.CODE_BACK;
                        }
                        case Constant.OPERATION_CANCEL, Constant.CODE_BACK -> {
                            Boundary.printOperationCancel(Constant.OP_TYPE_CHECKOUT);
                            Boundary.printOrderSummary(shoppingCart, currentPage, customer);
                            Boundary.printCheckOutMenu(currentPage, totalPages);
                        }
                    }
                }
                case "0" -> {
                    return Constant.CODE_BACK;
                }
                default -> {
                    Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                }
            }
        }
    }

    /**
     * Control the process of checking out.
     *
     * @param userController                    Controller that manages users.
     * @param inventoryController               Controller that manages inventory.
     * @return                                  A code representing the operation's result.
     */
    private int checkOut(UserControl userController, InventoryControl inventoryController){
        //get total price according to membership
        Customer customer = (Customer) userController.getCurrentUser();
        double totalPrice = customer.isMember() ? shoppingCart.getTotalMemberPrice() : shoppingCart.getTotalPrice();
        double currentCredit = customer.getCredits();

        // not enough credit
        if (totalPrice > currentCredit) {
            return Constant.OPERATION_FAIL;
        }

        //confirmation
        Order newOrder = new Order(shoppingCart.getTotalPrice(), shoppingCart.getTotalMemberPrice(),
                currentCredit, currentCredit - totalPrice, customer.isMember());

        Boundary.printOrderBrief(newOrder);
        boolean valid = false;
        String input;
        while (!valid) {
            Boundary.promptConfirm(Constant.OP_TYPE_CHECKOUT);
            input = Input.acceptStringInput();

            if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                return Constant.CODE_LOGOUT;
            }
            if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                return Constant.CODE_BACK;
            }
            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_NO)) {
                return Constant.OPERATION_CANCEL;
            }
            if (input.equalsIgnoreCase(Constant.STRING_CONFIRM_YES)) {
                valid = true;
            } else {
                Boundary.alertUnformattedInput(Constant.FIELD_CONFIRM);
            }
        }

        // update product information
        inventoryController.updateQuantityAfterCheckout(shoppingCart.getItemList());
        shoppingCart.clear();

//        customer.setCredits();
        userController.addOrder(customer, newOrder);

        //update customer information
        userController.updateCustomerInfoAfterCheckout(currentCredit - totalPrice, newOrder);

        return Constant.OPERATION_SUCCESS;
    }

    /**
     * Control the process of viewing the shopping cart's content.
     *
     * @param userController                    Controller that manages users.
     * @param inventoryController               Controller that manages inventory.
     * @return                                  A code representing the operation's result.
     */
    public int viewShoppingCart(UserControl userController, InventoryControl inventoryController) {

        if (shoppingCart.getSize() > 0) {

            int currentPage = 1;
            int totalPages = (int) Math.ceil((double) (shoppingCart.getSize()) / Constant.CART_PAGE_SIZE);

            Customer customer = (Customer) userController.getCurrentUser();
            boolean isMember = customer.isMember();
            Boundary.printShoppingCart(shoppingCart, currentPage, isMember);
            Boundary.printShoppingCartMenu(currentPage, totalPages);

            while (true) {
                String input = Input.acceptStringInput();

                if (input.equalsIgnoreCase(Constant.STRING_BACK)) {
                    return Constant.CODE_BACK;
                }

                if (input.equalsIgnoreCase(Constant.STRING_LOGOUT)) {
                    return Constant.CODE_LOGOUT;
                }

                switch (input) {
                    case ">" -> {
                        if (currentPage < totalPages) {
                            Boundary.printShoppingCart(shoppingCart, ++currentPage, isMember);
                            Boundary.printShoppingCartMenu(currentPage, totalPages);
                        } else {
                            Boundary.alertLastPage();
                        }
                    }
                    case "<" -> {
                        if (currentPage > 1) {
                            Boundary.printShoppingCart(shoppingCart, --currentPage, isMember);
                            Boundary.printShoppingCartMenu(currentPage, totalPages);
                        } else {
                            Boundary.alertFirstPage();
                        }
                    }
                    case "1" -> { //checkout
                        int result = viewOrderSummary(userController, inventoryController);
                        switch (result) {
                            case Constant.CODE_LOGOUT -> {
                                return Constant.CODE_LOGOUT;
                            }
                            case Constant.CODE_BACK -> {
                                Boundary.printShoppingCart(shoppingCart, currentPage, isMember);
                                Boundary.printShoppingCartMenu(currentPage, totalPages);
                            }
                            case Constant.OPERATION_SUCCESS -> {
                                return Constant.CODE_BACK;
                            }
                        }
                    }
                    case "0" -> {
                        return Constant.CODE_BACK;
                    }
                    default -> {
                        Boundary.alertUnformattedInput(Constant.FIELD_OPTION);
                    }
                }
            }
        }
        else {
            Boundary.alertEmptyCart();
            return Constant.CODE_BACK;
        }

    }

}
