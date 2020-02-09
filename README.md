# simpleMoneyTransfer

RestFul APIs for transferring money between accounts.

## TECH STACK

Programming Language : Java

Dependency Injection Framework : Guice

Server : Jetty

Rest FrameWork : RestEasy & Swagger

## Building The Application

mvn clean package

mvn clean install

## Running Tests

mvn clean verify

## Running the application

java -jar target/simpleMoneyTransfer-1.0-SNAPSHOT.jar

or

simply run main method under main package of simpleMoneyTransfer

## Using the Application

Navigate to : localhost:8080

This opens a swagger UI listing down the list of supported APIs.
APIs can be tested from this UI. 

Steps :
1. Click on the API bar which you want to execute.

2. Provide required details like request body or query params.

3. Click Execute


##API Details
1. POST /account/create

Sample Request Body : 
````
{
	"name": "shivendra",
	"accountNumber": 1001,
	"balance": 100.00,
	"currency": "INR",
	"emailId": "shivendra.singh3333@gmail.com",
	"mobileNo": "+91-7619567106"
}
````
Specs :

if accountNumber not provided then :

balance should be empty as well.
This will create a new account by generating a unique 
account number and 0 balance.

Sample Request Body :
````
{
	"name": "shivendra",
	"currency": "INR",
	"emailId": "shivendra.singh3333@gmail.com",
	"mobileNo": "+91-7619567106"
}
````

default values for other fields if not provided :
````
balance = 0.00
currency = USD
emailId = null
mobileNo = null
````

2. DELETE /account/delete

````
Query Param : accountNumber
````
3. GET /account​/getAccount

````
Query Param : accountNumber
````
4. PUT /account/updateAccount

Sample Request Body : 
````
{
	"emailId": "xxx",
	"mobileNo": "1111"
}
````
At least one of the fields emailId or mobileNo should be provided.

5. POST /transfer

Sample Request Body :
````
{
	"sourceAccountNumber": 1001,
	"destinationAccountNumber": 1002,
	"amount": 50.00
}
````
Successful Transfer Conditions :
````
1. The source and destination accounts exists
2. There is a sufficient balance on the source account
3. The currency of the transfer, of the source and destination accounts are identical
````