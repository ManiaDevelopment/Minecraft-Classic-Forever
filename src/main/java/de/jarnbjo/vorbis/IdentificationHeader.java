/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: IdentificationHeader.java,v 1.3 2003/03/31 00:20:16 jarnbjo Exp $
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
 * $Log: IdentificationHeader.java,v $
 * Revision 1.3  2003/03/31 00:20:16  jarnbjo
 * no message
 *
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */

package de.jarnbjo.vorbis;

import java.io.*;

import de.jarnbjo.util.io.BitInputStream;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 */

public class IdentificationHeader {

   private int version, channels, sampleRate, bitrateMaximum, bitrateNominal, bitrateMinimum, blockSize0, blockSize1;
   private boolean framingFlag;
   private MdctFloat[] mdct=new MdctFloat[2];
   //private MdctLong[] mdctInt=new MdctLong[2];

   private static final long HEADER = 0x736962726f76L; // 'vorbis'

   public IdentificationHeader(BitInputStream source) throws VorbisFormatException, IOException {
      //equalizer=new Equalizer();
      //equalizer.pack();
      //equalizer.show();

      long leading=source.getLong(48);
      if(leading!=HEADER) {
         throw new VorbisFormatException("The identification header has an illegal leading.");
      }
      version=source.getInt(32);
      channels=source.getInt(8);
      sampleRate=source.getInt(32);
      bitrateMaximum=source.getInt(32);
      bitrateNominal=source.getInt(32);
      bitrateMinimum=source.getInt(32);
      int bs=source.getInt(8);
      blockSize0=1<<(bs&0xf);
      blockSize1=1<<(bs>>4);

      mdct[0]=new MdctFloat(blockSize0);
      mdct[1]=new MdctFloat(blockSize1);
      //mdctInt[0]=new MdctLong(blockSize0);
      //mdctInt[1]=new MdctLong(blockSize1);

      framingFlag=source.getInt(8)!=0;
   }

   public int getSampleRate() {
      return sampleRate;
   }

   public int getMaximumBitrate() {
      return bitrateMaximum;
   }

   public int getNominalBitrate() {
      return bitrateNominal;
   }

   public int getMinimumBitrate() {
      return bitrateMinimum;
   }

   public int getChannels() {
      return channels;
   }

   public int getBlockSize0() {
      return blockSize0;
   }

   public int getBlockSize1() {
      return blockSize1;
   }

   protected MdctFloat getMdct0() {
      return mdct[0];
   }

   protected MdctFloat getMdct1() {
      return mdct[1];
   }

   public int getVersion() {
      return version;
   }
}