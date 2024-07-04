# Drones Assignment

This project is a Spring Boot application for managing drones and their loads. It includes functionalities such as registering drones, loading medications onto drones, checking drone battery levels, retrieving loaded medications for a specific drone, and getting available drones.


## Prerequisites

- Java 17 or later

## Getting Started

### Clone the Repository 

git clone https://github.com/evans-hub/Ajua-Assignment.git

## Build the Project
- mvn clean install

## Run the Project
- mvn spring-boot:run

The application will start and be accessible at [http://localhost:8080](http://localhost:8080).

- JDBC URL: `jdbc:h2:mem:DroneDB`
- Username: `langat`
- Password: `1234`

## Running Tests

To run the tests, use the following command:
- mvn test


## API Endpoints

### Register a Drone

- URL: `/api/drones/registerDrone`
- Method: `POST`
- Request Body (JSON):

```json
{
  "serialNumber": "DRONE123",
  "model": "LIGHTWEIGHT",
  "weightLimit": 100,
  "batteryCapacity": 80,
  "state": "IDLE"
}
```

### Load Medications onto a Drone
- URL: /api/drones/load/{droneId}
- Method: POST
- Request Body (JSON):
```json
[
  {
    "name": "Medication1",
    "weight": 10,
    "code": "MED1",
    "image": ""https://testing/images/med1.png""
  },
  {
    "name": "Medication2",
    "weight": 20,
    "code": "MED2",
    "image": ""https://testing/images/med2.png""
  }
]
```
### Get Loaded Medications for a Drone
- URL: /api/drones/{droneId}/medications
- Method: GET
### Get Available Drones
- URL: /api/drones/available
- Method: GET
### Check Drone Battery Level
- URL: /api/drones/{droneId}/battery
- Method: GET
## Logging
- The application uses SLF4J for logging. Logs can be found in the console output and is saved to in memory database.

