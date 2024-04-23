# Description
 Performance test for the register new user journey.

# Prerequisites
- Gradle

# How to Build
`gradle clean build -x test` 

## Running Create Application Script 
 
 In a terminal window, powershell window or cmd paste the below command

 These are the environments that are currently supported
 `env = qa/da/...`
`site = ss/internal`
 `typeOfTest = load,soak,stress`
 
`gradle createApplication -Denv=int -Dsite=ss -Dusers=40 -DrampUp=0 -Dduration=0 -DtypeOfTest=load  -DapplicationType='

 Please remove the braces when running.
 
 If the number of users is greater than 20, the harness will use an SQL statement to get users
 from the DB. You'll need to pass in the following system prop `-DdbUsername= and -DbPassword=` in addition to the other 
 properties
 
## Running Register User Script
 
  In a terminal window, powershell window or cmd paste the below command
  
 `gradle registerUser  -Denv=int -Dsite=ss -Dusers=40 -DrampUp=0 -Dduration=0 -DtypeOfTest=`


## Running External Search Script
 
  In a terminal window, powershell window or cmd paste the below command
  
 `gradle searchOperator  -Denv={qa} -Dsite={ss} -Dusers={2} -DrampUp={0} -Dduration={0} -DtypeOfTest=`

## Injectors

You can use a single profile or a multiple combination of the profiles below to construct your load profile.
These can be added to the simulation class

    nothingFor(4 seconds), // 1
    atOnceUsers(10), // 2
    rampUsers(10) over(5 seconds), // 3
    constantUsersPerSec(20) during(15 seconds), // 4
    constantUsersPerSec(20) during(15 seconds) randomized, // 5
    rampUsersPerSec(10) to 20 during(10 minutes), // 6
    rampUsersPerSec(10) to 20 during(10 minutes) randomized, // 7
    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
    splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy atOnceUsers(30), // 9
    heavisideUsers(1000) over(20 seconds) // 10
  
 
    
    nothingFor(duration): Pause for a given duration.
    atOnceUsers(nbUsers): Injects a given number of users at once.
    rampUsers(nbUsers) over(duration): Injects a given number of users with a linear ramp over a given duration.
    constantUsersPerSec(rate) during(duration): Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
    constantUsersPerSec(rate) during(duration) randomized: Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
    rampUsersPerSec(rate1) to (rate2) during(duration): Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
    rampUsersPerSec(rate1) to(rate2) during(duration) randomized: Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
    splitUsers(nbUsers) into(injectionStep) separatedBy(duration): Repeatedly execute the defined injection step separated by a pause of the given duration until reaching nbUsers, the total number of users to inject.
    splitUsers(nbUsers) into(injectionStep1) separatedBy(injectionStep2): Repeatedly execute the first defined injection step (injectionStep1) separated by the execution of the second injection step (injectionStep2) until reaching nbUsers, the total number of users to inject.
    heavisideUsers(nbUsers) over(duration): Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration.``


##Example

``Open workload model version:
// generate an open workload injection profile
// with levels of 10, 15, 20, 25 and 30 arriving users per second
// each level lasting 10 seconds
// separated by linear ramps lasting 10 seconds
scn.inject(
incrementUsersPerSec(5)
.times(5)
.eachLevelLasting(10 seconds)
.separatedByRampsLasting(10 seconds)
.startingFrom(10)
)
``
 
## Reports
 - Reports can be found in build/gatling-results/