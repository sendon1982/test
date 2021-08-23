# How to run the project

Run `mvn clean package` to build the jar file then run `java -jar target/demo-0.0.1-SNAPSHOT.jar`

# Access swagger doc
After running the jar, you can access it swagger doc at `http://localhost:8080/swagger-ui.html`

You can use curl command or use the swagger doc URL above to register a court

# Using CURL command
```
curl -X 'POST' \
  'http://localhost:8080/courts/register' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "player": "Jason",
  "date": "2021-08-12",
  "courtNumber": "COURT1"
}'
```

Response looks like this:

```
{
  "player": "Jason",
  "date": "2021-08-12",
  "courtNumber": "COURT1",
  "availableSpot": 3
}
```

After booking 4 times for the same court on the same date, console will print a message to notify players that court is ready

After booking 12 times for one day (different courts), system will throw exception to indicate no more bookings for this date

