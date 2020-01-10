
import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


// This CafeMenu ITEMS Perfomance Testing DO:
//   Create new Item
//   Load Items;
class CafemenuRabbitSimItems extends Simulation {

	val base_url = "http://127.0.0.1:8090"

	val new_item_name_random_map = Iterator.continually(
		Map("rand_item_name" -> ("SomeItem " + Random.alphanumeric.take(10).mkString )))	// ItemName postfix RANDOM number of chars
	val new_item_price_random_map = Iterator.continually(
		Map("rand_item_price" -> Random.nextInt(100)))
	/*val new_item_price_coin_fix = 12*/

	// SetUp SIMULATION:
	val pause_time = 10		// pause between operations in milliseconds
	val scn_repeats = 1		// Number of scenario repeats
	val setup_users_start_rate = 1	// MIN users INJECTED in ONE second
	val setup_users_end_rate = 50		// MAX users INJECTED in ONE second
	val setup_duration = 30					// Total duration in seconds



	val httpProtocol = http
		.baseUrl(base_url)
		.inferHtmlResources()
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
		.userAgentHeader("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/79.0.3945.79 Chrome/79.0.3945.79 Safari/537.36")

	val headers_0 = Map(
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")

	val headers_2 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive")

	val headers_6 = Map(
		"Origin" -> base_url,
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	val scn = scenario("CafemenuRabbitSimulationItems")
		// REPEATS:
  	.repeat(scn_repeats) {
			feed(new_item_name_random_map)
			.feed(new_item_price_random_map)

			// ### ITEM:
			// Goto AddItem page, then adding new item
			 .exec(http("request_AddItemPage")
				.get("/item")
				.headers(headers_0))
			 .pause(pause_time milliseconds)
			 .exec(http("request_PostItem")
						.post("/item")
						.headers(headers_2)
						.formParam("itemId", "")
						.formParam("itemName", "${rand_item_name}")
						.formParam("itemPrice", "${rand_item_price}")
				)
				.pause(pause_time milliseconds)

				// Loading Items...
				.exec(http("request_items")
					.get("/items")
					.headers(headers_0))
				.pause(pause_time milliseconds)

		}

	//setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

	// SetUp scenario: Inject users from StartRate to EndRate EACH SECOND during Duration
	setUp(scn.inject(rampUsersPerSec(setup_users_start_rate) to (setup_users_end_rate) during (setup_duration))).protocols(httpProtocol)
}
