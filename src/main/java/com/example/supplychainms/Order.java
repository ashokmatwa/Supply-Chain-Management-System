package com.example.supplychainms;

public class Order {
    public static boolean placeOrder(String customerEmail, Product product){

        DatabaseConnection databaseConnection = new DatabaseConnection();  // instanciated
        String query = String.format("INSERT INTO orders (customer_id, product_id) VALUES ((SELECT customer_id FROM CUSTOMER WHERE email = '%s'), %s)",customerEmail, product.getId());
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);  // calling function database class
        }catch (Exception e){
            e.printStackTrace();
        }

        return rowCount!=0; // if row = 0 --> no row has been affected there
        // o--> order not placed  1 --> order placed
    }
}
