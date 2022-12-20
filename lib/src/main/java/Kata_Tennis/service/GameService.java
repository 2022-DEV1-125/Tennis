package Kata_Tennis.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
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
		System.out.println("validateParametersValue() -> Entry ");
		String message = "";
		GameResponse response = new GameResponse();
		Object val = null;

		try {

			Field[] gameRequestFields = gameRequest.getClass().getDeclaredFields();
			for (Field field : gameRequestFields) {
				val = field.get(gameRequest);
				if (val == null) {
					field.set(gameRequest, "");
				}

				if (val != null && !isBlankOrNull(val.toString())) {
					System.out.println("Validating " + field.getName() + " :: " + val.toString());
					if (!validValues.contains(val.toString().toUpperCase())) {
						message = message + " " + field.getName();
					}
				}
			}
			if (!isBlankOrNull(message)) {
				response.setStatus("Error");
				response.setPlayerA_Score("Love");
				response.setPlayerB_Score("Love");
				response.setGameResult("Invalid values for parameters :: " + message + " Kindly enter valid values");
			} else {
				response.setStatus("Success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("validateParametersValue() -> Exit ");
		return response;
	}

	public GameResponse calculateScore(GameRequest gameRequest) {
		System.out.println("calculateScore() -> Entry ");

		int playerA_score = 0;
		int playerB_score = 0;
		String message = "";
		Object val = null;
		GameResponse result = new GameResponse();

		try {
			Field[] gameRequestFields = gameRequest.getClass().getDeclaredFields();
			for (Field field : gameRequestFields) {
				if (!field.getName().equals("advantagePoint") && !field.getName().equals("finalPoint")) {
					String value = field.get(gameRequest).toString();
					if (!isBlankOrNull(value)) {
						if (value.equalsIgnoreCase(playerA))
							playerA_score++;
						else if (value.equalsIgnoreCase(playerB))
							playerB_score++;

						if (playerA_score == 4 || playerB_score == 4) {
							break;
						}
					}

				}
			}

			message = getMessage(playerA_score, playerB_score, gameRequest);

			result.setStatus("Success");
			result.setPlayerA_Score(getScore(playerA_score));
			result.setPlayerB_Score(getScore(playerB_score));
			result.setGameResult(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("calculateScore() -> Exit ");
		return result;

	}

	private boolean isBlankOrNull(String s) {
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

	private String getMessage(int playerA_score, int playerB_score, GameRequest gameRequest) {
		System.out.println(
				"getMessage() -> Entry " + "playerA_score :: " + playerA_score + " playerB_score :: " + playerB_score);

		String message = "";

		if (playerA_score >= 4 && playerB_score < 4)
			message = "Player A won the game";
		else if (playerB_score >= 4 && playerA_score < 4)
			message = "Player B won the game";
		else if (playerA_score == 3 && playerB_score == 3) {
			message = checkDeuce(gameRequest);
		} else if ((playerA_score != 4 && playerB_score != 4)) {
			if (!(playerA_score == 3 && playerB_score == 3))
				message = "Please enter furthur points to calculate the winner";
		}

		System.out.println("getMessage() -> Exit ");
		return message;

	}

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

}
