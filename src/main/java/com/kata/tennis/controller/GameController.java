package com.kata.tennis.controller;

import com.kata.tennis.service.GameService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

import com.kata.tennis.model.GameRequest;
import com.kata.tennis.model.GameResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
@RequestMapping("/kata-tennis")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameResponse> calculateGameResult(
			@Parameter(description = "Controller to validate request parameters and give game result",
			schema = @Schema(implementation = GameRequest.class))
			@Valid @NotNull @RequestBody GameRequest gameRequest) {
		System.out.println("------Inside Controller------");

		GameResponse response = gameService.calculateScore(gameRequest);

		System.out.println("------Exit Controller------");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
