package com.mojang.minecraft.sound;

import de.jarnbjo.vorbis.VorbisStream;

import java.nio.ByteBuffer;

// TODO.
final class MusicPlayThread extends Thread {

   // $FF: synthetic field
   private Music music;


   public MusicPlayThread(Music var1) {
	   super();
      this.music = var1;
      this.setPriority(10);
      this.setDaemon(true);
   }

   public final void run() {
      try {
         do {
            if(this.music.stopped) {
               return;
            }

            Music var1 = this.music;
            ByteBuffer var2;
            Music var10001;
            if(this.music.playing == null) {
               var1 = this.music;
               if(this.music.current != null) {
                  var1 = this.music;
                  var2 = this.music.current;
                  var10001 = this.music;
                  this.music.playing = var2;
                  var2 = null;
                  var1 = this.music;
                  this.music.current = null;
                  var1 = this.music;
                  this.music.playing.clear();
               }
            }

            var1 = this.music;
            if(this.music.playing != null) {
               var1 = this.music;
               if(this.music.playing.remaining() != 0) {
                  while(true) {
                     var1 = this.music;
                     if(this.music.playing.remaining() == 0) {
                        break;
                     }

                     var1 = this.music;
                     var1 = this.music;
                     var2 = this.music.playing;
                     VorbisStream var9 = this.music.stream;
                     int var10 = this.music.stream.readPcm(var2.array(), var2.position(), var2.remaining());
                     var2.position(var2.position() + var10);
                     boolean var11;
                     if(var11 = var10 <= 0) {
                        this.music.finished = true;
                        this.music.stopped = true;
                        break;
                     }
                  }
               }
            }

            var1 = this.music;
            if(this.music.playing != null) {
               var1 = this.music;
               if(this.music.previous == null) {
                  var1 = this.music;
                  this.music.playing.flip();
                  var1 = this.music;
                  var2 = this.music.playing;
                  var10001 = this.music;
                  this.music.previous = var2;
                  var2 = null;
                  var1 = this.music;
                  this.music.playing = var2;
               }
            }

            Thread.sleep(10L);
            var1 = this.music;
         } while(this.music.player.running);

         return;
      } catch (Exception var7) {
         var7.printStackTrace();
         return;
      } finally {
         this.music.finished = true;
      }

   }
}
