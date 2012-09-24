package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.CodeBook;
import de.jarnbjo.vorbis.Floor;
import de.jarnbjo.vorbis.Mapping;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

final class SetupHeader {

   CodeBook[] codeBooks;
   Floor[] floors;
   Residue[] residues;
   Mapping[] mappings;
   Mode[] modes;


   public SetupHeader(VorbisStream var1, BitInputStream var2) {
	   try
	   {
		   if(var2.getLong(48) != 126896460427126L) {
			   throw new VorbisFormatException("The setup header has an illegal leading.");
		   } else {
			   int var3 = var2.getInt(8) + 1;
			   this.codeBooks = new CodeBook[var3];

			   for(var3 = 0; var3 < this.codeBooks.length; ++var3) {
				   this.codeBooks[var3] = new CodeBook(var2);
			   }

			   var3 = var2.getInt(6) + 1;

			   int var4;
			   for(var4 = 0; var4 < var3; ++var4) {
				   if(var2.getInt(16) != 0) {
					   throw new VorbisFormatException("Time domain transformation != 0");
				   }
			   }

			   var4 = var2.getInt(6) + 1;
			   this.floors = new Floor[var4];

			   for(var3 = 0; var3 < var4; ++var3) {
				   this.floors[var3] = Floor.createInstance(var2, this);
			   }

			   var3 = var2.getInt(6) + 1;
			   this.residues = new Residue[var3];

			   for(var4 = 0; var4 < var3; ++var4) {
				   this.residues[var4] = Residue.createInstance(var2, this);
			   }

			   var4 = var2.getInt(6) + 1;
			   this.mappings = new Mapping[var4];

			   for(var3 = 0; var3 < var4; ++var3) {
				   this.mappings[var3] = Mapping.createInstance(var1, var2, this);
			   }

			   var3 = var2.getInt(6) + 1;
			   this.modes = new Mode[var3];

			   for(int var5 = 0; var5 < var3; ++var5) {
				   this.modes[var5] = new Mode(var2, this);
			   }

			   if(!var2.getBit()) {
				   throw new VorbisFormatException("The setup header framing bit is incorrect.");
			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }
}
