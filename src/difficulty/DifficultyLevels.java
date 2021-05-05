package difficulty;

public enum DifficultyLevels {
	
	EASY, MEDIUM, HARD;

	public static DifficultyLevels currentDifficulty;
	
	//pozwala na zamienianie int na enum
	public static DifficultyLevels intToEnum(int n) {
		return switch (n) {
			case 0 -> EASY;
			case 1 -> MEDIUM;
			case 2 -> HARD;
			default -> null;
		};
	}
}
