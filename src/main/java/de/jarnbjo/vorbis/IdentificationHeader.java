package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.MdctFloat;
import de.jarnbjo.vorbis.VorbisFormatException;

public final class IdentificationHeader {

   public int channels;
   public int samplerate;
   int blockSize0;
   int blockSize1;
   MdctFloat[] mdct = new MdctFloat[2];


   public IdentificationHeader(BitInputStream var1) {
	   try
	   {
		   if(var1.getLong(48) != 126896460427126L) {
			   throw new VorbisFormatException("The identification header has an illegal leading.");
		   } else {
			   var1.getInt(32);
			   this.channels = var1.getInt(8);
			   this.samplerate = var1.getInt(32);
			   var1.getInt(32);
			   var1.getInt(32);
			   var1.getInt(32);
			   int var4 = var1.getInt(8);
			   this.blockSize0 = 1 << (var4 & 15);
			   this.blockSize1 = 1 << (var4 >> 4);
			   this.mdct[0] = new MdctFloat(this.blockSize0);
			   this.mdct[1] = new MdctFloat(this.blockSize1);
			   var1.getInt(8);
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }
}
