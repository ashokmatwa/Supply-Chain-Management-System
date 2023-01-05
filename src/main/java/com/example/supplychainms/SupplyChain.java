package com.example.supplychainms;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;

public class SupplyChain extends Application {

    public static final int width = 700, height = 600, headerBar = 50;

    Pane bodyPane = new Pane();
//    public static int bodyWidth, bodyHeight;

    Login login = new Login(); // login functionality creating login object to call function --> customerLogin present in Login class
    ProductDetails productDetails = new ProductDetails();

    Button globalLoginButton;
    Label customerEmailLabel = null;
    String customerEmail = null;

    Button globalSignUpButton;

    public TableView<Product> cartTable;
    Button addToCartButton;
    Button buyNowButton;
    Button myCartButton;
    Button logoutButton;
    ObservableList<Product> cartList;

    private GridPane headerBar(){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        //  gridPane.setStyle("-fx-background-colour: #C0C0C0");
//        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.setAlignment(Pos.CENTER); // to center

        TextField searchText = new TextField();
        // search by press enter instead of search button
//        searchText.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
//                    String productName=searchText.getText();
//                    bodyPane.getChildren().clear();
//                    bodyPane.getChildren().add(productDetails.getProductsByName(productName));
//                }
//            }
//        });

        Button searchButton = new Button("Search");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchText.getText();
                productDetails.getProductsByName(productName);  // calling a function from object of class ProductDetails
                //clear body and put this new pane in the body
                bodyPane.getChildren().clear();  // to remove all from table view and only display what has searched
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });

        globalLoginButton = new Button("Log In");
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() { // on clicking to this button login page will be displayed
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();    // clear everything
                bodyPane.getChildren().add(loginPage());  // here added loginpage to body pane
//                globalLoginButton.setDisable(true);// -->transfer to login button setOnAction
//                customerEmailLabel.setText("Welcome : "+ customerEmail);
            }
        });

        customerEmailLabel = new Label("Welcome");

//copy above loginbutton
        globalSignUpButton=new Button("Sign Up");
        globalSignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signupPage());
            }
        });


        gridPane.add(searchText,0,0);
        gridPane.add(searchButton,1,0);
        gridPane.add(globalLoginButton,2,0);
        gridPane.add(customerEmailLabel,3,0);

        gridPane.add(globalSignUpButton,4,0);

        return gridPane;
    }

    private GridPane signupPage(){

    }
    private GridPane loginPage(){
        GridPane gridPane = new GridPane();
        //set the size of gridpane equal to bodypane
        gridPane.setMinSize(bodyPane.getMinWidth(),bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
      //  gridPane.setStyle("-fx-background-colour: #C0C0C0");
        //gridPane.setStyle("-fx-background-color: #C0C0C0");
        gridPane.setAlignment(Pos.CENTER); // to center


        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");
       // Label messageLabel = new Label("I am Message");
        Label messageLabel = new Label("Enter Credentials");

        TextField emailTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordField.getText();
//                messageLabel.setText(email+" ** "+password);
//      login functionality
                if(login.customerLogin(email, password)){    // calling a function of login class passing two parameters

                    //getting name from database through database connection
                    DatabaseConnection databaseConnection=new DatabaseConnection();
                    String query=String.format("select first_name from customer where email = '%s'",email);
                    ResultSet rs = databaseConnection.getQueryTable(query);
                    try {
                        while(rs.next()) {
                            customerEmailLabel.setText("Welcome " + rs.getString("first_name"));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    messageLabel.setText("Login Successful");
                    customerEmail = email;
                    //globalLoginButton.setDisable(true);
                    globalLoginButton.setVisible(false);
                    globalSignUpButton.setVisible(false);
                    //customerEmailLabel.setText("Welcome : "+ customerEmail);
                    bodyPane.getChildren().clear();  // after login clear body
                    bodyPane.getChildren().add(productDetails.getAllProducts());// and then add all products to body
            // new button added
                    addToCartButton.setVisible(true);
                    buyNowButton.setVisible(true);
                    myCartButton.setVisible(true);
                    logoutButton.setVisible(true);
                }
                else{
                    messageLabel.setText("Login failed");
                }
            }
        });

        // x y coordinate
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailTextField,1,0);
        gridPane.add(passwordLabel,0,1);
        gridPane.add(passwordField,1,1);
        gridPane.add(loginButton,0,2);
        gridPane.add(messageLabel,1,2);

        return gridPane;
    }

// copy from headerBar
    private GridPane footerBar(){
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(),headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(20);
        //  gridPane.setStyle("-fx-background-colour: #C0C0C0");
//        gridPane.setStyle("-fx-background-color: #C0C0C0");

        gridPane.setAlignment(Pos.CENTER); // to center
        gridPane.setTranslateY(headerBar+height+5);

        addToCartButton = new Button("Add to Cart");
        buyNowButton = new Button("Buy Now");

//new button
        myCartButton=new Button("My cart");
        addToCartButton.setVisible(false);
        buyNowButton.setVisible(false);

        Label messageLabel = new Label(" ");

        cartList = FXCollections.observableArrayList();

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selectedProduct = productDetails.getSelectedProduct();   //calling function class Product Details which is selected by user for buy
                if(Order.placeOrder(customerEmail, selectedProduct)){  // calling function in order class
                    messageLabel.setText("Order Placed");
                }
                else{
                    messageLabel.setText("Order Failed");
                }
            }
        });


       // customerEmailLabel = new Label("Welcome");


        gridPane.add(addToCartButton,0,0);
        gridPane.add(buyNowButton,1,0);
        gridPane.add(messageLabel,2 ,0);

        return gridPane;
    }

    private Pane createContent(){
        Pane root = new Pane();

        root.setPrefSize(width,height+2*headerBar);

        bodyPane.setMinSize(width,height);
        bodyPane.setTranslateY(headerBar);

    //    bodyPane.setAlignment(Pos.CENTER); // to center

       // bodyPane.getChildren().addAll(loginPage()); //add loginpage to body
        bodyPane.getChildren().addAll(productDetails.getAllProducts());

        root.getChildren().addAll(headerBar(),bodyPane, footerBar()); //add body to root
        return root;
    }
    @Override
    public void start(Stage stage) throws IOException {
     //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Sabki Dukaan!!");
        stage.setScene(scene);
        stage.show();
//        stage.centerOnScreen();
//        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}


