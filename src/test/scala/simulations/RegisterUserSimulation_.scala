//package simulations
//
//import io.gatling.core.Predef.*
//import io.gatling.core.structure.PopulationBuilder
//import io.gatling.http.Predef.*
//import io.gatling.http.protocol.HttpProtocolBuilder
//import scenarios.RegisterUser
//import utils.SetUp_.{rampDurationInMin, rampUp, users}
//import utils.{Headers, SetUp_}
//
//import scala.concurrent.duration.*
//import scala.language.postfixOps
//
//
//class RegisterUserSimulation_ extends Simulation {
//
//  val httpConfiguration: HttpProtocolBuilder = http.baseUrl(SetUp_.baseURL).headers(Headers.requestHeaders)
//    .disableCaching
//    .disableWarmUp
//    .silentResources
//    .perUserNameResolution
//
//  val RegisterUsers: PopulationBuilder =
//        RegisterUser.registerUser().injectOpen(rampUsers(users) during (rampUp minutes))
//          .throttle(reachRps(1) in (60 seconds), holdFor(rampDurationInMin minutes))
//  setUp(RegisterUsers)
//    .protocols(httpConfiguration)
//    .assertions(global.failedRequests.count.is(0))
//}