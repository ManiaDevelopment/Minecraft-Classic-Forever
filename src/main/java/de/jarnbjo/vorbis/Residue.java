package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Look;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue0;
import de.jarnbjo.vorbis.Residue2;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;
import java.util.HashMap;

abstract class Residue {

   protected int begin;
   private int end;
   private int partitionSize;
   private int classifications;
   private int classBook;
   protected int[] cascade;
   private int[][] books;
   private HashMap looks = new HashMap();


   protected Residue() {}

   protected Residue(BitInputStream var1, SetupHeader var2) {
	   try
	   {
		   this.begin = var1.getInt(24);
		   this.end = var1.getInt(24);
		   this.partitionSize = var1.getInt(24) + 1;
		   this.classifications = var1.getInt(6) + 1;
		   this.classBook = var1.getInt(8);
		   this.cascade = new int[this.classifications];

		   int var3;
		   int var4;
		   for(var3 = 0; var3 < this.classifications; ++var3) {
			   var4 = 0;
			   boolean var5 = false;
			   int var6 = var1.getInt(3);
			   if(var1.getBit()) {
				   var4 = var1.getInt(5);
			   }

			   this.cascade[var3] = var4 << 3 | var6;
		   }

		   this.books = new int[this.classifications][8];

		   for(var3 = 0; var3 < this.classifications; ++var3) {
			   for(var4 = 0; var4 < 8; ++var4) {
				   if((this.cascade[var3] & 1 << var4) != 0) {
					   this.books[var3][var4] = var1.getInt(8);
					   if(this.books[var3][var4] > var2.codeBooks.length) {
						   throw new VorbisFormatException("Reference to invalid codebook entry in residue header.");
					   }
				   }
			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }

   }

   protected static Residue createInstance(BitInputStream var0, SetupHeader var1) {
	   try
	   {
		   int var2;
		   switch(var2 = var0.getInt(16)) {
			   case 0:
				   return new Residue0(var0, var1);
			   case 1:
				   return new Residue2(var0, var1);
			   case 2:
				   return new Residue2(var0, var1);
			   default:
				   throw new VorbisFormatException("Residue type " + var2 + " is not supported.");
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }

	   return null;
   }

   protected abstract void decodeResidue(VorbisStream var1, BitInputStream var2, Mode var3, boolean[] var4, float[][] var5);

   protected final int getBegin() {
      return this.begin;
   }

   protected final int getEnd() {
      return this.end;
   }

   protected final int getPartitionSize() {
      return this.partitionSize;
   }

   protected final int getClassifications() {
      return this.classifications;
   }

   protected final int getClassBook() {
      return this.classBook;
   }

   protected final int[] getCascade() {
      return this.cascade;
   }

   protected final int[][] getBooks() {
      return this.books;
   }

   protected final void fill(Residue var1) {
      var1.begin = this.begin;
      var1.books = this.books;
      var1.cascade = this.cascade;
      var1.classBook = this.classBook;
      var1.classifications = this.classifications;
      var1.end = this.end;
      var1.partitionSize = this.partitionSize;
   }

   protected final Look getLook(VorbisStream var1, Mode var2) {
      Look var3;
      if((var3 = (Look)this.looks.get(var2)) == null) {
         var3 = new Look(this, var1, var2);
         this.looks.put(var2, var3);
      }

      return var3;
   }
}
