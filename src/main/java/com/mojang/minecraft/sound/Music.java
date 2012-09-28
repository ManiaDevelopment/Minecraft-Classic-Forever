package com.mojang.minecraft.sound;

import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.ogg.OggFormatException;
import de.jarnbjo.ogg.OnDemandUrlStream;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;

// TODO.
public final class Music implements Audio {

   ByteBuffer playing = ByteBuffer.allocate(176400);
   ByteBuffer current = ByteBuffer.allocate(176400);
   private ByteBuffer processing = null;
   ByteBuffer previous = null;
   VorbisStream stream;
   SoundPlayer player;
   boolean finished = false;
   boolean stopped = false;


   public Music(SoundPlayer var1, URL var2) {
      this.player = var1;
	   try
	   {
		   LogicalOggStreamImpl var3 = (LogicalOggStreamImpl)(new OnDemandUrlStream(var2)).getLogicalStreams().iterator().next();
		   this.stream = new VorbisStream(var3);
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   } catch (OggFormatException e) {
		   e.printStackTrace();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
	   (new MusicPlayThread(this)).start();
   }

   public final boolean play(int[] var1, int[] var2, int var3) {
      if(!this.player.settings.music) {
         this.stopped = true;
         return false;
      } else {
         var3 = var3;
         int var4 = 0;

         while(var3 > 0 && (this.processing != null || this.previous != null)) {
            if(this.processing == null && this.previous != null) {
               this.processing = this.previous;
               this.previous = null;
            }

            if(this.processing != null && this.processing.remaining() > 0) {
               int var5;
               if((var5 = this.processing.remaining() / 4) > var3) {
                  var5 = var3;
               }

               for(int var6 = 0; var6 < var5; ++var6) {
                  var1[var4 + var6] += this.processing.getShort();
                  var2[var4 + var6] += this.processing.getShort();
               }

               var4 += var5;
               var3 -= var5;
            }

            if(this.current == null && this.processing != null && this.processing.remaining() == 0) {
               this.current = this.processing;
               this.processing = null;
            }
         }

         return this.processing != null || this.previous != null || !this.finished;
      }
   }
}
