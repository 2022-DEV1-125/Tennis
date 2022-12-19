package Kata_Tennis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import Kata_Tennis.model.GameRequest;
import Kata_Tennis.model.GameResponse;
import Kata_Tennis.service.GameService;

@RestController
@RequestMapping("/kata-tennis")
public class GameController {

	@Autowired
	GameService gameService;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResponse> calculateGameResult(
			@Parameter(description = "Controller to validate request paramets and give game result", schema = @Schema(implementation = GameRequest.class)) @RequestBody GameRequest gameRequest) {
		System.out.println("------Inside Controller------");
		GameResponse response = new GameResponse();

		try {
			response = gameService.validateParametersValue(gameRequest);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
