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

Using this API, a user can create a restaurant. He can choose to add list of menus now, or via the POST /menu

#### Request body
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|name    | String | Name of the restaurant | Yes |
| address     | String      |   List of values for the key | Yes |
| minOrder | Double |  Minimum order for the restaurant | No|
| rating | Double |  Rating of the restaurant | No|


#### Sample input
```json
POST http://127.0.0.1:8080/restaurant
{
    "name": "Raylene Continental House",
    "address": "New York",
    "minOrder": 20,
    "rating": 4.7
}
```
#### Sample output
```json
 HttpStatus: 201 Created
{
    "id": "f41924cf-43a3-4525-a694-76a0435f5853",
    "name": "Raylene Continental House",
    "address": "New York",
    "minOrder": 20,
    "rating": 4.7
}
```

### 3.2 POST /restaurant/{id}

Using this API, a user can create a menu for a restaurant. 

#### Request body
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|restaurantId    | String | Id of the restaurant | Yes |
| mealType     | String      |   Meal type of this meal | Yes |
| menuItems | List<MenuItem> | List of menu Items. The menu item object is described below | No|

#### MenuItem object
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|itemName    | String | Name of menu item | Yes |
|itemPrice    | String | Price of menu item | Yes |
|itemDescription    | String | Description of menu item | No |

```json
#### Sample input
POST http://127.0.0.1:8080/menu/
{
    {
    "restaurantId":"f41924cf-43a3-4525-a694-76a0435f5853",
    "mealType":"Dinner",
    "menuItems":[
        {
            "itemName":"Pepperoni Pizza",
            "itemPrice":"2",
            "itemDescription":"Made with the finest pork in town"
        },
        {
            "itemName":"Kolkata Rolls",
            "itemPrice":"3",
            "itemDescription":"One of the finest dishes straight from India"
        },
        {
            "itemName":"Mac and Cheese",
            "itemPrice":"4",
            "itemDescription":"Go back to the basics with this chef special"
        }
     ]
    }
}
```

#### Sample output

```json
 HttpStatus: 201 Created
{
    "menuId": "41b6f704-63e1-4815-8ae2-9eb015bc143b",
    "menuItems": [
        {
            "menuItemId": "202663b1-472f-4258-b7a7-83116c6bd4a2",
            "itemName": "Pepperoni Pizza",
            "itemPrice": "2",
            "itemDescription": "Made with the finest pork in town"
        },
        {
            "menuItemId": "46c25ed8-1360-4828-aa99-18417ce7eab2",
            "itemName": "Kolkata Rolls",
            "itemPrice": "3",
            "itemDescription": "One of the finest dishes straight from India"
        },
        {
            "menuItemId": "e6dfde0b-cc54-4200-b3ca-9e0eefe25a09",
            "itemName": "Mac and Cheese",
            "itemPrice": "4",
            "itemDescription": "Go back to the basics with this chef special"
        }
    ],
    "restaurantId": "f41924cf-43a3-4525-a694-76a0435f5853",
    "mealType": "DINNER"
}
```

### 3.3 GET /restaurant?type=

This API returns a complete view of the restaurant with all its menus. If the query parameter, 'type' is set, then it will return a menu of that mealType only.

#### Sample input
```json
GET http://127.0.0.1:8080/restaurant/f41924cf-43a3-4525-a694-76a0435f5853?type=dinner
```
#### Sample output
```json
HttpStatus: 200 OK
{
    "id": "f41924cf-43a3-4525-a694-76a0435f5853",
    "name": "Raylene Continental House",
    "address": "New York",
    "minOrder": 20,
    "rating": 4.7,
    "menu": [
        {
            "menuId": "41b6f704-63e1-4815-8ae2-9eb015bc143b",
            "menuItems": [
                {
                    "menuItemId": "202663b1-472f-4258-b7a7-83116c6bd4a2",
                    "itemName": "Pepperoni Pizza",
		    "itemPrice": "2",
		    "itemDescription": "Made with the finest pork in town"
                },
                {
                    "menuItemId": "46c25ed8-1360-4828-aa99-18417ce7eab2",
                    "itemName": "Kolkata Rolls",
                    "itemPrice": "3",
                    "itemDescription": "One of the finest dishes straight from India"
                },
                {
                    "menuItemId": "e6dfde0b-cc54-4200-b3ca-9e0eefe25a09",
                    "itemName": "Mac and Cheese",
                    "itemPrice": "4",
                    "itemDescription": "Go back to the basics with this chef special"
                }
            ],
            "restaurantId": "f41924cf-43a3-4525-a694-76a0435f5853",
            "mealType": "DINNER"
        }
    ]
}
```

