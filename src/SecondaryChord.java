import java.util.Arrays;

public class SecondaryChord {
	int rootNote;
	String name;
	int weight = 1;
	int[] notes = new int[12];
	
	public SecondaryChord( int[] notes, int rootNote, String name, int weight ) {
		for( int i=0; i<notes.length; i++ ) {
			notes[i] *= weight;
		}
		this.notes = notes;
		this.rootNote = rootNote;
		this.name = name;
		this.weight = weight;
	}
	public int nextNoteGlobalIndex( int prev ) {
		int nt = CONST_VAR.toLocalOctave(prev+1);
		while( notes[nt] == 0 ) {
			nt = CONST_VAR.toLocalOctave(nt+1);
		}
		if( nt<prev ) nt += 12*(1+(prev-nt)/12);
		return nt;
	}
	public int noteCount() {
		int count = 0;
		for( int n : notes) {
			if( n!=0 ) count++;
		}
		return count;
	}
	
	
//	ArrayList<Note> NoteList = new ArrayList<Note>();
//	Note RootNote = null;
//	Note KeyNote = null;
//	String name;
//	
//	public SecondaryChord( ArrayList<Note> NoteList, Note RootNote, Note KeyNote, String name ) {
//		this.NoteList = NoteList;
//		this.RootNote = RootNote;
//		this.KeyNote = KeyNote;
//		this.name = convertToCorrectChordName(name);
//	}
//	
//	public int count_related_score( ArrayList<NotePoint> noteList, ArrayList<NotePoint> rootList ) {
//		System.out.println("now compare chord:");
//		this.print();
//		int score = 0;
//		for( NotePoint compare : noteList ) {
//			int time = (int)compare.getTime();
//			Note note = compare.getNote();
////			System.out.println("now compare Note = "+note.getName() );
//			if( hasNote(note) ) {
//				System.out.println("this sec Chord has compare Note! note = "+note.getName());
//				score += time;
//			}
//		}
//		for( NotePoint compare : rootList ) {
//			int time = (int)compare.getTime();
//			Note note = compare.getNote();
////			System.out.println("now compare Note = "+note.getName() );
//			if( RootNote == note ) {
//				System.out.println("our RootNote is the same!!! note = "+note.getName());
//				score += time*1;
//			}
//		}
//		System.out.println("final score: "+score);
//		return score;
//	}
//	
//	public Boolean hasNote( Note n ) {
//		return NoteList.indexOf(n)!=-1 ;
//	}
//	
	public void print() {
		System.out.print(CONST_VAR.getNoteName(rootNote)+name+": ");
		System.out.print(Arrays.toString(notes));
		System.out.println("");
	}
//	public void short_print() {
//		System.out.print(name+"  ");
//	}

	
}
