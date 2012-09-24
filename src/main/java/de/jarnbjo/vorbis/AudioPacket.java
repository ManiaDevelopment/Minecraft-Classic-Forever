package de.jarnbjo.vorbis;

import com.mojang.util.MathHelper;
import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Floor;
import de.jarnbjo.vorbis.IdentificationHeader;
import de.jarnbjo.vorbis.Mapping;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.Util;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;

public final class AudioPacket {

   private int modeNumber;
   private Mode mode;
   private Mapping mapping;
   private int n;
   private boolean blockFlag;
   private boolean previousWindowFlag;
   private boolean nextWindowFlag;
   private int windowCenter;
   private int leftWindowStart;
   private int leftWindowEnd;
   private int leftN;
   private int rightWindowStart;
   private int rightWindowEnd;
   private float[] window;
   private float[][] pcm;
   private int[][] pcmInt;
   private Floor[] channelFloors;
   private boolean[] noResidues;
   private static final float[][] windows = new float[8][];


   protected AudioPacket(VorbisStream var1, BitInputStream var2) {
	   try
	   {
		   SetupHeader var4 = var1.setupHeader;
		   IdentificationHeader var5 = var1.identificationHeader;
		   Mode[] var7 = var4.modes;
		   Mapping[] var8 = var4.mappings;
		   Residue[] var9 = var4.residues;
		   int var10 = var5.channels;
		   if(var2.getInt(1) != 0) {
			   throw new VorbisFormatException("Packet type mismatch when trying to create an audio packet.");
		   } else {
			   this.modeNumber = var2.getInt(Util.ilog(var7.length - 1));

			   try {
				   this.mode = var7[this.modeNumber];
			   } catch (ArrayIndexOutOfBoundsException var14) {
				   throw new VorbisFormatException("Reference to invalid mode in audio packet.");
			   }

			   Mode var6 = this.mode;
			   this.mapping = var8[this.mode.mapping];
			   int[] var19 = this.mapping.getMagnitudes();
			   int[] var21 = this.mapping.getAngles();
			   var6 = this.mode;
			   this.blockFlag = this.mode.blockFlag;
			   int var18 = var5.blockSize0;
			   int var11 = var5.blockSize1;
			   this.n = this.blockFlag?var11:var18;
			   if(this.blockFlag) {
				   this.previousWindowFlag = var2.getBit();
				   this.nextWindowFlag = var2.getBit();
			   }

			   this.windowCenter = this.n / 2;
			   if(this.blockFlag && !this.previousWindowFlag) {
				   this.leftWindowStart = this.n / 4 - var18 / 4;
				   this.leftWindowEnd = this.n / 4 + var18 / 4;
				   this.leftN = var18 / 2;
			   } else {
				   this.leftWindowStart = 0;
				   this.leftWindowEnd = this.n / 2;
				   this.leftN = this.windowCenter;
			   }

			   if(this.blockFlag && !this.nextWindowFlag) {
				   this.rightWindowStart = this.n * 3 / 4 - var18 / 4;
				   this.rightWindowEnd = var18 / 2;
			   } else {
				   this.rightWindowStart = this.windowCenter;
				   this.rightWindowEnd = this.n / 2;
			   }

			   this.window = this.getComputedWindow();
			   this.channelFloors = new Floor[var10];
			   this.noResidues = new boolean[var10];
			   this.pcm = new float[var10][this.n];
			   this.pcmInt = new int[var10][this.n];
			   boolean var20 = true;

			   int var3;
			   for(var11 = 0; var11 < var10; ++var11) {
				   var3 = this.mapping.getMux()[var11];
				   int var12 = this.mapping.getSubmapFloors()[var3];
				   Floor var13 = var4.floors[var12].decodeFloor(var1, var2);
				   this.channelFloors[var11] = var13;
				   this.noResidues[var11] = var13 == null;
				   if(var13 != null) {
					   var20 = false;
				   }
			   }

			   if(!var20) {
				   for(var11 = 0; var11 < var19.length; ++var11) {
					   if(!this.noResidues[var19[var11]] || !this.noResidues[var21[var11]]) {
						   this.noResidues[var19[var11]] = false;
						   this.noResidues[var21[var11]] = false;
					   }
				   }

				   int var24;
				   for(var11 = 0; var11 < this.mapping.getSubmaps(); ++var11) {
					   var3 = 0;
					   boolean[] var22 = new boolean[var10];

					   for(var24 = 0; var24 < var10; ++var24) {
						   if(this.mapping.getMux()[var24] == var11) {
							   var22[var3++] = this.noResidues[var24];
						   }
					   }

					   var24 = this.mapping.getSubmapResidues()[var11];
					   var9[var24].decodeResidue(var1, var2, this.mode, var22, this.pcm);
				   }

				   for(var11 = this.mapping.getCouplingSteps() - 1; var11 >= 0; --var11) {
					   float[] var16 = this.pcm[var19[var11]];
					   float[] var23 = this.pcm[var21[var11]];

					   for(var24 = 0; var24 < var16.length; ++var24) {
						   float var17 = var23[var24];
						   float var15 = var16[var24];
						   if(var17 > 0.0F) {
							   var23[var24] = var15 > 0.0F?var15 - var17:var15 + var17;
						   } else {
							   var16[var24] = var15 > 0.0F?var15 + var17:var15 - var17;
							   var23[var24] = var15;
						   }
					   }
				   }

				   for(var11 = 0; var11 < var10; ++var11) {
					   if(this.channelFloors[var11] != null) {
						   this.channelFloors[var11].computeFloor(this.pcm[var11]);
					   }
				   }

				   for(var11 = 0; var11 < var10; ++var11) {
					   (this.blockFlag?var5.mdct[1]:var5.mdct[0]).imdct(this.pcm[var11], this.window, this.pcmInt[var11]);
				   }

			   }
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }
   }

	private float[] getComputedWindow() {
		int var1 = (this.blockFlag?4:0) + (this.previousWindowFlag?2:0) + (this.nextWindowFlag?1:0);
		float[] var2;
		if((var2 = windows[var1]) == null) {
			var2 = new float[this.n];

			int var3;
			float var4;
			for(var3 = 0; var3 < this.leftN; ++var3) {
				var4 = MathHelper.sin((float)((double)((var4 = MathHelper.sin((float)(((double)var3 + 0.5D) / (double)this.leftN * 3.1415927410125732D / 2.0D))) * var4) * 1.5707963705062866D));
				var2[var3 + this.leftWindowStart] = var4;
			}

			for(var3 = this.leftWindowEnd; var3 < this.rightWindowStart; var2[var3++] = 1.0F) {
				;
			}

			for(var3 = 0; var3 < this.rightWindowEnd; ++var3) {
				var4 = MathHelper.sin((float)((double)((var4 = MathHelper.sin((float)(((double)(this.rightWindowEnd - var3) - 0.5D) / (double)this.rightWindowEnd * 3.1415927410125732D / 2.0D))) * var4) * 1.5707963705062866D));
				var2[var3 + this.rightWindowStart] = var4;
			}

			windows[var1] = var2;
		}

		return var2;
	}

   protected final int getNumberOfSamples() {
      return this.rightWindowStart - this.leftWindowStart;
   }

   protected final void getPcm(AudioPacket var1, byte[] var2) {
      int var3 = this.pcm.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = 0;
         int var6 = var1.rightWindowStart;
         int[] var7 = var1.pcmInt[var4];
         int[] var8 = this.pcmInt[var4];

         int var9;
         int var10;
         for(var9 = this.leftWindowStart; var9 < this.leftWindowEnd; ++var9) {
            if((var10 = var7[var6++] + var8[var9]) > 32767) {
               var10 = 32767;
            }

            if(var10 < -32768) {
               var10 = -32768;
            }

            var2[var5 + (var4 << 1) + 1] = (byte)var10;
            var2[var5 + (var4 << 1)] = (byte)(var10 >> 8);
            var5 += var3 << 1;
         }

         var5 = (this.leftWindowEnd - this.leftWindowStart) * var3 << 1;

         for(var9 = this.leftWindowEnd; var9 < this.rightWindowStart; ++var9) {
            if((var10 = var8[var9]) > 32767) {
               var10 = 32767;
            }

            if(var10 < -32768) {
               var10 = -32768;
            }

            var2[var5 + (var4 << 1) + 1] = (byte)var10;
            var2[var5 + (var4 << 1)] = (byte)(var10 >> 8);
            var5 += var3 << 1;
         }
      }

   }

}
