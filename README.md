Kata-Tennis-Springboot-API

For building and application you need
1. JDK 1.8 with JAVA_HOME and JRE_HOME mapped in environment variable
2. Gradle 7.3.3 or higher with GRADLE_HOME mapped in environemnt variable
3. Any latest eclipse that supports gradle project

Running application locally
1. Checkout from https://github.com/2022-DEV1-125/Tennis -> main branch
2. Import in eclipse as gradle project
3. On successful import right click on project -> Gradle -> refresh dependencies to download all libraries
4. Upon successful import of all libraries , run or debug Tennis.java as java application
5. Application will be started at http://localhost:9080/kata-tennis

Testing locally
1. after successful start of application, Testing can be done using junit or postman
2. For junit tetsing, right click TennisTest.java (with possible scenarios and test data) -> run or debug as junitTest
3. For postman testing please find the sample request and response below

Request:
POST http://localhost:9080/kata-tennis
Accept: applicaion/json
Content-Type: application/json
{
"point1":"b",
"point2":"B",
"point3":"b",
"point4":"a",
"point5":"a",
"point6":"a",
"advantagePoint":"a",
"finalPoint":"a"
}

Response:
{
    "status": "Success",
    "playerA_Score": "40",
    "playerB_Score": "40",
    "gameResult": "Player A won the game after Deuce"
}
