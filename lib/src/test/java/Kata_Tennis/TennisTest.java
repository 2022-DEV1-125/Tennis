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

	/* Test to validate parameter values */
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
		assertEquals("Invalid values for parameters ::  point4 point5 point6 Kindly enter valid values",
				result.getGameResult());

	}

	/* Test to declare winner as player A */
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

	/* Test to declare winner as player B */
	@Test
	public void deuceTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("");
		request.setFinalPoint("");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Deuce! Kindly enter advantage and final point", result.getGameResult());

	}

	/* Test to check deuce and validate mandatory param after deuce */
	@Test
	public void advantagePointTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("");
		request.setFinalPoint("a");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Deuce! Kindly enter advantage point", result.getGameResult());

	}

	/* Test to check deuce and validate mandatory param after deuce */
	@Test
	public void finalPointTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("b");
		request.setFinalPoint("");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Deuce! Kindly enter final point", result.getGameResult());

	}

	/* Test to declare A as winner after deuce */
	@Test
	public void playerA_afterDeuce() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("a");
		request.setFinalPoint("A");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Player A won the game after Deuce", result.getGameResult());

	}

	/* Test to declare A as winner after deuce */
	@Test
	public void playerB_afterDeuce() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("B");
		request.setFinalPoint("b");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Player B won the game after Deuce", result.getGameResult());

	}

	/* Test to validate second deuce */
	@Test
	public void deuceAgain() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");
		request.setAdvantagePoint("B");
		request.setFinalPoint("a");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Deuce Again!", result.getGameResult());

	}

	/* Test to ignore missing param and declare winner using given scores */
	
	@Test
	public void withoutParamTest_B() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("B");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("Love", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Player B won the game", result.getGameResult());

	}

	/* Test to ignore param after winner declaration */
	@Test
	public void optionalParam_AfterWin() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("A");
		request.setPoint2("B");
		request.setPoint3("a");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("B");
		request.setAdvantagePoint("B");
		request.setFinalPoint("b");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("15", result.getPlayerB_Score()); // Since player A already won at point5 rest of the following
														// points are ignored
		assertEquals("Player A won the game", result.getGameResult());

	}

	/* Test to validate missing parameters after deuce */
	@Test
	public void withoutParamTest_deuce() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");
		request.setPoint5("a");
		request.setPoint6("A");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("40", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Deuce! Kindly enter advantage and final point", result.getGameResult());

	}

	/* Test to validate if adequate parameters are present or not*/
	@Test
	public void missingMandatoryParamTest() throws Exception {

		GameRequest request = new GameRequest();
		request.setPoint1("b");
		request.setPoint2("B");
		request.setPoint3("b");
		request.setPoint4("a");

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("15", result.getPlayerA_Score());
		assertEquals("40", result.getPlayerB_Score());
		assertEquals("Please enter furthur points to calculate the winner", result.getGameResult());

	}

	/* Test to validate if request body is empty*/
	@Test
	public void emptyBodyTest() throws Exception {

		GameRequest request = new GameRequest();

		Response response = given().contentType("application/json").accept("application/json").body(request).when()
				.post("/kata-tennis").then().statusCode(200).extract().response();
		response.getBody().print();
		GameResponse result = ((ResponseOptions<Response>) response).getBody().as(GameResponse.class);
		assertEquals("Love", result.getPlayerA_Score());
		assertEquals("Love", result.getPlayerB_Score());
		assertEquals("Please enter furthur points to calculate the winner", result.getGameResult());

	}

}
