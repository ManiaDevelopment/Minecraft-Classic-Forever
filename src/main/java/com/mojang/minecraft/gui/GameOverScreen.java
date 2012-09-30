package com.mojang.minecraft.gui;

import org.lwjgl.opengl.GL11;

public final class GameOverScreen extends GuiScreen {

   public final void onOpen() {
      this.buttons.clear();
      this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 72, "Generate new level..."));
      this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 96, "Load level.."));
      if(this.minecraft.session == null) {
         ((Button)this.buttons.get(2)).active = false;
      }

   }

   protected final void onButtonClick(Button var1) {
      if(var1.id == 0) {
         this.minecraft.setCurrentScreen(new OptionsScreen(this, this.minecraft.settings));
      }

      if(var1.id == 1) {
         this.minecraft.setCurrentScreen(new GenerateLevelScreen(this));
      }

      if(this.minecraft.session != null && var1.id == 2) {
         this.minecraft.setCurrentScreen(new LoadLevelScreen(this));
      }

   }

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1615855616, -1602211792);
      GL11.glPushMatrix();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      drawCenteredString(this.fontRenderer, "Game over!", this.width / 2 / 2, 30, 16777215);
      GL11.glPopMatrix();
      drawCenteredString(this.fontRenderer, "Score: &e" + this.minecraft.player.getScore(), this.width / 2, 100, 16777215);
      super.render(var1, var2);
   }
}
