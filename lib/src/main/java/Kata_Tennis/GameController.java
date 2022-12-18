package Kata_Tennis;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import Kata_Tennis.model.GameRequest;
import Kata_Tennis.model.GameResponse;

@RestController
@RequestMapping("/kata-tennis")
public class GameController {

	public static final String playerA = "A";
	public static final String playerB = "B";
	public static final List<String> validValues = new ArrayList<String>();
	static {
		validValues.add(playerA);
		validValues.add(playerB);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public GameResponse calculateGameResult(
			@Parameter(description = "Controller to validate request paramets and give game result", schema = @Schema(implementation = GameRequest.class)) @RequestBody GameRequest gameRequest) {
		System.out.println("------Inside Controller------");
		GameResponse response = new GameResponse();
		String message = "";

		try {
			message = validateParametersValue(gameRequest);
			if (!isBlankOrNull(message)) {
				response.setPlayerA_Score("Love");
				response.setPlayerB_Score("Love");
				response.setGameResult("Invalid values for parameters :: " + message);
			}

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return response;
	}

	private String validateParametersValue(GameRequest gameRequest) {
		String message = "";

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
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return message;
	}

	private boolean isBlankOrNull(String s) {
		return (s == null || s.trim().equals(""));
	}

}
