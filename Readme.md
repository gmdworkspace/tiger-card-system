**Tiger card System**

An engine to calculate the total fare for the shuttles by a person.  

Run Integration Test

_Daily Cap Reached Problem:_

`./gradlew clean test --tests FareEngineApplicationIntegrationTest.shouldTestDailyCapReachedProblem -i`

_Weekly Cap Reached Problem:_

`./gradlew clean test --tests FareEngineApplicationIntegrationTest.shouldTestWeeklyCapReachedProblem -i`

_Run test in Docker(If machine doesn't have gradle installed)_

`docker build -t tigercard .`

`docker run -it tigercard sh`

`./gradlew clean test --tests FareEngineApplicationIntegrationTest -i`

_Run All tests_

`./gradlew clean test`

_Run build_

`./gradlew clean build`

This will check for lints and runs tests



