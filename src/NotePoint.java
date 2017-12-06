
public class NotePoint {
	private int channel;
	private int key;
	private long start;
	private long end = -1;
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
//	public int getOctave() {
//		return (key / 12)-1;
//	}
	public int getNoteIndex() {
		return key % 12;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}
	public long getTime() {
		return end-start;
	}
	public NotePoint(int channel, int key, long start) {
		setChannel(channel);
		setKey(key);
		setStart(start);
	}	
	public NotePoint(int channel, int key, long start, long end) {
		this(channel, key, start);
		this.setEnd(end);
	}
	public NotePoint(NotePoint p) {
		this(p.getChannel(), p.getKey(), p.getStart(), p.getEnd());
	}
//	public void printNote() {
//		String noteName = CONST_VAR.getNoteName(getNoteIndex());
//		System.out.println("Channel "+getChannel()+": "+noteName+getOctave()+" in "+start+"~"+end);
//    }
	
}
