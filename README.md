How to Run the Project

Requirements:
Java Development Kit (JDK) 8 or later
Maven
Database (e.g., MySQL)

Database Setup:
Create a database named employwise (or a name of your choice).
Configure database connection details in application.properties file.

3.API Documentation: 

Add Employee:
URL: POST /employees/add
Input JSON Structure:
{
  "employeeName": "John Doe",
  "phoneNumber": "123-456-7890",
  "email": "john.doe@example.com",
  "reportsTo": 1,
  "profileImage": "path/to/image.jpg"
}

Get All Employees:
URL: GET /employees/getAll

Delete Employee:
URL: DELETE /employees/deleteId?id={employeeId}

Update Employee:
URL: PUT /employees/update/{id}
Input JSON Structure:
{
  "employeeName": "Updated Name",
  "phoneNumber": "987-654-3210",
  "email": "updated.email@example.com",
  "reportsTo": 2,
  "profileImage": "path/to/updated_image.jpg"
}

Get Nth Level Manager:
URL: GET /employees/nthLevelManager?employeeId={employeeId}&level={n}

Get Paging Employees:
URL: GET /employees/getPagingEmployees?pageNumber={pageNumber}&pageSize={pageSize}&sortBy={sortBy}
