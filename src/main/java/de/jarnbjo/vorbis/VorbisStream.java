package de.jarnbjo.vorbis;

import de.jarnbjo.ogg.LogicalOggStreamImpl;
import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.AudioPacket;
import de.jarnbjo.vorbis.CommentHeader;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.VorbisFormatException;
import java.util.LinkedList;

public final class VorbisStream {

   private LogicalOggStreamImpl oggStream;
   public IdentificationHeader identificationHeader;
   private CommentHeader commentHeader;
   SetupHeader setupHeader;
   private AudioPacket lastAudioPacket;
   private byte[] currentPcm;
   private int currentPcmIndex;
   private int currentPcmLimit;
   private Object streamLock;
   private int pageCounter;
   private long currentGranulePosition;


   public VorbisStream() {
      new LinkedList();
      this.streamLock = new Object();
      this.pageCounter = 0;
   }

   public VorbisStream(LogicalOggStreamImpl var1) {
	   try
	   {
		   new LinkedList();
		   this.streamLock = new Object();
		   this.pageCounter = 0;
		   this.oggStream = var1;

		   for(int var2 = 0; var2 < 3; ++var2) {
			   BitInputStream var3;
			   switch((var3 = new BitInputStream(var1.getNextOggPacket())).getInt(8)) {
				   case 1:
					   this.identificationHeader = new IdentificationHeader(var3);
				   case 2:
				   case 4:
				   default:
					   break;
				   case 3:
					   this.commentHeader = new CommentHeader(var3);
					   break;
				   case 5:
					   this.setupHeader = new SetupHeader(this, var3);
			   }
		   }

		   if(this.identificationHeader == null) {
			   throw new VorbisFormatException("The file has no identification header.");
		   } else if(this.commentHeader == null) {
			   throw new VorbisFormatException("The file has no commentHeader.");
		   } else if(this.setupHeader == null) {
			   throw new VorbisFormatException("The file has no setup header.");
		   } else {
			   IdentificationHeader var5 = this.identificationHeader;
			   var5 = this.identificationHeader;
			   this.currentPcm = new byte[this.identificationHeader.channels * this.identificationHeader.blockSize1 << 1];
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }

   public final int readPcm(byte[] var1, int var2, int var3) {
      Object var4 = this.streamLock;
      synchronized(this.streamLock) {
         if(this.lastAudioPacket == null) {
            this.lastAudioPacket = this.getNextAudioPacket();
         }

         if(this.currentPcm == null || this.currentPcmIndex >= this.currentPcmLimit) {
            AudioPacket var5 = this.getNextAudioPacket();

            try {
               var5.getPcm(this.lastAudioPacket, this.currentPcm);
               int var10001 = var5.getNumberOfSamples();
               IdentificationHeader var6 = this.identificationHeader;
               this.currentPcmLimit = var10001 * this.identificationHeader.channels << 1;
            } catch (ArrayIndexOutOfBoundsException var8) {
               return 0;
            }

            this.currentPcmIndex = 0;
            this.lastAudioPacket = var5;
         }

         int var10 = 0;
         boolean var12 = false;
         int var7 = 0;

         int var11;
         for(var11 = this.currentPcmIndex; var11 < this.currentPcmLimit && var7 < var3; ++var11) {
            var1[var2 + var7++] = this.currentPcm[var11];
            ++var10;
         }

         this.currentPcmIndex = var11;
         return var10;
      }
   }

   private AudioPacket getNextAudioPacket() {
      ++this.pageCounter;
      byte[] var1 = this.oggStream.getNextOggPacket();
      AudioPacket var2 = null;

      while(var2 == null) {
         try {
            var2 = new AudioPacket(this, new BitInputStream(var1));
         } catch (ArrayIndexOutOfBoundsException var3) {
            ;
         }
      }

      this.currentGranulePosition += (long)var2.getNumberOfSamples();
      return var2;
   }
}
