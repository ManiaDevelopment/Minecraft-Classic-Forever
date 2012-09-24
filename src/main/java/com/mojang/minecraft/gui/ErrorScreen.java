package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.GuiScreen;

public final class ErrorScreen extends GuiScreen {

   private String title;
   private String text;


   public ErrorScreen(String var1, String var2) {
      this.title = var1;
      this.text = var2;
   }

   public final void onOpen() {}

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, -12574688, -11530224);
      drawCenteredString(this.fontRenderer, this.title, this.width / 2, 90, 16777215);
      drawCenteredString(this.fontRenderer, this.text, this.width / 2, 110, 16777215);
      super.render(var1, var2);
   }

   protected final void onKeyPress(char var1, int var2) {}
}
