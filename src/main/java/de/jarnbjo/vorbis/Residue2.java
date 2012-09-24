package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.CodeBook;
import de.jarnbjo.vorbis.Look;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

final class Residue2 extends Residue {

   private Residue2() {}

   protected Residue2(BitInputStream var1, SetupHeader var2) {
      super(var1, var2);
   }

   protected final void decodeResidue(VorbisStream var1, BitInputStream var2, Mode var3, boolean[] var4, float[][] var5) {
	   try
	   {
		   Look var22 = this.getLook(var1, var3);
		   int var6 = (this.getEnd() - this.getBegin()) / this.getPartitionSize();
		   int var7 = this.getPartitionSize();
		   CodeBook var8 = var22.phrasebook;
		   int var9 = var22.phrasebook.dimensions;
		   int var10 = (var6 + var9 - 1) / var9;
		   int var11 = 0;

		   for(int var12 = 0; var12 < var4.length; ++var12) {
			   if(!var4[var12]) {
				   ++var11;
			   }
		   }

		   float[][] var26 = new float[var11][];
		   var11 = 0;

		   for(int var13 = 0; var13 < var4.length; ++var13) {
			   if(!var4[var13]) {
				   var26[var11++] = var5[var13];
			   }
		   }

		   int[][] var27 = new int[var10][];

		   for(int var23 = 0; var23 < var22.parts; ++var23) {
			   int var24 = 0;

			   for(var10 = 0; var24 < var6; ++var10) {
				   if(var23 == 0) {
					   var8 = var22.phrasebook;
					   if((var11 = var2.getInt(var22.phrasebook.huffmanRoot)) == -1) {
						   throw new VorbisFormatException("");
					   }

					   var27[var10] = var22.decodemap[var11];
					   if(var27[var10] == null) {
						   throw new VorbisFormatException("");
					   }
				   }

				   for(var11 = 0; var11 < var9 && var24 < var6; ++var24) {
					   int var14 = this.begin + var24 * var7;
					   if((this.cascade[var27[var10][var11]] & 1 << var23) != 0) {
						   SetupHeader var25 = var1.setupHeader;
						   if((var8 = var1.setupHeader.codeBooks[var22.partbooks[var27[var10][var11]][var23]]) != null) {
							   BitInputStream var17 = var2;
							   float[][] var28 = var26;
							   var8 = var8;
							   int var18 = 0;
							   int var19;
							   if((var19 = var26.length) != 0) {
								   int var20 = (var14 + var7) / var19;
								   int var16 = var14 / var19;

								   while(var16 < var20) {
									   float[] var21 = var8.valueVector[var17.getInt(var8.huffmanRoot)];

									   for(int var15 = 0; var15 < var8.dimensions; ++var15) {
										   int var10001 = var18++;
										   var28[var10001][var16] += var21[var15];
										   if(var18 == var19) {
											   var18 = 0;
											   ++var16;
										   }
									   }
								   }
							   }
						   }
					   }

					   ++var11;
				   }
			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }

   }

   public final Object clone() {
      Residue2 var1 = new Residue2();
      this.fill(var1);
      return var1;
   }
}
