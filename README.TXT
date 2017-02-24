To run the application, execute the command:
./gradlew run

or
./gradlew oneJar
java -jar build/libs/Tracker-standalone.jar server config/tracker.yml

For a UI, visit the web page:
http://localhost:8080/tracker/swagger

I recommend starting with the GET /title endpoint to get a title ID
After that, visit GET /title/{titleId}/subscription to get a player ID
The programatically interesting endpoint is making a payment at
PUT /player/{playerId}/subscription/{titleId}/payment but you should first
visit GET /player/{playerId}/subscription/{titleId}/payment to get a realistic
object.

The /title with an ID of 95950671-d62d-4103-8f73-0cf95ccc238b has *NO* players
