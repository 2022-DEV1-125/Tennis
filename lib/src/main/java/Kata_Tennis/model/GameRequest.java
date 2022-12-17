package Kata_Tennis.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="GameRequest", description="Parameters to get score of the players")
public class GameRequest {
	
	private String point1;
	private String point2;
	private String point3;
	private String point4;
	private String point5;
	private String point6;
	private String advantagePoint;
	private String gamePoint;
	public String getPoint1() {
		return point1;
	}
	public void setPoint1(String point1) {
		this.point1 = point1;
	}
	public String getPoint2() {
		return point2;
	}
	public void setPoint2(String point2) {
		this.point2 = point2;
	}
	public String getPoint3() {
		return point3;
	}
	public void setPoint3(String point3) {
		this.point3 = point3;
	}
	public String getPoint4() {
		return point4;
	}
	public void setPoint4(String point4) {
		this.point4 = point4;
	}
	public String getPoint5() {
		return point5;
	}
	public void setPoint5(String point5) {
		this.point5 = point5;
	}
	public String getPoint6() {
		return point6;
	}
	public void setPoint6(String point6) {
		this.point6 = point6;
	}
	public String getAdvantagePoint() {
		return advantagePoint;
	}
	public void setAdvantagePoint(String advantagePoint) {
		this.advantagePoint = advantagePoint;
	}
	public String getGamePoint() {
		return gamePoint;
	}
	public void setGamePoint(String gamePoint) {
		this.gamePoint = gamePoint;
	}

	

}
