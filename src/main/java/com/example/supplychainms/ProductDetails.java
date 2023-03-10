package com.example.supplychainms;

import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class ProductDetails {


    public TableView<Product> productTable;   // tableView 's object  // why <product> type

  // created table to display
    public Pane getAllProducts(){
        // column names
        TableColumn id = new TableColumn("Id");  // column name
        id.setCellValueFactory(new PropertyValueFactory<>("id"));  // values derive from this id --> from class Product

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

//static procduct details
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(1, "asus",89999));
//        data.add(new Product(2, "lenovo",69999));

        ObservableList<Product> products = Product.getAllProducts(); //calling static fun from class Product  // to put data         // why <product> type

        productTable = new TableView<>();  // instanciated
//        productTable.setItems(data);
        productTable.setItems(products);
        productTable.getColumns().addAll(id,name,price);
        productTable.setMinSize(SupplyChain.width, SupplyChain.height);//full body
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);// to remove extra vacant column

        Pane tablePane = new Pane();
        tablePane.setStyle("-fx-background-color: #C0C0C0");
        tablePane.setMinSize(SupplyChain.width, SupplyChain.height);
        tablePane.getChildren().add(productTable);
        return tablePane;
    }

    // copied from just above
    public Pane getProductsByName(String productName){ //name change
        TableColumn id = new TableColumn("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name = new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

//static procduct details
//        ObservableList<Product> data = FXCollections.observableArrayList();
//        data.add(new Product(1, "asus",89999));
//        data.add(new Product(2, "lenovo",69999));

        ObservableList<Product> products = Product.getProductsByName(productName);  // 2nd change from above copied

        productTable = new TableView<>();
//        productTable.setItems(data);
        productTable.setItems(products);
        productTable.getColumns().addAll(id,name,price);
        productTable.setMinSize(SupplyChain.width, SupplyChain.height);//full body
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);// to remove extra vacant column

        Pane tablePane = new Pane();
        tablePane.setStyle("-fx-background-color: #C0C0C0");
        tablePane.setMinSize(SupplyChain.width, SupplyChain.height);
        tablePane.getChildren().add(productTable);
        return tablePane;
    }

    public Product getSelectedProduct(){ // to return product which was selected to buy now  --> will call from buynow button setOnAction block
        try{
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();  // special
            return selectedProduct;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
