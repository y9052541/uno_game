package myuno;

import java.io.*;

import javax.sound.midi.*;
import javax.swing.JOptionPane;

public class mymusic{
	private Sequencer midi;
	
	private boolean started;
	
	public mymusic ()
	{
		started = false;
	}
	
	public void init()
	{
		try {
            midi = MidiSystem.getSequencer();
            midi.open();
			Sequence se = MidiSystem.getSequence(new File("bgm.mid"));
			Thread.sleep(1000);
			midi.setSequence(se);
            midi.setLoopStartPoint(0);
            midi.setLoopEndPoint(-1);
            midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            midi.start();
            started = true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "±≥æ∞“Ù¿÷≤•∑≈ ß∞‹£°", "¥ÌŒÛ", JOptionPane.ERROR_MESSAGE);
			started = false;
		}
	}
	
	public void changestate()
	{
		if (started)
		{				
			if (midi.isRunning())
				midi.stop();
			else
				midi.start();
		}
	}
	
}

