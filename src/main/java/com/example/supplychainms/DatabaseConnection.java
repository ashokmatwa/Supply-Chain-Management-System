package com.example.supplychainms;

import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;


public class DatabaseConnection {
    private static final String databaseurl = "jdbc:mysql://localhost:3360/supply_chain";
    private static final String userName = "root";
    private static final String password = "epicC@der2022";

    public Statement getStatement(){
        Statement statement = null;
        Connection conn;
        try{
            conn = DriverManager.getConnection(databaseurl,userName,password);
            statement = conn.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return statement;
    }

    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return  statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ResultSet rs = databaseConnection.getQueryTable("SELECT email,first_name FROM CUSTOMER");
        try{
            while(rs.next()){
                System.out.println(rs.getString("email")+ " " + rs.getString("first_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



