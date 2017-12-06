import java.util.ArrayList;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class ReadMIDI {
	NotePoint[] notePoints;
	int[] channelList;
	ArrayList<MidiData> midiDataList = new ArrayList<MidiData>();
	
	public ReadMIDI(Track[] tracks) {
		
		ArrayList<MidiData> todoList = new ArrayList<MidiData>();

		for (Track track : tracks) {
			// System.out.println("Track " + trackNumber + ": size = " +
			// track.size());
			// System.out.println();
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				long tick = event.getTick();
				// System.out.print("@" + tick + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					ShortMessage sm = (ShortMessage) message;
					int channel = sm.getChannel() + 1;
					int command = sm.getCommand();
					// System.out.print("Channel: " + channel+" ");
					// System.out.print("
					// Command:"+Integer.toBinaryString(command)+" ");
					if (command == CONST_VAR.NOTE_ON || command == CONST_VAR.NOTE_OFF) {
						// if (command==NOTE_OFF) System.out.println("NOTEOFF");
						if (!isDrum(channel)) {
							int key = sm.getData1();
							int velocity = sm.getData2();
							// System.out.println("velocity = "+velocity+", key
							// = "+key);
							if (velocity != 0 && command == CONST_VAR.NOTE_ON) {
//								todoPoints.add(new NotePoint(channel, key, tick));
								int iTodo = getMidiData(todoList, channel);
								if( iTodo!=-1) {
									todoList.get(iTodo).notePoints.add(new NotePoint(channel, key, tick));
								}
								else {
									todoList.add(new MidiData(channel, new NotePoint(channel, key, tick)));
								}
							} 
							else {
								int iTodo = getMidiData(todoList, channel);
								if( iTodo==-1 ) System.out.println("NONONO");
								MidiData thisChannelMidiData = todoList.get(iTodo);
								for( int j=0; j<thisChannelMidiData.notePoints.size(); j++ ) {
									if ( thisChannelMidiData.notePoints.get(j).getKey() == key) {
										NotePoint np = thisChannelMidiData.notePoints.remove(j);
										np.setEnd(tick);
										
										int iPush = getMidiData(midiDataList, channel);
										if( iPush!=-1 ) {
											midiDataList.get(iPush).notePoints.add(np);
										}
										else {
											midiDataList.add(new MidiData(channel, np));
										}
										
										break;
									}
								}
							}
						}
					} 
					else {
//						int data1 = sm.getData1();
//						int data2 = sm.getData2();
						// System.out.println("Data:("+data1+", "+data2+")");
					}
				} 
				else {
					// System.out.println("Other message: " +
					// message.getClass());
					// MetaMessage mm = (MetaMessage)message;
					// mm.getData();
					// System.out.println("mm.getStatus = "+mm.getStatus());
					// System.out.println("mm.getData = "+ mm.getData());
				}
			}

		}
		
//		this.notePoints = (NotePoint[]) notePoints.toArray(new NotePoint[0]);
		
//		this.channelList =  TODO
	}

	private int getMidiData(ArrayList<MidiData> list, int channel) {
		for( int i=0; i<list.size(); i++ ) {
			if( list.get(i).channel == channel ) return i;
		}
		return -1;
	}
	
	public NotePoint[] getAllNotePoints() {
		ArrayList<NotePoint> wholeData = new ArrayList<NotePoint>();
		for( int i=0; i<midiDataList.size(); i++ ) {
			MidiData data = midiDataList.get(i);
			wholeData.addAll(data.notePoints);
		}
		return (NotePoint[]) wholeData.toArray(new NotePoint[0]);
		
	}
	public NotePoint[] getChannelNotePoints(int channel) {
		for( int i=0; i<midiDataList.size(); i++ ) {
			MidiData data = midiDataList.get(i);
			if( data.channel==channel ) {
				return data.getNotePointArray();
			}
		}
		return null;
	}
	public int[] getChannelList() {
		int[] arr = new int[midiDataList.size()];
		for( int i=0; i<arr.length; i++ ) arr[i] = midiDataList.get(i).channel;
		return arr;
	}
	
	private static Boolean isDrum(int channel) {
//		return 9<=channel && channel<=16;
		return channel==10;
	}
	
	private static void printNote(int command, int key, int velocity) {
		String com;
		if (command == CONST_VAR.NOTE_ON)
			com = "NOTE_ON";
		else if (command == CONST_VAR.NOTE_OFF)
			com = "NOTE_OFF";
		else
			com = "ERROR";

		int octave = (key / 12) - 1;
		int note = key % 12;
		String noteName = CONST_VAR.getNoteName(note);
		System.out.println(com + ", " + noteName + octave + " velocity: " + velocity);
	}
}
