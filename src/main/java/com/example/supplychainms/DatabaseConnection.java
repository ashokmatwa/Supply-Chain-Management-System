package com.example.supplychainms;

import java.sql.*;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.Statement;


public class DatabaseConnection {
    private static final String databaseurl = "jdbc:mysql://localhost:3306/supply_chain"; // jdbc connector
    private static final String userName = "root";
    private static final String password = "epicC@der2022";

    //class -- statement , connection , drivermanager

    //drivermanager --> helps to intercat with different drivers
    //takes the driver and make the connection first to database

    //connection-->it has an object which is connected to database--> do different things

    //statement --> from connection to satement to execute the queries
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

    //return table
    public ResultSet getQueryTable(String query){
        Statement statement = getStatement();
        try{
            return  statement.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

//copied from just above --> change return type and name
    public int  executeUpdateQuery(String query){  // help in execute uodate and insert queries to database --> called from order class
        Statement statement = getStatement();
        try{
            return  statement.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ResultSet rs = databaseConnection.getQueryTable("SELECT email,first_name FROM customer");
        try{
            while(rs.next()){
                System.out.println(rs.getString("email")+ " " + rs.getString("first_name"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}



