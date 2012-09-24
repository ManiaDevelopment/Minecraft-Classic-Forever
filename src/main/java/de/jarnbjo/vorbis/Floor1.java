package de.jarnbjo.vorbis;

import de.jarnbjo.util.io.BitInputStream;
import de.jarnbjo.vorbis.Floor;
import de.jarnbjo.vorbis.SetupHeader;
import de.jarnbjo.vorbis.Util;
import de.jarnbjo.vorbis.VorbisFormatException;
import de.jarnbjo.vorbis.VorbisStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

final class Floor1 extends Floor implements Cloneable {

   private int[] partitionClassList;
   private int maximumClass;
   private int multiplier;
   private int rangeBits;
   private int[] classDimensions;
   private int[] classSubclasses;
   private int[] classMasterbooks;
   private int[][] subclassBooks;
   private int[] xList;
   private int[] yList;
   private int[] lowNeighbours;
   private int[] highNeighbours;
   private static final int[] RANGES = new int[]{256, 128, 86, 64};


   private Floor1() {}

   protected Floor1(BitInputStream var1, SetupHeader var2) {
	   try
	   {
		   this.maximumClass = -1;
		   int var3 = var1.getInt(5);
		   this.partitionClassList = new int[var3];

		   int var4;
		   for(var4 = 0; var4 < this.partitionClassList.length; ++var4) {
			   this.partitionClassList[var4] = var1.getInt(4);
			   if(this.partitionClassList[var4] > this.maximumClass) {
				   this.maximumClass = this.partitionClassList[var4];
			   }
		   }

		   this.classDimensions = new int[this.maximumClass + 1];
		   this.classSubclasses = new int[this.maximumClass + 1];
		   this.classMasterbooks = new int[this.maximumClass + 1];
		   this.subclassBooks = new int[this.maximumClass + 1][];

		   int var5;
		   for(var4 = 0; var4 <= this.maximumClass; ++var4) {
			   this.classDimensions[var4] = var1.getInt(3) + 1;
			   this.classSubclasses[var4] = var1.getInt(2);
			   if(this.classDimensions[var4] > var2.codeBooks.length || this.classSubclasses[var4] > var2.codeBooks.length) {
				   throw new VorbisFormatException("There is a class dimension or class subclasses entry higher than the number of codebooks in the setup header.");
			   }

			   if(this.classSubclasses[var4] != 0) {
				   this.classMasterbooks[var4] = var1.getInt(8);
			   }

			   this.subclassBooks[var4] = new int[1 << this.classSubclasses[var4]];

			   for(var5 = 0; var5 < this.subclassBooks[var4].length; ++var5) {
				   this.subclassBooks[var4][var5] = var1.getInt(8) - 1;
			   }
		   }

		   this.multiplier = var1.getInt(2) + 1;
		   this.rangeBits = var1.getInt(4);
		   ArrayList var7;
		   (var7 = new ArrayList()).add(new Integer(0));
		   var7.add(new Integer(1 << this.rangeBits));

		   int var6;
		   for(var5 = 0; var5 < var3; ++var5) {
			   for(var6 = 0; var6 < this.classDimensions[this.partitionClassList[var5]]; ++var6) {
				   var7.add(new Integer(var1.getInt(this.rangeBits)));
			   }
		   }

		   this.xList = new int[var7.size()];
		   this.lowNeighbours = new int[this.xList.length];
		   this.highNeighbours = new int[this.xList.length];
		   Iterator var8 = var7.iterator();

		   for(var6 = 0; var6 < this.xList.length; ++var6) {
			   this.xList[var6] = ((Integer)var8.next()).intValue();
		   }

		   for(var6 = 0; var6 < this.xList.length; ++var6) {
			   this.lowNeighbours[var6] = Util.lowNeighbour(this.xList, var6);
			   this.highNeighbours[var6] = Util.highNeighbour(this.xList, var6);
		   }
	   } catch (VorbisFormatException e) {
		   e.printStackTrace();
	   }

   }

