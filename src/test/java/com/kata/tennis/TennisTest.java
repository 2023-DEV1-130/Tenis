package com.kata.tennis;

import com.kata.tennis.controller.GameController;
import com.kata.tennis.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kata.tennis.model.GameRequest;
import com.kata.tennis.model.GameResponse;
import io.restassured.RestAssured;

import static groovy.json.JsonOutput.toJson;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(GameController.class)
class TennisTest {

	@MockBean
	private GameService gameService;
	@Autowired
	private MockMvc mvc;

	@BeforeEach
	void setUp() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 9080;
	}

	@Test
	public void CalculateGameResult_InvalidInput_BadRequest() throws Exception
	{
		/************GIVEN ************/
		GameRequest request = getGameRequest("A", "a", "b", "<>:;", "3er", "C", "", "");

		/************WHEN ************/
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
								.post("/kata-tennis")
								.content(toJson(request))
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON))
								.andReturn();


		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();

		/************THEN ************/
		assertEquals(400, status);
		System.out.println("content="+content);
		assertTrue(content.contains("Bad request"));
		assertTrue(content.contains("Invalid value for parameter. Allowed values are a & b"));
	}


	//FOLLOWING ARE THE INTEGRATION TEST

	/* Test to declare winner as player A */
	@Test
	public void playerAWinTest() throws Exception {

		GameRequest request = getGameRequest("A", "a", "b", "B", "a", "A", "", "");

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

		GameRequest request = getGameRequest("b", "B", "b", "B", "a", "A", "", "");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "", "");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "", "a");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "b", "");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "a", "A");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "B", "b");

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

		GameRequest request = getGameRequest("b", "B", "b", "a", "a", "A", "B", "a");

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

		GameRequest request = getGameRequest("A", "B", "a", "a", "a", "B", "B", "b");

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
	private static GameRequest getGameRequest(String A, String a, String b, String point4, String point5, String C, String advantagePoint, String advantagePoint1) {
		GameRequest request = new GameRequest();
		request.setPoint1(A);
		request.setPoint2(a);
		request.setPoint3(b);
		request.setPoint4(point4);
		request.setPoint5(point5);
		request.setPoint6(C);
		request.setAdvantagePoint(advantagePoint);
		request.setFinalPoint(advantagePoint1);
		return request;
	}

}
