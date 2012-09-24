package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Floor;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

final class Floor0 extends Floor {

   private int[] bookList;


   protected Floor0(BitInputStream var1, SetupHeader var2) {
	   try
	   {
		   var1.getInt(8);
		   var1.getInt(16);
		   var1.getInt(16);
		   var1.getInt(6);
		   var1.getInt(8);
		   int var3 = var1.getInt(4) + 1;
		   this.bookList = new int[var3];

		   for(var3 = 0; var3 < this.bookList.length; ++var3) {
			   this.bookList[var3] = var1.getInt(8);
			   if(this.bookList[var3] > var2.codeBooks.length) {
				   throw new VorbisFormatException("A floor0_book_list entry is higher than the code book count.");
			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }

   }

   protected final Floor decodeFloor(VorbisStream var1, BitInputStream var2) {
      throw new UnsupportedOperationException();
   }

   protected final void computeFloor(float[] var1) {
      throw new UnsupportedOperationException();
   }
}
