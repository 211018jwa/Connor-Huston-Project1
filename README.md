# Connor-Huston-Project1
A reimbursement that focuses on employee reimbursements.

# Technologies Used

* Javalin
* Mockito
* Selenium
* JavaScript
* JUnit
* JDBC
* AJAX
* CSS
* HTML
* Java
* SQL

# Features

List of features that are ready:

* Users are ready to sign in and out as employees or finance manager
* Employees can add a reimbursement by filling out the following steps provided on the homepage.
* Finance Managers can see employee's reimbursements and can either approve or deny the reimbursement by clicking on the approve or deny button.
* Employees can later see the results left by the finance manager to see if their reimburesement has been approved or denied. Or if their reimburesement has yet to have a status.

List of to-do's to improve this project:

* Images need to improve the sizes so both employee and finance manager can see the actual image.
* Some selenium testing needs to be work when adding a new reimbursement or approving or denying a reimbursement.
* Add a sign-up page for the front-end, even though the programmer can do that through the backend.

# Getting Started

* Git clone the following command line so it would look like this:
![alt text](![image](https://user-images.githubusercontent.com/92756711/147126582-3d910903-b287-4498-9ac6-60fca8b418c4.png)

# Usage

* Once you've successfully clone the repository, open up your Eclipse/Springtool suite to import the progress through the backend.
* Then make some changes in the JDBCUtility like the username and password to your postgres server that you use in DBeaver so that you'll be able to connect to the tables you made in that postgres server.
* As for the frontend, make sure everything is running in VSCode before running the application. As well as connecting to your Chromedriver instead of the driver I'm using.
* Then once everthing is setted up on your end, start the application on the backend, open up postman and make sure every endpoint is up and running. If not, go into the backend and see if you're missing some requirements, then run it again and see if everything is working.
* Once you have everything working on the backend, go to the frontend on the login.html page and open with live server.
* There you will change the name of of the http server host to "localhost:8080/" in order to cennect to the live server properly.
* On the login page you should be able to login as an employee or a finance manager. If you put in an incorrect username and/or password, you should see an error message.
* If you login as an employee, you should be redirected to the employee homepage, there you can add a reimbursement in the information on the left.
* If you login as a finance manager, you should be redirected to finance manager homepage, there you can view repositories submitted by employees, then you can either approve or deny them.
* On both pages, there should be a logout button, if it's clicked, it will take you back to the login page.

