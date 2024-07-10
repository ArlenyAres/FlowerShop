# Flower Shop Management System

## Description

The Flower Shop Management System is an application that allows managing flower shops, including their inventory, sales, and earnings. It uses MongoDB as the database to store and retrieve information about flower shops and their products.

## Features

- Create and manage multiple flower shops.
- Add, remove, and display products in the inventory of each flower shop.
- Perform and manage purchases.
- Display purchase history and calculate total earnings.
- Save and retrieve data from a MongoDB database.

## Requirements

- Java 8 or higher
- MongoDB
- Third-party libraries:
  - MongoDB Driver
  - SLF4J (Simple Logging Facade for Java)

## Project Structure

The project is organized into the following packages:

- `Database`: Handling MongoDB connection.
- `Exception`: Custom exceptions.
- `Model`: Data models for flower shops, products, and purchases.
- `Services`: Services for handling data input and business logic.
- `utils`: Various utilities, such as reading configuration files.

## Configuration

### MongoDB Configuration

1. Install and configure MongoDB on your system.
2. Create a `mongodb.properties` file at the root of the project with the following content:

```
host=localhost
port=27017
user=your_username
password=your_password
```

### Dependencies

Make sure to include the following dependencies in your `pom.xml` file if you're using Maven:

```
xml
<dependencies>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.2.3</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.30</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-simple</artifactId>
        <version>1.7.30</version>
    </dependency>
</dependencies>
```

### Usage

Running the Project
Clone the project repository.
Configure MongoDB and the mongodb.properties file as mentioned above.
Import the project into your favorite IDE.
Run the Main class.
Functionality
Upon starting the application, you will be presented with a menu with the following options:

Create flower shop.
Add product to inventory.
Remove product from inventory.
Show inventory.
Show products by quantity.
Flower shop value.
Create purchase ticket.
Show purchase history.
Show total earnings.
Exit.
Select an option by entering the corresponding number and follow the on-screen instructions.

### development team

## Marina Aguiar Alecrim
Email: [your.email@example.com]

GitHub: [ https://github.com/marinaaguiar]

Likendin:  https://www.linkedin.com/in/marina-aguiar]


## Arleny Medina Prince
Email: [Arleny.ares@gmail.com]

GitHub: [https://github.com/ArlenyAres]

Likendin: [www.linkedin.com/in/arleny-medina-prince]

Thank you for using the Flower Shop Management System!
