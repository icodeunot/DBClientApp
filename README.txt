Title c195 DBClientApp - Scheduling Application
Purpose: Connect to outside database and apply CRUD actions

Author: John Salazar
Student ID: 000650269
Date: 10/30/2023

IDE: IntelliJ IDEA Community Edition 2022.2.3
JDK version: Open Oracle 17.0.8
JavaFX Version: JavaFX.SDK.17.0.8
MySQL Connector Driver Version: mysql-connector-java-8.0.28

How to run the program:
Build from the Main.java page and use the following credentials for access:
Username: test
Password: test
or
Username: admin
Password: admin
Enter the credentials and hit submit. If the credentials are invalid the user will be alerted.
Upon successful entry, the user will be alerted to any appointment within 15 minutes, then be shown the Main Menu.

Main Menu:
The main screen holds both the Customers table and the Appointments table. Add either by clicking the "Add" button beneath the respective table.
Click on a specific customer or appointment, and then click the update page for the appropriate table. To delete an appointment, click on an appointment and press delete.
To delete a customer, click on a customer, click delete, and if there are any existing appointments for the customer, you will be given the option
to delete all of the appointments and the customer, or you can cancel and delete them individually. Click on the reports button to view the reports page.
Finally, the radio buttons will show all appointments, appointments within the current week, and appointments within the current month.

Reports page:
The reports page presents the user with an option to select a contact and have all of their appointments be displayed.
Additional reports are available by a selected customer:
- By Type
- By Month
- By location

The Customer add and update forms:
The add customer page requires all data to be input before saving and provides comboboxes for country and first level division options.

The Appointment add and update forms:
The appointment forms contain logic that allows an appointment to start only after the current time if selected for the current day, and is within business hours.
The appointment start cannot be at the closing time and the ending appointment time cannot be the start time.