### 3.4 POST /menu/{id}/item

This API adds a menu item to an existing menu with menuId as the one in the path parameter. Returns the complete updated menu.

#### Request body
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|itemName    | String | Name of menu item | Yes |
|itemPrice    | String | Price of menu item | Yes |
|itemDescription    | String | Description of menu item | No |

#### Sample input
```json
HttpStatus: 201 Created
POST http://127.0.0.1:8080/menu/41b6f704-63e1-4815-8ae2-9eb015bc143b/item
{
            "itemName": "The Baconator",
            "itemPrice": "7.5",
            "itemDescription": "One of this week's specials"
}
```
#### Sample output
```json
{
    "menuId": "41b6f704-63e1-4815-8ae2-9eb015bc143b",
    "menuItems": [
        {
            "menuItemId": "202663b1-472f-4258-b7a7-83116c6bd4a2",
            "itemName": "Pepperoni Pizza",
            "itemPrice": "2",
            "itemDescription": "Made with the finest pork in town"
        },
        {
            "menuItemId": "46c25ed8-1360-4828-aa99-18417ce7eab2",
            "itemName": "Kolkata Rolls",
            "itemPrice": "3",
            "itemDescription": "One of the finest dishes straight from India"
        },
        {
            "menuItemId": "e6dfde0b-cc54-4200-b3ca-9e0eefe25a09",
            "itemName": "Mac and Cheese",
            "itemPrice": "4",
            "itemDescription": "Go back to the basics with this chef special"
        },
        {
            "menuItemId": "51eb721f-e0ac-424c-b934-7995c635ab3d",
            "itemName": "The Baconator",
            "itemPrice": "7.5",
            "itemDescription": "One of this week's specials"
        }
    ],
    "restaurantId": "f41924cf-43a3-4525-a694-76a0435f5853",
    "mealType": "DINNER"
}
```

### 3.5 DELETE /menu/item

This API deletes a menu item from a menu.

#### Request body
| Field        | Type           | Description  |  Required  |
| ------------- |:-------------:| :-------------| :-------------|
|menuId    | String | Id of menu | Yes |
|menuItemId    | String | Id of menu item | Yes |

#### Sample input
```json
DELETE http://127.0.0.1:8080/menu/item
{
	"menuId":"e8dfc246-ef55-4b29-a20c-f3e74dd3391f",
	"menuItemId":"81fbce19-53ba-4c7c-8e4f-82ff1d989dd6"
}
```
### Sample output
```json
HttpStatus: 200 OK
{
    "status": "0",
    "message": "Menu Item deleted successfully"
}
```

### 3.6 DELETE /menu/{id}

This API deletes a menu for a restaurant. Updates mealType map for restaurant.

#### Sample input
```json
DELETE http://127.0.0.1:8080/menu/41b6f704-63e1-4815-8ae2-9eb015bc143b
{
	"menuId":"e8dfc246-ef55-4b29-a20c-f3e74dd3391f",
	"menuItemId":"81fbce19-53ba-4c7c-8e4f-82ff1d989dd6"
}
```
### Sample output
```json
HttpStatus: 200 OK
{
    "status": "0",
    "message": "Menu deleted successfully"
}
```

### 3.7 DELETE /restaurant/{id}

This API deletes a restaurant and all its linked menus.

#### Sample input
```json
DELETE http://127.0.0.1:8080/restaurant/f41924cf-43a3-4525-a694-76a0435f5853
```
### Sample output
```json
HttpStatus: 200 OK
{
    "status": "0",
    "message": "Restaurant deleted successfully"
}
```

