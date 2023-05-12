package com.kata.tennis.service;

import java.util.Map;
import java.util.stream.Collectors;

import com.kata.tennis.commons.JsonUtils;
import org.springframework.stereotype.Service;

import com.kata.tennis.model.GameRequest;
import com.kata.tennis.model.GameResponse;

@Service
public class GameService {

	public static final String PLAYER_A = "A";
	public static final String PLAYER_B = "B";
	public static final String LOVE = "Love";
	public static final String ONE_POINT = "15";
	public static final String TWO_POINTS = "30";
	public static final String THREE_POINTS = "40";
	public static final String GAME_POINT = THREE_POINTS;
	public static final String ADVANTAGE_POINT = "advantagePoint";
	public static final String FINAL_POINT = "finalPoint";

	/*
	 * This method is used to calculate score with given valid values
	 *
	 *
	 * @param GameRequest
	 *
	 * @return GameResponse
	 */
	public GameResponse calculateScore(GameRequest gameRequest) {
		System.out.println("calculateScore() -> Entry ");

		String message = "";
		GameResponse result = new GameResponse();

		try {

			Map<Boolean, Integer> gamePoints = JsonUtils.objectToMap(gameRequest).entrySet()
					.stream()
					.filter(entry -> !(ADVANTAGE_POINT.equals(entry.getKey()) || FINAL_POINT.equals(entry.getKey())))
					.collect(Collectors.partitioningBy(entry -> PLAYER_A.equalsIgnoreCase(entry.getValue()), Collectors.summingInt(e -> 1)));

			int scorePlayerA = gamePoints.get(true);
			int scorePlayerB = gamePoints.get(false);

			message = getMessage(scorePlayerA, scorePlayerB, gameRequest);

			result.setStatus("Success");
			result.setPlayerA_Score(getScore(scorePlayerA));
			result.setPlayerB_Score(getScore(scorePlayerB));
			result.setGameResult(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("calculateScore() -> Exit ");
		return result;

	}

	/*
	 * This method is used to return message according to calculates score
	 * found
	 *
	 * @param scorePlayerA, scorePlayerB, gameRequest
	 *
	 * @return result message
	 */
	private String getMessage(int scorePlayerA, int scorePlayerB, GameRequest gameRequest) {
		System.out.println(
				"getMessage() -> Entry " + "scorePlayerA :: " + scorePlayerA + " scorePlayerB :: " + scorePlayerB);

		String message = "";

		if (scorePlayerA >= 4 && scorePlayerB < 4)
			message = "Player A won the game";
		else if (scorePlayerB >= 4 && scorePlayerA < 4)
			message = "Player B won the game";
		else if (scorePlayerA == 3 && scorePlayerB == 3) {
			message = checkDeuce(gameRequest);
		} else if ((scorePlayerA != 4 && scorePlayerB != 4)) {
			if (!(scorePlayerA == 3 && scorePlayerB == 3))
				message = "Please enter further points to calculate the winner";
		}

		System.out.println("getMessage() -> Exit ");
		return message;

	}

	/*
	 * This method is used to check deuce and return message accordingly
	 * found
	 *
	 * @param GameRequest
	 *
	 * @return message
	 */
	private String checkDeuce(GameRequest gameRequest) {
		System.out.println("getMessage() -> Entry ");
		String message = "Deuce!";
		String adPoint = gameRequest.getAdvantagePoint();
		String finalPoint = gameRequest.getFinalPoint();

		if (isBlankOrNull(adPoint) && isBlankOrNull(finalPoint)) {
			message = message + " Kindly enter advantage and final point";
		} else if (isBlankOrNull(adPoint)) {
			message = message + " Kindly enter advantage point";
		} else if (isBlankOrNull(finalPoint)) {
			message = message + " Kindly enter final point";
		} else {
			if (adPoint.equalsIgnoreCase(finalPoint)) {
				message = "Player " + adPoint.toUpperCase() + " won the game after Deuce";
			} else if (!adPoint.equalsIgnoreCase(finalPoint)) {
				message = "Deuce Again!";
			}
		}

		System.out.println("getMessage() -> Exit ");
		return message;
	}

	private boolean isBlankOrNull(String s) {
		return (s == null || s.trim().equals(""));
	}

	private String getScore(int point) {

		switch (point) {
			case 1:
				return ONE_POINT;
			case 2:
				return TWO_POINTS;
			case 3:
			case 4:
				return GAME_POINT;
			case 0:
			default:
				return LOVE;
		}

	}

}
