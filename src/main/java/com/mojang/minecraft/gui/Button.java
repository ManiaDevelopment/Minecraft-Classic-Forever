package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.Screen;

public class Button extends Screen {

   int width;
   int height;
   public int x;
   public int y;
   public String text;
   public int id;
   public boolean active;
   public boolean visible;


   public Button(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, 200, 20, var4);
   }

   protected Button(int var1, int var2, int var3, int var4, int var5, String var6) {
      this.width = 200;
      this.height = 20;
      this.active = true;
      this.visible = true;
      this.id = var1;
      this.x = var2;
      this.y = var3;
      this.width = var4;
      this.height = 20;
      this.text = var6;
   }
}
