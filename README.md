# smart-ac-exercise
Smart AC Portal

The project is a proof of concept for smart AC portal.
The backend is written in Java/Spring boot and front end in React.js.
The demo is deployed on Google App Engine and is viewable at https://citrusbytes-smart-ac.appspot.com/

#### REST API
You can see the REST API documentations here: https://citrusbytes-smart-ac.appspot.com/swagger-ui.html 

#### Build

To build the server code install maven and run:
```
mvn clean install
```
To build the front-end make sure you have npm installed and run 
the following commands.
```
cd src/main/web
npm run build 
``` 
#### Deploy
To push your changes to the server run
```
mvn appengine:deploy
```

#### Running Locally
To set up the database connection run this command for development:
```
gcloud beta emulators datastore start --host-port=localhost:8484
```
