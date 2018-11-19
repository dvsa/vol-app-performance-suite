# Description
 Performance test for the register new user journey.

# Prerequisites
- Gradle

# How to Build
`gradle clean build -x test` 

# Running Test 
 
 In a terminal window, powershell window or cmd paste the below command
 
`gradle test -Denv=e.g.{da} -Dusers=e.g.{30} -DrampUp=e.g.2 -Dduration=e.g{10}`

 Please remove the braces when running.
 
## Reports
 - Reports can be found in build/gatling-results/