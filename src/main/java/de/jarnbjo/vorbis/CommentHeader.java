package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.VorbisFormatException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public final class CommentHeader {

   private HashMap comments = new HashMap();


   public CommentHeader(BitInputStream var1) {
	   try
	   {
		   if(var1.getLong(48) != 126896460427126L) {
			   throw new VorbisFormatException("The identification header has an illegal leading.");
		   } else {
			   getString(var1);
			   int var2 = var1.getInt(32);

			   for(int var3 = 0; var3 < var2; ++var3) {
				   String var4;
				   int var5 = (var4 = getString(var1)).indexOf(61);
				   String var6 = var4.substring(0, var5);
				   var4 = var4.substring(var5 + 1);
				   ArrayList var7;
				   if((var7 = (ArrayList)this.comments.get(var6)) == null) {
					   var7 = new ArrayList();
					   this.comments.put(var6, var7);
				   }

				   var7.add(var4);
			   }

			   var1.getInt(8);
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }

   private static String getString(BitInputStream var0) {
	   try
	   {
		   int var1;
		   byte[] var2 = new byte[var1 = var0.getInt(32)];

		   for(int var3 = 0; var3 < var1; ++var3) {
			   var2[var3] = (byte)var0.getInt(8);
		   }

		   return new String(var2, "UTF-8");
	   } catch (UnsupportedEncodingException e) {
		   e.printStackTrace();
	   }

	   return null;
   }
}
