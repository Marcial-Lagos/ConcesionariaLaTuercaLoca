package cl.pixelandbean.service;

public class AppContext {
    private final UserService userService;
    private final ProductService productService;

    public AppContext() {
        this.userService = new UserService();
        this.productService = new ProductService();
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }
}
