package com.mycompany.ems;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class conn1 {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public conn1() {
        try {
            // Connect to MongoDB
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("employeemanagementsystem"); // Replace with your MongoDB database name
            System.out.println("Connected to MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("MongoDB connection closed");
        }
    }

    // Method to retrieve all employee IDs
    public List<String> getEmployeeIds() {
        List<String> employeeIds = new ArrayList<>();
        MongoCollection<Document> collection = database.getCollection("employee");
        MongoCursor<Document> cursor = collection.find().projection(new Document("empId", 1)).iterator();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            employeeIds.add(document.getString("empId"));
        }

        return employeeIds;
    }

    // Method to retrieve all employee data
    public Iterable<Document> getEmployeeData() {
        MongoCollection<Document> collection = database.getCollection("employee");
        return collection.find();
    }

    // Method to retrieve employee by ID
    public Iterable<Document> getEmployeeById(String empId) {
        MongoCollection<Document> collection = database.getCollection("employee");
        Document query = new Document("empId", empId);
        return collection.find(query);
    }

    // Method to update employee (replace with your actual implementation)
   public void updateEmployee(String empId, String newName, String newSalary, String newAddress, String newPhone, String newEmail) {
    // Assuming you have a MongoDB collection named "employee"
    MongoCollection<Document> collection = database.getCollection("employee");

    // Search for the employee with the given empId
    Document query = new Document("empId", empId);
    Document update = new Document("$set", new Document("name", newName)
            .append("salary", newSalary)
            .append("address", newAddress)
            .append("phone", newPhone)
            .append("email", newEmail));

    // Update the document in the collection
    collection.updateOne(query, update);

    System.out.println("Employee with ID " + empId + " updated successfully.");
}
    // Method to delete employee by ID
    public void deleteEmployeeById(String empId) {
        MongoCollection<Document> collection = database.getCollection("employee");
        Document query = new Document("empId", empId);
        collection.deleteOne(query);
        System.out.println("Employee deleted successfully");
    }
}
