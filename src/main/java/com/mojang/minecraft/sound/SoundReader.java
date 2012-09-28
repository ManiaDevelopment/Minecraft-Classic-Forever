package com.mojang.minecraft.sound;

import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.ogg.OggFormatException;
import de.jarnbjo.ogg.OnDemandUrlStream;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

import java.io.IOException;
import java.net.URL;

// TODO.
public final class SoundReader {

   public static SoundData read(URL var0) {
	   VorbisStream var12 = null;
	   try
	   {
		   LogicalOggStreamImpl var11 = (LogicalOggStreamImpl)(new OnDemandUrlStream(var0)).getLogicalStreams().iterator().next();
		   var12 = new VorbisStream(var11);
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   } catch (OggFormatException e) {
		   e.printStackTrace();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   byte[] var2 = new byte[4096];
      int var3 = 0;
      boolean var1 = false;
      IdentificationHeader var14 = var12.getIdentificationHeader();
      int var4 = var12.getIdentificationHeader().getChannels();
      short[] var5 = new short[4096];
      int var6 = 0;

      label51:
      while(var3 >= 0) {
         int var15 = 0;

         while(true) {
            try {
               if(var15 < var2.length && (var3 = var12.readPcm(var2, var15, var2.length - var15)) > 0) {
                  var15 += var3;
                  continue;
               }
            } catch (Exception var10) {
               var3 = -1;
            }

            if(var15 <= 0) {
               break;
            }

            boolean var7 = false;
            int var16 = 0;

            while(true) {
               if(var16 >= var15) {
                  continue label51;
               }

               int var8 = 0;

               for(int var9 = 0; var9 < var4; ++var9) {
                  var8 += var2[var16++] << 8 | var2[var16++] & 255;
               }

               if(var6 == var5.length) {
                  short[] var18 = var5;
                  var5 = new short[var5.length << 1];
                  System.arraycopy(var18, 0, var5, 0, var6);
               }

               var5[var6++] = (short)(var8 / var4);
            }
         }
      }

      if(var6 != var5.length) {
         short[] var17 = var5;
         var5 = new short[var6];
         System.arraycopy(var17, 0, var5, 0, var6);
      }

      IdentificationHeader var13;
      return new SoundData(var5, (float)(var13 = var12.getIdentificationHeader()).getSampleRate());
   }
}
