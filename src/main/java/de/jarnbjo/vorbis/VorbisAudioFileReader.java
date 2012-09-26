package de.jarnbjo.vorbis;

/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: VorbisAudioFileReader.java,v 1.1 2003/08/08 19:48:22 jarnbjo Exp $
 * -----------------------------------------------------------
 *
 * $Author: jarnbjo $
 *
 * Description:
 *
 * Copyright 2002-2003 Tor-Einar Jarnbjo
 * -----------------------------------------------------------
 *
 * Change History
 * -----------------------------------------------------------
 * $Log: VorbisAudioFileReader.java,v $
 *
 */
 
import java.io.*;
import java.net.*;
import java.util.*;

import de.jarnbjo.ogg.*;
import de.jarnbjo.vorbis.*;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.AudioFileReader;

public class VorbisAudioFileReader extends AudioFileReader {
	
	public VorbisAudioFileReader() {
	}
	
	public AudioFileFormat getAudioFileFormat(File file) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioFileFormat(new FileStream(new RandomAccessFile(file, "r")));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}
	}
	
	public AudioFileFormat getAudioFileFormat(InputStream stream) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioFileFormat(new BasicStream(stream));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}		
	}
	
	public AudioFileFormat getAudioFileFormat(URL url) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioFileFormat(new UncachedUrlStream(url));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}		
	}
	
	private AudioFileFormat getAudioFileFormat(PhysicalOggStream oggStream)  throws IOException, UnsupportedAudioFileException {
		try {
			Collection streams=oggStream.getLogicalStreams();
			if(streams.size()!=1) {
				throw new UnsupportedAudioFileException("Only Ogg files with one logical Vorbis stream are supported.");	
			}
			
			LogicalOggStream los=(LogicalOggStream)streams.iterator().next();
			if(los.getFormat()!=LogicalOggStream.FORMAT_VORBIS) {
				throw new UnsupportedAudioFileException("Only Ogg files with one logical Vorbis stream are supported.");	
			}

			VorbisStream vs=new VorbisStream(los);
			
	        AudioFormat audioFormat=new AudioFormat(
            	(float)vs.getIdentificationHeader().getSampleRate(),
            	16,
            	vs.getIdentificationHeader().getChannels(),
            	true, true);
			
			return new AudioFileFormat(VorbisFormatType.getInstance(), audioFormat, AudioSystem.NOT_SPECIFIED);
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());
		}
		catch(VorbisFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());
		}
	}
	
	
	
	public AudioInputStream getAudioInputStream(File file) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioInputStream(new FileStream(new RandomAccessFile(file, "r")));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}
	}
	
	public AudioInputStream getAudioInputStream(InputStream stream) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioInputStream(new BasicStream(stream));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}		
	}
	
	public AudioInputStream getAudioInputStream(URL url) throws IOException, UnsupportedAudioFileException {
		try {
			return getAudioInputStream(new UncachedUrlStream(url));	
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());	
		}		
	}
	
	private AudioInputStream getAudioInputStream(PhysicalOggStream oggStream)  throws IOException, UnsupportedAudioFileException {
		try {
			Collection streams=oggStream.getLogicalStreams();
			if(streams.size()!=1) {
				throw new UnsupportedAudioFileException("Only Ogg files with one logical Vorbis stream are supported.");	
			}
			
			LogicalOggStream los=(LogicalOggStream)streams.iterator().next();
			if(los.getFormat()!=LogicalOggStream.FORMAT_VORBIS) {
				throw new UnsupportedAudioFileException("Only Ogg files with one logical Vorbis stream are supported.");	
			}

			VorbisStream vs=new VorbisStream(los);

	        AudioFormat audioFormat=new AudioFormat(
            	(float)vs.getIdentificationHeader().getSampleRate(),
            	16,
            	vs.getIdentificationHeader().getChannels(),
            	true, true);
			
         	return new AudioInputStream(new VorbisInputStream(vs), audioFormat, -1);
		}
		catch(OggFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());
		}
		catch(VorbisFormatException e) {
			throw new UnsupportedAudioFileException(e.getMessage());
		}
	}
	
	
	public static class VorbisFormatType extends AudioFileFormat.Type {
		
		private static final VorbisFormatType instance=new VorbisFormatType();
		
		private VorbisFormatType() {
			super("VORBIS", "ogg");
		}
		
		public static AudioFileFormat.Type getInstance() {
			return instance;
		}
	}
	
   	public static class VorbisInputStream extends InputStream {

      	private VorbisStream source;
      	private byte[] buffer=new byte[8192];

      	public VorbisInputStream(VorbisStream source) {
         	this.source=source;
      	}

      	public int read() throws IOException {
         	return 0;
      	}

      	public int read(byte[] buffer) throws IOException {
         	return read(buffer, 0, buffer.length);
      	}

      	public int read(byte[] buffer, int offset, int length) throws IOException {
         	try {
            	return source.readPcm(buffer, offset, length);
         	}
         	catch(EndOfOggStreamException e) {
            	return -1;
         	}
      	}
   	}

	
}