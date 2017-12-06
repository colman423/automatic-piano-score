import java.io.File;
import java.util.Arrays;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

public class WriteMIDI {

	static final int NOTE_ON = 0x90;
	static final int NOTE_OFF = 0x80;
	static final int MEASURE = 960;

	public static void write(long songTime, int speed, SecondaryChord[] chordSequence, NotePoint[] melodyNotePoints, int base, String outPath) {
		try {
			Sequence seq = new Sequence(javax.sound.midi.Sequence.PPQ, 480);
			Track t = initialWritingTrack(seq, speed); // intial track
			writeChord(chordSequence, base, t); // write chord
			writeMelody(melodyNotePoints, t);	// write melody
			writeFile(seq, t, (songTime + MEASURE), outPath);
			
			new ProcessBuilder(CONST_VAR.MUSE_SCORE, outPath).start();		
		} // try
		catch (Exception e) {
			System.out.println("write Exception caught " + e.toString());
		} // catch
	}

	private static Track initialWritingTrack(Sequence seq, int speed) {
		try {
			Track t = seq.createTrack();
			// **** General MIDI sysex -- turn on General MIDI sound set ****
			SysexMessage sm = new SysexMessage();
			byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
			sm.setMessage(b, 6);
			MidiEvent me = new MidiEvent(sm, (long) 0);
			t.add(me);

			// **** set tempo (meta event) ****
			MetaMessage mt = new MetaMessage();
			byte[] bt = getTempoByte(speed);
			mt.setMessage(0x51, bt, 3);
			me = new MidiEvent(mt, (long) 0);
			t.add(me);
			
			// **** set track name (meta event) ****
			mt = new MetaMessage();
			String TrackName = new String("midifile track");
			mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt, (long) 0);
			t.add(me);

			// **** set omni on ****
			ShortMessage mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set poly on ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set instrument to Piano ****
			mm = new ShortMessage();
			mm.setMessage(0xC0, 0x29, 0x29);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			return t;
		} catch (Exception e) {
			System.out.println("initialWritingTrack Exception caught " + e.toString());

			return null;
		} // catch

	}

	private static byte[] getTempoByte(int speed) {
		int num = 60000000/speed;
		byte cByte =  (byte) (num%256);
		num /= 256;
		byte bByte = (byte) (num%256);
		byte aByte = (byte) ((num/256)%256);
		return new byte[]{aByte, bByte, cByte};
	}

	private static void writeMelody(NotePoint[] melodyNotePoints, Track t) {
		try {
			ShortMessage mm;
			MidiEvent me;
			for( NotePoint np : melodyNotePoints ) {
				mm = new ShortMessage(NOTE_ON, 10, np.getKey(), 0x60);
				me = new MidiEvent(mm, np.getStart());
				t.add(me);
				mm = new ShortMessage(NOTE_OFF, 10, np.getKey(), 0x00);
				me = new MidiEvent(mm, np.getEnd());
				t.add(me);
				mm = new ShortMessage(NOTE_ON, 10, np.getKey()+12, 0x60);
				me = new MidiEvent(mm, np.getStart());
				t.add(me);
				mm = new ShortMessage(NOTE_OFF, 10, np.getKey()+12, 0x00);
				me = new MidiEvent(mm, np.getEnd());
				t.add(me);
			}
//			for (int i = 0; i < count + 1; j++) {
//				try {
//					// **** note on****
//					mm = new ShortMessage(NOTE_ON, 10, base + note, 0x60);
//					me = new MidiEvent(mm, time);
//					t.add(me);
//
//					time += 120;
//					// **** note off 120 ticks later ****
//					mm = new ShortMessage(NOTE_OFF, 10, base + note, 0x00);
//					me = new MidiEvent(mm, time);
//					t.add(me);
//				} catch (Exception e) {
//					System.out.println("writeChord Exception caught " + e.toString());
//				}
//
//				note = sc.nextNoteGlobalIndex(note);
//			}
		} catch (Exception e) {
			System.out.println("writeMelody Exception caught " + e.toString());

			return;
		} // catch
		
	}

	private static void writeChord(SecondaryChord[] chordSequence, int base, Track t) {
		for (int i = 0; i < chordSequence.length; i++) {
			SecondaryChord sc = chordSequence[i];
			if (sc != null) {
				ShortMessage mm;
				MidiEvent me;
				long time = i * MEASURE;
				int root = sc.rootNote;
				int note = root;

				int count = sc.noteCount();
				count *= 8 / count;

				for (int j = 0; j < count + 1; j++) {
					try {
						// **** note on****
						mm = new ShortMessage(NOTE_ON, 10, base + note, 0x60);
						me = new MidiEvent(mm, time);
						t.add(me);

						time += 120;
						// **** note off 120 ticks later ****
						mm = new ShortMessage(NOTE_OFF, 10, base + note, 0x00);
						me = new MidiEvent(mm, time);
						t.add(me);
					} catch (Exception e) {
						System.out.println("writeChord Exception caught " + e.toString());
					}

					note = sc.nextNoteGlobalIndex(note);
				}

			}
		}
	}

	private static void writeFile(Sequence s, Track t, long endTime, String outPath) {
		System.out.println(outPath);
		try {
			// **** set end of track (meta event) 19 ticks later ****
			MetaMessage mt = new MetaMessage();
			byte[] bet = {}; // empty array
			mt.setMessage(0x2F, bet, 0);
			MidiEvent me = new MidiEvent(mt, endTime);
			t.add(me);

			// **** write the MIDI sequence to a MIDI file ****
			File f = new File(outPath);
			MidiSystem.write(s, 1, f);
		} catch (Exception e) {
			System.out.println("writeFile Exception caught " + e.toString());
		} // catch

	}
}