   protected final Floor decodeFloor(VorbisStream var1, BitInputStream var2) {
      if(!var2.getBit()) {
         return null;
      } else {
         Floor1 var3;
         (var3 = (Floor1)this.clone()).yList = new int[this.xList.length];
         int var4 = RANGES[this.multiplier - 1];
         var3.yList[0] = var2.getInt(Util.ilog(var4 - 1));
         var3.yList[1] = var2.getInt(Util.ilog(var4 - 1));
         var4 = 2;

         for(int var5 = 0; var5 < this.partitionClassList.length; ++var5) {
            int var6 = this.partitionClassList[var5];
            int var7 = this.classDimensions[var6];
            int var8 = this.classSubclasses[var6];
            int var9 = (1 << var8) - 1;
            int var10 = 0;
            SetupHeader var11;
            if(var8 > 0) {
               var11 = var1.setupHeader;
               var10 = var2.getInt(var1.setupHeader.codeBooks[this.classMasterbooks[var6]].huffmanRoot);
            }

            for(int var12 = 0; var12 < var7; ++var12) {
               int var13 = this.subclassBooks[var6][var10 & var9];
               var10 >>>= var8;
               if(var13 >= 0) {
                  int var10001 = var12 + var4;
                  var11 = var1.setupHeader;
                  var3.yList[var10001] = var2.getInt(var1.setupHeader.codeBooks[var13].huffmanRoot);
               } else {
                  var3.yList[var12 + var4] = 0;
               }
            }

            var4 += var7;
         }

         return var3;
      }
   }

   protected final void computeFloor(float[] var1) {
      int var2 = var1.length;
      int var3;
      boolean[] var4 = new boolean[var3 = this.xList.length];
      int var5 = RANGES[this.multiplier - 1];

      int var7;
      int var8;
      int var9;
      int var10;
      int var12;
      int var13;
      for(int var6 = 2; var6 < var3; ++var6) {
         var7 = this.lowNeighbours[var6];
         var8 = this.highNeighbours[var6];
         int var10000 = this.xList[var7];
         int var10001 = this.xList[var8];
         int var10002 = this.yList[var7];
         int var10003 = this.yList[var8];
         var9 = this.xList[var6];
         var10 = var10003;
         int var11 = var10002;
         var12 = var10001;
         var13 = var10000;
         var9 = ((var10 -= var11) < 0?-var10:var10) * (var9 - var13) / (var12 - var13);
         var13 = var10 < 0?var11 - var9:var11 + var9;
         var12 = this.yList[var6];
         var11 = var5 - var13;
         var9 = var11 < var13?var11 << 1:var13 << 1;
         if(var12 != 0) {
            var4[var7] = true;
            var4[var8] = true;
            var4[var6] = true;
            if(var12 >= var9) {
               this.yList[var6] = var11 > var13?var12 - var13 + var13:-var12 + var11 + var13 - 1;
            } else {
               this.yList[var6] = (var12 & 1) == 1?var13 - (var12 + 1 >> 1):var13 + (var12 >> 1);
            }
         } else {
            var4[var6] = false;
            this.yList[var6] = var13;
         }
      }

      int[] var15 = new int[var3];
      System.arraycopy(this.xList, 0, var15, 0, var3);
      boolean[] var20 = var4;
      int[] var19 = this.yList;
      int[] var22 = var15;
      var10 = var15.length;

      for(var5 = 0; var5 < var10; ++var5) {
         for(var7 = var5; var7 > 0 && var22[var7 - 1] > var22[var7]; --var7) {
            int var14 = var22[var7];
            var22[var7] = var22[var7 - 1];
            var22[var7 - 1] = var14;
            var14 = var19[var7];
            var19[var7] = var19[var7 - 1];
            var19[var7 - 1] = var14;
            boolean var16 = var20[var7];
            var20[var7] = var20[var7 - 1];
            var20[var7 - 1] = var16;
         }
      }

      var7 = 0;
      var8 = 0;
      var13 = 0;
      var12 = this.yList[0] * this.multiplier;
      float[] var21 = new float[var1.length];
      float[] var17 = new float[var1.length];
      Arrays.fill(var21, 1.0F);
      System.arraycopy(var1, 0, var17, 0, var1.length);

      for(var9 = 1; var9 < var3; ++var9) {
         if(var4[var9]) {
            var8 = this.yList[var9] * this.multiplier;
            var7 = var15[var9];
            Util.renderLine(var13, var12, var7, var8, var1);
            Util.renderLine(var13, var12, var7, var8, var21);
            var13 = var7;
            var12 = var8;
         }
      }

      for(float var18 = DB_STATIC_TABLE[var8]; var7 < var2 / 2; var1[var7++] = var18) {
         ;
      }

   }

   public final Object clone() {
      Floor1 var1;
      (var1 = new Floor1()).classDimensions = this.classDimensions;
      var1.classMasterbooks = this.classMasterbooks;
      var1.classSubclasses = this.classSubclasses;
      var1.maximumClass = this.maximumClass;
      var1.multiplier = this.multiplier;
      var1.partitionClassList = this.partitionClassList;
      var1.rangeBits = this.rangeBits;
      var1.subclassBooks = this.subclassBooks;
      var1.xList = this.xList;
      var1.yList = this.yList;
      var1.lowNeighbours = this.lowNeighbours;
      var1.highNeighbours = this.highNeighbours;
      return var1;
   }

}
