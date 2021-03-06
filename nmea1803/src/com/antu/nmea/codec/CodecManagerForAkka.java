package com.antu.nmea.codec;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.UntypedActor;

import com.antu.nmea.codec.exception.MessageFieldCodecNotFoundException;
import com.antu.nmea.codec.exception.SentenceCodecNotFoundException;
import com.antu.nmea.codec.exception.SentenceFieldCodecNotFoundException;
import com.antu.nmea.sentence.EncapsulationSentence;
import com.antu.nmea.sentence.INmeaSentence;
import com.antu.nmea.sentence.MultiSentenceTable;
import com.antu.nmea.sentence.ParametricSentence;
import com.antu.nmea.util.Buffer;
import com.antu.nmea.util.StringHelper;
import com.antu.util.GenericFactory;

/**
 * 
 * @author yining
 *
 * CodecManager is the entry point for all encoding and decoding of NMEA sentences from a single source,
 * ie. each CodecManager should be instantiated and configured for each source generating NMEA messages,
 * may it be a server socket, a client socket, a file or a serial port.
 * 
 * A CodecManager implements Observer and extends Observable, it gets notified from underlying
 * AbstractNmeaSentenceCodecs(Observable) when a sentence is fully decoded. Programs using a CodecManager
 * should implement Oberver interface and add to this Observable to receive decoded sentence objects.
 */
public class CodecManagerForAkka extends UntypedActor {
	private CodecManager manager;
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
