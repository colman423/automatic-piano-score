
public class AnalyzeChord {
	Chord[] chordList;
	int[][] noteData;
	int measureCount;
	
	public AnalyzeChord( Chord[] chordList, int[][] noteData, int measureCount ) {
		this.chordList = chordList; 
		this.noteData = noteData;
		this.measureCount = measureCount;
	}
	public SecondaryChord[] doJob() {
		SecondaryChord[] list = new SecondaryChord[measureCount];
		for( int i=0; i<noteData.length; i++ ) {
			int[] data = noteData[i];
			SecondaryChord bestChord = null;
			int max = 0;
			for( Chord chord : chordList ) {
				for( SecondaryChord secC : chord.MajorChordList ) {
					int product = innerProduct(data, secC.notes);
					if( product > max ) {
						max = product;
						bestChord = secC;
					}
				}
				for( SecondaryChord secC : chord.MinorChordList ) {
					int product = innerProduct(data, secC.notes);
					if( product > max ) {
						max = product;
						bestChord = secC;
					}
				}
			}
			list[i] = bestChord;
		}
		return list;
	}
	private int innerProduct( int[] a, int[] b ) {
		if( a.length != b.length ) {
			System.out.println("length not equal");
			return -1;
		}
		else {
			int sum = 0;
			for( int i=0; i<a.length; i++ ) {
				sum += a[i]*b[i];
			}
			return sum;
		}
	}
}
