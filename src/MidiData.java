import java.util.ArrayList;

public class MidiData {
	int channel;
	ArrayList<NotePoint> notePoints = new ArrayList<NotePoint>();
	public MidiData(int channel) {
		this.channel = channel;
	}
	public MidiData(int channel, NotePoint firstNp) {
		this(channel);
		this.notePoints.add(firstNp);
	}
	public NotePoint[] getNotePointArray() {
		return (NotePoint[]) notePoints.toArray(new NotePoint[0]);
	}
	public void print() {
		System.out.println("channel: "+channel+"size: "+notePoints.size());
	}
}
