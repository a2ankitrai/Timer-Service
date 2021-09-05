# Timer-Service
Timer Service to create timers that will trigger webhook urls when the timer expires.

The service exposes two api endpoint accepting requests as mentioned in the requirement:
- Create Timers (POST "/timers")
- Get Timer Info (GET "/timer")

The service also contains a scheduling job that runs every 1 second and finds out all the timers whose duration has ended. The scheduler than triggers the REST API calls to webhook along with the ID that was supplied while creating the timer.

## Tech Specs:
- Java based rest service with Spring boot framework.
- MongoDB as database for persistence.
- maven as build tool.

### To run the service:
- Mongo DB instance should be configured to run at localhost on port 27017
- Start the service using command `mvn spring-boot:run`. The Service will get started on http://localhost:8080