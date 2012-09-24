package de.jarnbjo.util.io;


public final class HuffmanNode {

   private int depth;
   protected HuffmanNode o0;
   protected HuffmanNode o1;
   protected Integer value;
   private boolean full;


   public HuffmanNode() {
      this((HuffmanNode)null);
   }

   private HuffmanNode(HuffmanNode var1) {
      this.depth = 0;
      this.full = false;
      if(var1 != null) {
         this.depth = var1.depth + 1;
      }

   }

   private HuffmanNode(HuffmanNode var1, int var2) {
      this(var1);
      this.value = new Integer(var2);
      this.full = true;
   }

   private boolean isFull() {
      return this.full?true:(this.full = this.o0 != null && this.o0.isFull() && this.o1 != null && this.o1.isFull());
   }

   public final boolean setNewValue(int var1, int var2) {
      if(this.isFull()) {
         return false;
      } else {
         HuffmanNode var3;
         if(var1 == 1) {
            if(this.o0 == null) {
               var3 = new HuffmanNode(this, var2);
               this.o0 = var3;
               return true;
            } else if(this.o1 == null) {
               var3 = new HuffmanNode(this, var2);
               this.o1 = var3;
               return true;
            } else {
               return false;
            }
         } else {
            HuffmanNode var10000;
            if(this.o0 == null) {
               var3 = new HuffmanNode(this);
               var10000 = this.o0 = var3;
            } else {
               var10000 = this.o0;
            }

            if(var10000.setNewValue(var1 - 1, var2)) {
               return true;
            } else {
               if(this.o1 == null) {
                  var3 = new HuffmanNode(this);
                  var10000 = this.o1 = var3;
               } else {
                  var10000 = this.o1;
               }

               return var10000.setNewValue(var1 - 1, var2);
            }
         }
      }
   }
}
