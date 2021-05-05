package customization;

import java.awt.Color;
import java.awt.image.BufferedImage;

import graphics.Assets;

public enum PlayerColor {

	GREEN, RED, PINK;

	//liczba kolorow gracza w grze
	public static final int numberOfColors = 3;
	
	//kolor wybrany przez gracza
	public static PlayerColor playerColor = null;
	
	//zwraca asset gracza o danym koloru
	public static BufferedImage getPlayerAsset() {
		return switch (playerColor) {
			case GREEN -> Assets.player_green;
			case RED -> Assets.player_red;
			case PINK -> Assets.player_pink;
		};
	}
	
	//zwraca asset na podstawie int
	public static BufferedImage intToAsset(int i) {
		return switch (i) {
			case 0 -> Assets.player_green;
			case 1 -> Assets.player_red;
			case 2 -> Assets.player_pink;
			default -> null;
		};
	}
	
	//zwraca kolor z java.atx.Color do zmiany koloru w renderowaniu 
	public static Color getJavaColor() {
		return switch (playerColor) {
			case GREEN -> Color.GREEN;
			case RED -> Color.RED;
			case PINK -> Color.MAGENTA;
		};
	}
	
	//zwraca kolor z java.atx.Color do zmiany koloru w renderowaniu na podstawie int
	public static Color getJavaColor(int i) {
		return switch (i) {
			case 0 -> Color.GREEN;
			case 1 -> Color.RED;
			case 2 -> Color.MAGENTA;
			default -> null;
		};
	}
	
	//zwraca enum na podstawie int
	public static PlayerColor intToEnum(int n) {
		return switch (n) {
			case 0 -> PlayerColor.GREEN;
			case 1 -> PlayerColor.RED;
			case 2 -> PlayerColor.PINK;
			default -> null;
		};
	}
}
