/*
 * $ProjectName$
 * $ProjectRevision$
 * -----------------------------------------------------------
 * $Id: Floor0.java,v 1.2 2003/03/16 01:11:12 jarnbjo Exp $
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
 * $Log: Floor0.java,v $
 * Revision 1.2  2003/03/16 01:11:12  jarnbjo
 * no message
 *
 *
 */
 
package de.jarnbjo.vorbis;

import java.io.IOException;

import de.jarnbjo.util.io.BitInputStream;

class Floor0 extends Floor {

   private int order, rate, barkMapSize, amplitudeBits, amplitudeOffset;
   private int bookList[];

   protected Floor0(BitInputStream source, SetupHeader header) throws VorbisFormatException, IOException {

      order=source.getInt(8);
      rate=source.getInt(16);
      barkMapSize=source.getInt(16);
      amplitudeBits=source.getInt(6);
      amplitudeOffset=source.getInt(8);

      int bookCount=source.getInt(4)+1;
      bookList=new int[bookCount];

      for(int i=0; i<bookList.length; i++) {
         bookList[i]=source.getInt(8);
         if(bookList[i]>header.getCodeBooks().length) {
            throw new VorbisFormatException("A floor0_book_list entry is higher than the code book count.");
         }
      }
   }

   protected int getType() {
      return 0;
   }

   protected Floor decodeFloor(VorbisStream vorbis, BitInputStream source) throws VorbisFormatException, IOException {
      /** @todo implement */
      throw new UnsupportedOperationException();
   }

   protected void computeFloor(float[] vector) {
      /** @todo implement */
      throw new UnsupportedOperationException();
   }

}