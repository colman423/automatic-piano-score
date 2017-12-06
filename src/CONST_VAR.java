

public class CONST_VAR {
	static final int NOTE_ON = 0x90;
    static final int NOTE_OFF = 0x80;
    static final int MEASURE = 960;
    static final String outPath = "E:/Porkchop/桌面/課程/資科/專題/SOUND";
    static final String MUSE_SCORE = "C:/Program Files (x86)/MuseScore 2/bin/MuseScore.exe";
    
	static final String[] NOTE_NAMES = {"C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B"};
	static final int[][] TONE_WEIGHT = {{10, 3}, {3, 3}, {6, 10}, {4, 3}, {7, 9}, {10, 8}, {3, 3}, {10, 4}, {4, 3}, {7, 10}, {5, 3}, {3, 3}};
	
	static final int DEFAULT_SPEED = 120;
	static final int SPEED_RANGE = 100;
	
	static int toLocalOctave( int index ) {
		return Math.floorMod(index, 12);
//		index % 12;
	}
	static String getNoteName(int index) {
		return NOTE_NAMES[index];
	}
}

