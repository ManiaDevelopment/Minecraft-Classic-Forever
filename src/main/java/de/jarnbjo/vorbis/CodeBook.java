package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.util.io.HuffmanNode;
import de.jarnbjo.vorbis.Util;
import de.jarnbjo.vorbis.VorbisFormatException;
import java.util.Arrays;

final class CodeBook {

   HuffmanNode huffmanRoot;
   int dimensions;
   private int entries;
   private int[] entryLengths;
   float[][] valueVector;


   protected CodeBook(BitInputStream var1) {
	   try
	   {
		   if(var1.getInt(24) != 5653314) {
			   throw new VorbisFormatException("The code book sync pattern is not correct.");
		   } else {
			   this.dimensions = var1.getInt(16);
			   this.entries = var1.getInt(24);
			   this.entryLengths = new int[this.entries];
			   int var2;
			   int var3;
			   if(var1.getBit()) {
				   var2 = var1.getInt(5) + 1;

				   int var4;
				   for(var3 = 0; var3 < this.entryLengths.length; var3 += var4) {
					   var4 = var1.getInt(Util.ilog(this.entryLengths.length - var3));
					   if(var3 + var4 > this.entryLengths.length) {
						   throw new VorbisFormatException("The codebook entry length list is longer than the actual number of entry lengths.");
					   }

					   Arrays.fill(this.entryLengths, var3, var3 + var4, var2);
					   ++var2;
				   }
			   } else if(var1.getBit()) {
				   for(var3 = 0; var3 < this.entryLengths.length; ++var3) {
					   if(var1.getBit()) {
						   this.entryLengths[var3] = var1.getInt(5) + 1;
					   } else {
						   this.entryLengths[var3] = -1;
					   }
				   }
			   } else {
				   for(var3 = 0; var3 < this.entryLengths.length; ++var3) {
					   this.entryLengths[var3] = var1.getInt(5) + 1;
				   }
			   }

			   if(!this.createHuffmanTree(this.entryLengths)) {
				   throw new VorbisFormatException("An exception was thrown when building the codebook Huffman tree.");
			   } else {
				   switch(var2 = var1.getInt(4)) {
					   case 0:
						   return;
					   case 1:
					   case 2:
						   float var14 = Util.float32unpack(var1.getInt(32));
						   float var13 = Util.float32unpack(var1.getInt(32));
						   int var5 = var1.getInt(4) + 1;
						   boolean var6 = var1.getBit();
						   boolean var7 = false;
						   int var9;
						   int var10;
						   int var15;
						   if(var2 == 1) {
							   int var8 = this.dimensions;
							   var15 = this.entries;
							   int var10000 = (var9 = (int)Math.pow(2.718281828459045D, Math.log((double)var15) / (double)var8)) + 1;
							   var10 = var8;
							   var8 = var10000;

							   int var11;
							   for(var11 = 1; var10 > 0; var11 *= var8) {
								   --var10;
							   }

							   var15 = var11 <= var15?var9 + 1:var9;
						   } else {
							   var15 = this.entries * this.dimensions;
						   }

						   int[] var16 = new int[var15];

						   for(var9 = 0; var9 < var16.length; ++var9) {
							   var16[var9] = var1.getInt(var5);
						   }

						   this.valueVector = new float[this.entries][this.dimensions];
						   if(var2 != 1) {
							   throw new UnsupportedOperationException();
						   } else {
							   for(var9 = 0; var9 < this.entries; ++var9) {
								   float var12 = 0.0F;
								   var2 = 1;

								   for(var5 = 0; var5 < this.dimensions; ++var5) {
									   var10 = var9 / var2 % var15;
									   this.valueVector[var9][var5] = (float)var16[var10] * var13 + var14 + var12;
									   if(var6) {
										   var12 = this.valueVector[var9][var5];
									   }

									   var2 *= var15;
								   }
							   }

							   return;
						   }
					   default:
						   throw new VorbisFormatException("Unsupported codebook lookup type: " + var2);
				   }
			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }

   private boolean createHuffmanTree(int[] var1) {
      this.huffmanRoot = new HuffmanNode();

      for(int var2 = 0; var2 < var1.length; ++var2) {
         int var3;
         if((var3 = var1[var2]) > 0 && !this.huffmanRoot.setNewValue(var3, var2)) {
            return false;
         }
      }

      return true;
   }
}
