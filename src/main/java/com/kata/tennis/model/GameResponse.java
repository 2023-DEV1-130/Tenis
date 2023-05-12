package com.kata.tennis.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Game Response", description = "Parameters to display game result")
public class GameResponse {

	private String status;
	private String playerA_Score;
	private String playerB_Score;
	private String gameResult;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlayerA_Score() {
		return playerA_Score;
	}

	public void setPlayerA_Score(String playerA_Score) {
		this.playerA_Score = playerA_Score;
	}

	public String getPlayerB_Score() {
		return playerB_Score;
	}

	public void setPlayerB_Score(String playerB_Score) {
		this.playerB_Score = playerB_Score;
	}

	public String getGameResult() {
		return gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	public GameResponse() {
		this.status = "Error";
		this.playerA_Score = "Love";
		this.playerB_Score = "Love";
		this.gameResult = "GeneralError";
	}

}
