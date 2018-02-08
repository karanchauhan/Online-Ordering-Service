# Online-Ordering-Service
Backend for an online food ordering service made with Spring and DynamoDB

## Author : Karan Chauhan 

## 1. Introduction

A backend service has been implemented for an online food ordering service in Java with the Spring framework, using
DynamoDB as the persisitance layer. DynamoDB local was used to test the code, and has to be running locally for testing.
At server startup, it checks whether the tables required for computing, i.e., "Restaurant" and "Menu" exist or not
in the local dynamo db of the tester; if they don't it creates them. 
I have implemented the persistance layer with two databases, Restaurant and Menu. The MenuItem table was ignored because
I figured that a lot of db calls would have to be made for calls like DELETE /menu (all menuitem documents would have had to 
be deleted from the database) - an async delete could have been implemented, but has been omitted in the interest of time.

## 2. Directory Structure


```
.
main
├── java
│   └── com
│       └── zappos
│           └── onlineordering
│               ├── application
│               │   └── OnlineOrderingServiceApplication.java
│               ├── controller
│               │   └── RestaurantController.java
│               ├── model
│               │   ├── DeleteMenuItemRequest.java
│               │   ├── Menu.java
│               │   ├── MenuItem.java
│               │   ├── Restaurant.java
│               │   ├── RestaurantResponse.java
│               │   └── StatusResponse.java
│               ├── repository
│               │   ├── DynamoDBConfig.java
│               │   ├── MenuItemRepository.java
│               │   ├── MenuRepository.java
│               │   └── RestaurantRepository.java
│               ├── service
│               │   ├── RestaurantService.java
│               │   └── RestaurantServiceImpl.java
│               └── utils
│                   ├── Constants.java
│                   └── TableInit.java
└── resources
    └── application.properties


```


## 3. APIs

The project has implemented the following APIs for the food ordering service:

### 3.1 POST /restaurant

Using this API, a user can create a restaurant. He can choose to add list of menus now, or via the POST /restaurant/{id}

#### Request body
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|name    | String | Name of the restaurant | Yes |
| address     | String      |   List of values for the key | Yes |
| minOrder | Double |  Minimum order for the restaurant | No|
| rating | Double |  Rating of the restaurant | No|


#### Sample input
{
    "name": "Raylene Continental House",
    "address": "New York",
    "minOrder": 20,
    "rating": 4.7
}
#### Sample output
 HttpStatus: 201 Created
{
    "id": "f41924cf-43a3-4525-a694-76a0435f5853",
    "name": "Raylene Continental House",
    "address": "New York",
    "minOrder": 20,
    "rating": 4.7
}
   
