import java.util.Arrays;

import javax.swing.JOptionPane;

public class ShowOptionPane {
//	int melodyTrack = -1;
//	int songSpeed = 120;
//	int tone = 0;
//	
//	File inFile;

	public static int getMelodyChannel(int[] channelList) {
		Object[] list = new Object[channelList.length];
		for(int i=0; i<list.length; i++ ) {
			list[i] = channelList[i];
		}
		System.out.println("list: ");
		System.out.println(Arrays.toString(list));
		Object ans = JOptionPane.showInputDialog(null, "選擇主旋律頻道", "選擇主旋律頻道", JOptionPane.QUESTION_MESSAGE, null, list, 1);
		return Integer.valueOf(ans.toString());
	}

	public static int getSongSpeed() {
		int range = CONST_VAR.SPEED_RANGE;
		Object[] list = new Object[range];
		for( int i=0; i<list.length; i++ ) {
			list[i] = CONST_VAR.DEFAULT_SPEED - range/2 + i;
		}
		Object ans = JOptionPane.showInputDialog(null, "選擇歌曲速度", "選擇歌曲速度", JOptionPane.QUESTION_MESSAGE, null, list, 120);
		return Integer.valueOf(ans.toString());
	}

	public static int getTone() {
		Object[] list = new Object[]{"C/Am", "#C/#Am", "D/Bm", "bE/Cm", "E/#Cm", "F/Dm", "bG/bEm", "G/Em", "bA/Fm", "A/#Fm", "bB/Gm", "B/#Gm"};

		Object ans = JOptionPane.showInputDialog(null, "選擇調性", "選擇調性", JOptionPane.QUESTION_MESSAGE, null, list, null);
		System.out.println(ans);
		System.out.println(Arrays.asList(list).indexOf(ans));
		return Arrays.asList(list).indexOf(ans);
	}
}
