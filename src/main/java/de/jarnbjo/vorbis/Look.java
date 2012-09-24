package de.jarnbjo.vorbis;

import de.jarnbjo.vorbis.CodeBook;
import de.jarnbjo.vorbis.Mode;
import de.jarnbjo.vorbis.Residue;
import de.jarnbjo.vorbis.Util;
import de.jarnbjo.vorbis.VorbisStream;

final class Look {

   private int map;
   int parts;
   private CodeBook[] fullbooks;
   CodeBook phrasebook;
   int[][] partbooks;
   private int partvals;
   int[][] decodemap;


   protected Look(Residue var1, VorbisStream var2, Mode var3) {
      boolean var10 = false;
      int var4 = 0;
      this.map = var1.getClassifications();
      this.fullbooks = var2.setupHeader.codeBooks;
      this.phrasebook = this.fullbooks[var1.getClassBook()];
      CodeBook var8 = this.phrasebook;
      int var11 = this.phrasebook.dimensions;
      this.partbooks = new int[this.map][];

      int var5;
      int var6;
      int var9;
      for(var9 = 0; var9 < this.map; ++var9) {
         if((var5 = Util.ilog(var1.getCascade()[var9])) != 0) {
            if(var5 > var4) {
               var4 = var5;
            }

            this.partbooks[var9] = new int[var5];

            for(var6 = 0; var6 < var5; ++var6) {
               if((var1.getCascade()[var9] & 1 << var6) != 0) {
                  this.partbooks[var9][var6] = var1.getBooks()[var9][var6];
               }
            }
         }
      }

      this.partvals = (int)Math.rint(Math.pow((double)this.map, (double)var11));
      this.parts = var4;
      this.decodemap = new int[this.partvals][];

      for(var9 = 0; var9 < this.partvals; ++var9) {
         var5 = var9;
         var6 = this.partvals / this.map;
         this.decodemap[var9] = new int[var11];

         for(int var7 = 0; var7 < var11; ++var7) {
            var4 = var5 / var6;
            var5 -= var4 * var6;
            var6 /= this.map;
            this.decodemap[var9][var7] = var4;
         }
      }

   }
}
