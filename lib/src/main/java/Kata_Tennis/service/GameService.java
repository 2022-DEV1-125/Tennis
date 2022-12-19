package Kata_Tennis.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Service;

import Kata_Tennis.model.GameRequest;
import Kata_Tennis.model.GameResponse;

@Service
public class GameService {

	public static final String playerA = "A";
	public static final String playerB = "B";
	public static final List<String> validValues = new ArrayList<String>();
	static {
		validValues.add(playerA);
		validValues.add(playerB);
	}

	public GameResponse validateParametersValue(GameRequest gameRequest) {
		String message = "";
		GameResponse response = new GameResponse();

		try {

			Field[] gameRequestFields = gameRequest.getClass().getDeclaredFields();
			for (Field field : gameRequestFields) {

				String value = field.get(gameRequest).toString();
				if (!isBlankOrNull(value)) {
					System.out.println("Validating " + field.getName() + " :: " + value);
					if (!validValues.contains(value.toUpperCase())) {
						message = message + " " + field.getName();
					}
				}
			}

			if (!isBlankOrNull(message)) {
				response.setPlayerA_Score("Love");
				response.setPlayerB_Score("Love");
				response.setGameResult("Invalid values for parameters :: " + message + " Kindly enter valid values");
			} else {
				response = calculateScore(gameRequestFields, gameRequest);
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return response;
	}

	private GameResponse calculateScore(Field[] gameRequestFields, GameRequest gameRequest)
			throws IllegalArgumentException, IllegalAccessException {

		int playerA_score = 0;
		int playerB_score = 0;
		String message = "";
		GameResponse result = new GameResponse();

		for (Field field : gameRequestFields) {
			String value = field.get(gameRequest).toString();
			if (!isBlankOrNull(value)) {
				if (playerA_score <= 4 && playerB_score <= 4) {
					if (value.equalsIgnoreCase(playerA))
						playerA_score++;
					else if (value.equalsIgnoreCase(playerB))
						playerB_score++;
				}
			}
		}

		message = getMessage(playerA_score, playerB_score);

		result.setPlayerA_Score(getScore(playerA_score));
		result.setPlayerB_Score(getScore(playerB_score));
		result.setGameResult(message);

		return result;

	}

	public boolean isBlankOrNull(String s) {
		return (s == null || s.trim().equals(""));
	}

	private String getScore(int point) {

		switch (point) {
		case 0:
			return "Love";
		case 1:
			return "15";
		case 2:
			return "30";
		case 3:
		case 4:
			return "40";
		default:
			return "Love";
		}

	}

	private String getMessage(int playerA_score, int playerB_score) {
		String message = "";

		if (playerA_score >= 4 && playerB_score < 4)
			message = "Player A won the game";
		else if (playerB_score >= 4 && playerA_score < 4)
			message = "Player B won the game";

		return message;

	}

}
