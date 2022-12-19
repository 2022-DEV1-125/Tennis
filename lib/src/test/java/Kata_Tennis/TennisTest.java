/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Kata_Tennis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Kata_Tennis.model.GameRequest;
import Kata_Tennis.model.GameResponse;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;


import static org.junit.jupiter.api.Assertions.*;

class TennisTest {

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 9080;
	}

	@Test
	public void someLibraryReturnsTrue() {
		Tennis classUnderTest = new Tennis();
		assertTrue(classUnderTest.someMethod(), "someMethod should return 'true'");
	}

	@Test
	public void paramValidationTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("A");
		request.setPoint2("a");
		request.setPoint3("b");
		request.setPoint4("<>:;");
		request.setPoint5("3er");
		request.setPoint6("C");
		request.setAdvantagePoint("");
		request.setFinalPoint("");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("Love", result.getPlayerA_Score());
		assertEquals("Love", result.getPlayerB_Score());
		assertEquals("Invalid values for parameters ::  point4 point5 point6 Kindly enter valid values", result.getGameResult());

	}
	
	@Test
	public void playerAWinTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("A");
		request.setPoint2("a");
		request.setPoint3("b");
		request.setPoint4("B");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("");
		request.setFinalPoint("");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("30", result.getPlayerB_Score());
		assertEquals("Player A won the game", result.getGameResult());

	}
	
	@Test
	public void playerBWinTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("B");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("");
		request.setFinalPoint("");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("Love", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Player B won the game", result.getGameResult());

	}
}
