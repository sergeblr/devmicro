
import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


// This CafeMenu OVERALL PerfomanceTest Scenario DO:
//   Load MainPage, Orders, Items;
//   Create new Item, try to create New item with existing name
//   Load Items, Orders page, create new Order
//   Fill order with 5 items (iio) + 1 same item (iterate existing iio)
class CafemenuRabbitSimulationALL extends Simulation {

	val base_url = "http://127.0.0.1:8090"

	val pause_time = 5			// pause between operations in milliseconds
	val scn_repeats = 1		// Number of scenario repeats

	val new_item = "NewItem"+Random.nextString(5)
	val new_int = Random.nextInt(100)
	val new_coin_int = Random.nextInt(99)
	val new_employee_id = Random.nextInt(9999)

	// SetUp SIMULATION:
	val setup_users_start_rate = 5
	val setup_users_end_rate = 50
	val setup_duration = 30



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

	val headers_1 = Map(
		"Accept" -> "*/*",
		"Proxy-Connection" -> "keep-alive")

	val headers_2 = Map(
		"Origin" -> base_url,
		"Proxy-Connection" -> "keep-alive",
		"Upgrade-Insecure-Requests" -> "1")


	val scn = scenario("CafemenuRabbitSimulationALL")
		// REPEATS:
		.repeat(scn_repeats) {

			// ### ALL PAGES
			// Loading MainPage, then Orders, then Items...
			exec(http("request_mainpage")
				.get("/mainpage")
				.headers(headers_0))
				.pause(pause_time milliseconds)
				.exec(http("request_orders")
					.get("/ordersdto")
					.headers(headers_0)
					.resources(http("request_js")
						.get("/js/bootstrap-datetimepicker.min.js")
						.headers(headers_1),
						http("request_js2")
							.get("/js/moment.min.js")
							.headers(headers_1)))
				.pause(pause_time milliseconds)
				.exec(http("request_items")
					.get("/items")
					.headers(headers_0))
				.pause(pause_time * 2 milliseconds)



				// ### ITEM:
				// Goto AddItem page, then adding some item
				.exec(http("request_AddItemPage")
					.get("/item")
					.headers(headers_0))
				.pause(pause_time * 2 milliseconds)
				.exec(http("request_PostItem")
					.post("/item")
					.headers(headers_2)
					.formParam("itemId", "")
					.formParam("itemName", new_item)
					.formParam("itemPrice", new_int.toString + "." + new_coin_int))
				.pause(pause_time * 2 milliseconds)

				// Goto AddItem page and try to add EXISTING item
				.exec(http("request_GetItem")
					.get("/item")
					.headers(headers_0))
				.pause(pause_time milliseconds)
				.exec(http("request_PostNewItemThatAlreadyExist")
					.post("/item")
					.headers(headers_2)
					.formParam("itemId", "")
					.formParam("itemName", "Burger")
					.formParam("itemPrice", new_coin_int.toString))
				.pause(pause_time milliseconds)

				// Loading again Items page, Orders
				.exec(http("request_itemsAgain")
					.get("/items")
					.headers(headers_0))
				.pause(pause_time milliseconds)
				.exec(http("request_ordersAgain")
					.get("/ordersdto")
					.headers(headers_0))
				.pause(pause_time * 2 milliseconds)



				// ###ORDER:
				// Goto NEW Order page and create New order
				.exec(http("request_AddOrderPage")
					.get("/order")
					.headers(headers_0))
				.pause(pause_time * 2 milliseconds)
				.exec(http("request_PostOrder")
					.post("/order")
					.headers(headers_2)
					.formParam("orderId", "")
					.formParam("orderEmployeeId", new_employee_id.toString))
				.pause(pause_time milliseconds)

				// Fill order with items (ItemInOrder)
				.exec(http("request_AddIio1")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "1")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "3")
					.formParam("iioItemName", "French fried")
					.formParam("iioItemPrice", "3"))
				.pause(pause_time milliseconds)
				.exec(http("request_AddIio2")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "5")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "7")
					.formParam("iioItemName", "GrillBurger")
					.formParam("iioItemPrice", "4"))
				.pause(pause_time milliseconds)
				.exec(http("request_AddIio3")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "1")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "218")
					.formParam("iioItemName", "Vegetable Soup Nigella Xp")
					.formParam("iioItemPrice", "26"))
				.pause(pause_time milliseconds)
				.exec(http("request_AddIio4")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "1")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "240")
					.formParam("iioItemName", "Pasta and Beans Mixed Spice Fv")
					.formParam("iioItemPrice", "39"))
				.pause(pause_time milliseconds)
				// And adding again the same item in this order
				.exec(http("request_AddIioThatAlreadyInOrder")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "3")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "3")
					.formParam("iioItemName", "French fried")
					.formParam("iioItemPrice", "3"))
				.pause(pause_time milliseconds)
				.exec(http("request_AddIio5")
					.post("/iteminorder")
					.headers(headers_2)
					.formParam("iioItemCount", "17")
					.formParam("iioOrderId", "172")
					.formParam("iioItemId", "209")
					.formParam("iioItemName", "Pizza Cardamom Whole Nu")
					.formParam("iioItemPrice", "68"))
				.pause(pause_time milliseconds)
				.exec(http("request_AddOrderUPDATE")
					.post("/order/172")
					.headers(headers_2)
					.formParam("orderId", "172")
					.formParam("orderEmployeeId", "99"))
				.pause(pause_time milliseconds)

				//Load Orders page
				.exec(http("request_ordersAgain")
					.get("/ordersdto")
					.headers(headers_0))
		}

	// setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

	// SetUp scenario: Inject users from StartRate to EndRate during Duration
	setUp(scn.inject(rampUsersPerSec(setup_users_start_rate) to (setup_users_end_rate) during (setup_duration))).protocols(httpProtocol)
}
