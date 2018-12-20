# Description
 Performance test for the register new user journey.

# Prerequisites
- Gradle

# How to Build
`gradle clean build -x test` 

## Running Create Application Script 
 
 In a terminal window, powershell window or cmd paste the below command
 
`gradle test -Denv={da} -Dusers={2} -DrampUp={0} -Dduration={0}`


 Please remove the braces when running.
 
 If the number of users is greater than 20, the harness will use an SQL statement to get users
 from the DB. You'll need to pass in the following system prop `-DdbUsername= and -DbPassword=` in addition to the other 
 properties
 
## Running Register User Script
 
  In a terminal window, powershell window or cmd paste the below command
  
 `gradle registerUser -Denv={da} -Dusers={2} -DrampUp={0} -Dduration={0}`


## Running External Search Script
 
  In a terminal window, powershell window or cmd paste the below command
  
 `gradle searchOperator -Denv={da} -Dusers={2} -DrampUp={0} -Dduration={0}`
 
## Reports
 - Reports can be found in build/gatling-results/