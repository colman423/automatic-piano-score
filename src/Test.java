import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.swing.JFileChooser;

public class Test {
	/*	
	 * tone weight
	 * tempo
	 * 
	*/
	public static void main(String[] args) throws Exception {
//		System.setOut(new PrintStream(new FileOutputStream("E:/Porkchop/орн▒/output.txt")));

		File inFile = getInputFile();
		Sequence midiSequence = MidiSystem.getSequence(inFile);
		
		long songTime = midiSequence.getTickLength();
		int measureCount;
		measureCount = 1 + (int) songTime / CONST_VAR.MEASURE;
		consoleInfo(inFile.getName(), measureCount, midiSequence.getDivisionType(), midiSequence.getResolution());
		
		ReadMIDI reader = new ReadMIDI(midiSequence.getTracks());
		
		NotePoint[] notePoints = reader.getAllNotePoints(); 
		int[][] noteData = new AnalyzeNotePoint(notePoints, measureCount).doJob();

		int[] channelList = reader.getChannelList();
		int melodyChannel = ShowOptionPane.getMelodyChannel(channelList);
		
		int songSpeed = ShowOptionPane.getSongSpeed();
		int tone = ShowOptionPane.getTone();
		Chord[] chordList = Chord.initialChordList(tone);
		
		NotePoint[] melodyNotePoints = reader.getChannelNotePoints(melodyChannel);
		SecondaryChord[] chordSequence = new AnalyzeChord(chordList, noteData, measureCount).doJob();
		WriteMIDI.write(songTime, songSpeed, chordSequence, melodyNotePoints, 36, getOutputFileName(inFile.getPath()));

		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
		System.out.println("COMPLETE");
	}

	private static void consoleInfo(String inFile, int measureCount, float divisionType, int resolution) {
		System.out.println(inFile);
		System.out.println("measureCount: " + measureCount);
		System.out.println("divisionType: " + divisionType);
		System.out.println("resolution: " + resolution);
	}

	private static File getInputFile() {
		JFileChooser fc = new JFileChooser(CONST_VAR.outPath);
		fc.setAcceptAllFileFilterUsed(false);
		fc.showOpenDialog(null);
		return fc.getSelectedFile();
	}

	private static String getOutputFileName(String in) {
		int pointPos = in.lastIndexOf('.');
		System.out.println("pointPos = " + pointPos);
		String filename_after_point = in.substring(pointPos);
		String filename_before_point = in.substring(0, pointPos);
		return filename_before_point + "_new" + filename_after_point;
	}

}
