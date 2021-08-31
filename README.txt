Hello! This budgeting app has been created as a capstone project for Coding Nomad's Java fundamentals course.
It is a simple CLI application used track a monthly budget and includes some simple reporting tools.
The database setup was done using MySQL Workbench and I have included an annotation that contains the script
for setting up the schema in your own database. The database user/password can be changed by following this navigation:
budget_app.services.DatabaseConnector --> openConnection() method, change "connectionString".