package com.kata.tennis.model;

import com.kata.tennis.validations.GameRequestConstraint;

import javax.validation.constraints.NotNull;

public class GameRequest {

	@GameRequestConstraint
	public String point1;
	@GameRequestConstraint
	public String point2;
	@GameRequestConstraint
	public String point3;
	@GameRequestConstraint
	public String point4;
	@GameRequestConstraint
	public String point5;
	@GameRequestConstraint
	public String point6;
	public String advantagePoint;
	public String finalPoint;

	public String getPoint1() {
		return point1;
	}

	public void setPoint1(String point1) {
		this.point1 = point1 != null ? point1 : "";
	}

	public String getPoint2() {
		return point2;
	}

	public void setPoint2(String point2) {
		this.point2 = point2 != null ? point2 : "";
	}

	public String getPoint3() {
		return point3;
	}

	public void setPoint3(String point3) {
		this.point3 = point3 != null ? point3 : "";
	}

	public String getPoint4() {
		return point4;
	}

	public void setPoint4(String point4) {
		this.point4 = point4 != null ? point4 : "";
	}

	public String getPoint5() {
		return point5;
	}

	public void setPoint5(String point5) {
		this.point5 = point5 != null ? point5 : "";
	}

	public String getPoint6() {
		return point6;
	}

	public void setPoint6(String point6) {
		this.point6 = point6 != null ? point6 : "";
	}

	public String getAdvantagePoint() {
		return advantagePoint;
	}

	public void setAdvantagePoint(String advantagePoint) {
		this.advantagePoint = advantagePoint != null ? advantagePoint : "";
	}

	public String getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(String finalPoint) {
		this.finalPoint = finalPoint != null ? finalPoint : "";
	}

}
