# Tool Rental Management System (TRMS)

### Reference Documentation

This is a project to handle tool rental management system based on some functional requirements not documented here. 

This project is a Springboot application with a single API to handle tool rental checkout process.

For the simplicity of the project, there is no backend database but most of the data is stored in-memory in form of key-value maps.

### Instruction to run the application
1. Download the project and setup as a Springboot application with Maven as the build tool

2. Build the project with Maven (mvn clean install)

3. Run the application as a Springboot application by simply clicking TrmsApplication.java and run as a java application.

Here is the sample request and response for checkout request done using a REST client like PostMan.

#### Sample Request - 
```
{
"customerId": "1",
"storeAssociateId": "1",
"storeId": "1",
"toolCode": "LADW",
"rentalDayCount": 5,
"discountPercent": 10,
"checkoutDate": "01/29/24"
}
```
#### Sample Response - 
```
{
"toolCode": "LADW",
"toolType": "Ladder",
"toolBrand": "Werner",
"rentalDays": 5,
"checkoutDate": "01/29/24",
"dueDate": "02/03/24",
"dailyRentalCharge": "$1.99",
"chargeDays": 5,
"preDiscountCharge": "$9.95",
"discountPercent": "10%",
"discountAmount": "$1.00",
"finalCharge": "$8.95"
}
```
Here is the sample data for Tools that is supported in the app.
```
Tool Code, Tool Type, Tool Brand
CHNS, Chainsaw, Stihl
LADW, Ladder, Werner
JAKD, Jackhammer, DeWalt
JAKR, Jackhammer, Ridgid
```
Here is the sample data for Prices that is supported in the app.
```
Tool Type, Daily Charge, Holiday Charge, Weekend Charge, Weekday Charge
Chainsaw, $1.49, Yes, No, Yes
Ladder, $1.49, No, Yes, Yes
Jackhammer, $2.99, No, No, Yes
```
Please refer the functional specification for holidays. 

Note: In the interest of time there are not many tests added in the project. However there are some unit tests for the main service. 
