# lagom-persistent-read-side

> This is a simple lagom application which is used to create user and fetch the details of existing users.

Steps to follow to run this application:

- Open the terminal.
- Clone the project ```git clone git@github.com:DivyaDua/lagom-persistent-read-side.git```.
- ```cd lagom-persistent-read-side```.
- Execute ```sbt clean compile```.
- Run the cassandra on your localhost, since embedded cassandra is being disabled here.
- Run the application using ```sbt runAll```.

You can check which services are up by hitting ```http://localhost:8000/services```. There, you can see that along with cassandra and kafka, service named 'user_service' is also running. And you can check through cqlsh(cassandra query language shell) that keyspace named ```user``` is created having ```usertable```.

You can now hit the service using postman -

- To create a new user, hit the service with a POST call using URL - http://localhost:9000/user/create/:id/:name/:age.
  You can give id, name and age of your choice. You can check usertable is updated with the information.

- To fetch user details, hit the service with a GET call using URL - http://localhost:9000/user/details/id/:id.
  You can give the id for which you want to retrieve the details.

- And, to fetch user details using name, hit the service with a GET call using URL - http://localhost:9000/user/details/name/:name.
  You can give the name for which you want to retrieve the details.




