import java.util.Arrays;

public class AnalyzeNotePoint {
	final static int MEASURE = 960;
	NotePoint[] notePoints;
	int measureCount;
	
	public AnalyzeNotePoint( NotePoint[] notePoints, int measureCount ) {
		this.notePoints = notePoints;
		this.measureCount = measureCount;
	}
	
	public int[][] doJob() {
		int[][] noteData = new int[measureCount][12];
		for( NotePoint np : notePoints ) {
//			np.printNote();
			placeIntoMeasure(np, noteData);
		}	
		for( int[] tmp : noteData ) { 
			System.out.println(Arrays.toString(tmp));
		}
		return noteData;
	}
	private void placeIntoMeasure( NotePoint np, int[][] noteData ) {
		long startT = np.getStart();
		long endT = np.getEnd();
		long startMeasure = (startT/MEASURE);
		long endMeasure = (endT/MEASURE);
		
		if( startMeasure != endMeasure ) {
			NotePoint newNp = new NotePoint(np);
			newNp.setStart( (startMeasure+1)*MEASURE );
			placeIntoMeasure(newNp, noteData);
			endT = (startMeasure+1)*MEASURE-1;
		}
		long weight = 1;
		if( startT % MEASURE == 0 ) {
			weight = 2;
		}
		System.out.println((int)startMeasure);
		System.out.println(np.getKey());
		noteData[(int)startMeasure][np.getNoteIndex()] += weight*(endT - startT);
	}
	
	public void getNoteData() {
		
	}
	public void getTempoData() {
		
	}
	
}
