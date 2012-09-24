package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Mapping;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.Util;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

final class Mapping0 extends Mapping {

   private int[] magnitudes;
   private int[] angles;
   private int[] mux;
   private int[] submapFloors;
   private int[] submapResidues;


   protected Mapping0(VorbisStream var1, BitInputStream var2, SetupHeader var3) {
	   try
	   {
		   int var4 = 1;
		   if(var2.getBit()) {
			   var4 = var2.getInt(4) + 1;
		   }

		   int var8;
		   int var5 = Util.ilog((var8 = var1.identificationHeader.channels) - 1);
		   int var6;
		   int var7;
		   if(var2.getBit()) {
			   var6 = var2.getInt(8) + 1;
			   this.magnitudes = new int[var6];
			   this.angles = new int[var6];

			   for(var7 = 0; var7 < var6; ++var7) {
				   this.magnitudes[var7] = var2.getInt(var5);
				   this.angles[var7] = var2.getInt(var5);
				   if(this.magnitudes[var7] == this.angles[var7] || this.magnitudes[var7] >= var8 || this.angles[var7] >= var8) {
					   System.err.println(this.magnitudes[var7]);
					   System.err.println(this.angles[var7]);
					   throw new VorbisFormatException("The channel magnitude and/or angle mismatch.");
				   }
			   }
		   } else {
			   this.magnitudes = new int[0];
			   this.angles = new int[0];
		   }

		   if(var2.getInt(2) != 0) {
			   throw new VorbisFormatException("A reserved mapping field has an invalid value.");
		   } else {
			   this.mux = new int[var8];
			   if(var4 > 1) {
				   for(var6 = 0; var6 < var8; ++var6) {
					   this.mux[var6] = var2.getInt(4);
					   if(this.mux[var6] > var4) {
						   throw new VorbisFormatException("A mapping mux value is higher than the number of submaps");
					   }
				   }
			   } else {
				   for(var6 = 0; var6 < var8; ++var6) {
					   this.mux[var6] = 0;
				   }
			   }

			   this.submapFloors = new int[var4];
			   this.submapResidues = new int[var4];
			   var6 = var3.floors.length;
			   var7 = var3.residues.length;

			   for(var8 = 0; var8 < var4; ++var8) {
				   var2.getInt(8);
				   this.submapFloors[var8] = var2.getInt(8);
				   this.submapResidues[var8] = var2.getInt(8);
				   if(this.submapFloors[var8] > var6) {
					   throw new VorbisFormatException("A mapping floor value is higher than the number of floors.");
				   }

				   if(this.submapResidues[var8] > var7) {
					   throw new VorbisFormatException("A mapping residue value is higher than the number of residues.");
				   }
			   }

		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }

   protected final int[] getAngles() {
      return this.angles;
   }

   protected final int[] getMagnitudes() {
      return this.magnitudes;
   }

   protected final int[] getMux() {
      return this.mux;
   }

   protected final int[] getSubmapFloors() {
      return this.submapFloors;
   }

   protected final int[] getSubmapResidues() {
      return this.submapResidues;
   }

   protected final int getCouplingSteps() {
      return this.angles.length;
   }

   protected final int getSubmaps() {
      return this.submapFloors.length;
   }
}
