/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Mode.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
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
 * $Log: Mode.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */
 
package de.jarnbjo.vorbis;

import java.io.*;

import de.jarnbjo.util.io.*;

class Mode {

   private boolean blockFlag;
   private int windowType, transformType, mapping;

   protected Mode(BitInputStream source, SetupHeader header) throws VorbisFormatException, IOException {
      blockFlag=source.getBit();
      windowType=source.getInt(16);
      transformType=source.getInt(16);
      mapping=source.getInt(8);

      if(windowType!=0) {
         throw new VorbisFormatException("Window type = "+windowType+", != 0");
      }

      if(transformType!=0) {
         throw new VorbisFormatException("Transform type = "+transformType+", != 0");
      }

      if(mapping>header.getMappings().length) {
         throw new VorbisFormatException("Mode mapping number is higher than total number of mappings.");
      }
   }

   protected boolean getBlockFlag() {
      return blockFlag;
   }

   protected int getWindowType() {
      return windowType;
   }

   protected int getTransformType() {
      return transformType;
   }

   protected int getMapping() {
      return mapping;
   }
}