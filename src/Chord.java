import java.util.ArrayList;

public class Chord {
	int rootNote;
	ArrayList<SecondaryChord> MajorChordList = new ArrayList<SecondaryChord>();
	ArrayList<SecondaryChord> MinorChordList = new ArrayList<SecondaryChord>();
	
	public static Chord[] initialChordList(int tone) {
		Chord[] list = new Chord[12];
		for (int i = 0; i < 12; i++) {
			int localTone = CONST_VAR.toLocalOctave(i-tone);
			int[] weight = CONST_VAR.TONE_WEIGHT[localTone];
			list[i] = new Chord(i, weight[0], weight[1]);
		}
		for( int i=0; i<12; i++ ) {
			String name = CONST_VAR.NOTE_NAMES[i];
			for( int j=0; j<list[i].MajorChordList.size(); j++ ) {
				System.out.println(name+list[i].MajorChordList.get(j).name+" W="+list[i].MajorChordList.get(j).weight);
			}
			for( int j=0; j<list[i].MinorChordList.size(); j++ ) {
				System.out.println(name+list[i].MinorChordList.get(j).name+" W="+list[i].MinorChordList.get(j).weight);
			}
		}
		return list;
		
	}
	
	public Chord( int root, int majorWeight, int minorWeight ) {
		rootNote = root;
		initialMajorChordList(majorWeight);
		initialMinorChordList(minorWeight);
	}
	private void initialMajorChordList(int weight) {
		System.out.println("ROOTNTOE = "+rootNote);
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+4) ] = 75;	//E
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+4) ] = 75;	//E
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			notes[ CONST_VAR.toLocalOctave(rootNote+10) ] = 50;	//Bb
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "7", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+4) ] = 75;	//E
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			notes[ CONST_VAR.toLocalOctave(rootNote+11) ] = 50;	//B
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "maj7", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			notes[ CONST_VAR.toLocalOctave(rootNote+2) ] = 50;	//D
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "sus2", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+5) ] = 50;	//F
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "sus4", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+4) ] = 50;	//E
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			notes[ CONST_VAR.toLocalOctave(rootNote+2) ] = 50;	//D
			
			MajorChordList.add(new SecondaryChord(notes, rootNote,  "add2", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+4) ] = 50;	//E
			notes[ CONST_VAR.toLocalOctave(rootNote+11) ] = 50;	//B
			notes[ CONST_VAR.toLocalOctave(rootNote+14) ] = 50;	//D
			
			MajorChordList.add(new SecondaryChord(notes, rootNote, "16", weight ));
		}
	}
	
	private void initialMinorChordList(int weight){
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+3) ] = 75;	//bE
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			MinorChordList.add(new SecondaryChord(notes, rootNote, "m", weight ));
		}
		{
			int[] notes = new int[12];
			notes[ CONST_VAR.toLocalOctave(rootNote+0) ] = 100;	//C
			notes[ CONST_VAR.toLocalOctave(rootNote+3) ] = 75;	//bE
			notes[ CONST_VAR.toLocalOctave(rootNote+7) ] = 75;	//G
			notes[ CONST_VAR.toLocalOctave(rootNote+10) ] = 75;	//bB
			MinorChordList.add(new SecondaryChord(notes, rootNote, "m7", weight ));
		}
	}
	
}
