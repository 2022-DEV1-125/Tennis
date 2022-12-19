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
		String message = "";
		GameResponse response = new GameResponse();

		try {

			Field[] gameRequestParams = gameRequest.getClass().getDeclaredFields();
			for (Field field : gameRequestParams) {

				String value = field.get(gameRequest).toString();
				if (!isBlankOrNull(value)) {
					System.out.println("Validating " + field.getName() + " :: " + value);
					if (!validValues.contains(value.toUpperCase())) {
						message = message + ", " + field.getName();
					}
				}
			}

			if (!isBlankOrNull(message)) {
				response.setPlayerA_Score("Love");
				response.setPlayerB_Score("Love");
				response.setGameResult("Invalid values for parameters :: " + message);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return response;
	}

	public boolean isBlankOrNull(String s) {
		return (s == null || s.trim().equals(""));
	}

}
