package com.mojang.minecraft.level;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.HashSet;
import java.util.Set;

public final class LevelObjectInputStream extends ObjectInputStream {

   private Set classes = new HashSet();


   public LevelObjectInputStream(InputStream var1) throws IOException {
      super(var1);
      this.classes.add("Player$1");
      this.classes.add("Creeper$1");
      this.classes.add("Skeleton$1");
   }

   protected final ObjectStreamClass readClassDescriptor() {
	   try
	   {
		   ObjectStreamClass var1 = super.readClassDescriptor();
		   return this.classes.contains(var1.getName())?ObjectStreamClass.lookup(Class.forName(var1.getName())):var1;
	   } catch (ClassNotFoundException e) {
		   e.printStackTrace();
	   } catch (IOException e) {
		   e.printStackTrace();
	   }

	   return null;
   }
}
