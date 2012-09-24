package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.GuiScreen;

public final class GenerateLevelScreen extends GuiScreen {

   private GuiScreen parent;


   public GenerateLevelScreen(GuiScreen var1) {
      this.parent = var1;
   }

   public final void onOpen() {
      this.buttons.clear();
      this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Small"));
      this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Normal"));
      this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Huge"));
      this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 120, "Cancel"));
   }

   protected final void onButtonClick(Button var1) {
      if(var1.id == 3) {
         this.minecraft.setCurrentScreen(this.parent);
      } else {
         this.minecraft.generateLevel(var1.id);
         this.minecraft.setCurrentScreen((GuiScreen)null);
         this.minecraft.grabMouse();
      }
   }

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, "Generate new level", this.width / 2, 40, 16777215);
      super.render(var1, var2);
   }
}
