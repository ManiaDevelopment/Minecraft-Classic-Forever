/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Mapping.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
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
 * $Log: Mapping.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */
 
package de.jarnbjo.vorbis;

import java.io.IOException;

import de.jarnbjo.util.io.BitInputStream;

abstract class Mapping {

   protected static Mapping createInstance(VorbisStream vorbis, BitInputStream source, SetupHeader header) throws VorbisFormatException, IOException {

      int type=source.getInt(16);
      switch(type) {
      case 0:
         //System.out.println("mapping type 0");
         return new Mapping0(vorbis, source, header);
      default:
         throw new VorbisFormatException("Mapping type "+type+" is not supported.");
      }
   }

   protected abstract int getType();
   protected abstract int[] getAngles();
   protected abstract int[] getMagnitudes() ;
   protected abstract int[] getMux();
   protected abstract int[] getSubmapFloors();
   protected abstract int[] getSubmapResidues();
   protected abstract int getCouplingSteps();
   protected abstract int getSubmaps();

}