package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.GenerateLevelScreen;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.LoadLevelScreen;
import com.mojang.minecraft.gui.OptionsScreen;
import com.mojang.minecraft.gui.SaveLevelScreen;

public final class PauseScreen extends GuiScreen {

   public final void onOpen() {
      this.buttons.clear();
      this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Options..."));
      this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Generate new level..."));
      this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Save level.."));
      this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 72, "Load level.."));
      this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 120, "Back to game"));
      if(this.minecraft.session == null) {
         ((Button)this.buttons.get(2)).active = false;
         ((Button)this.buttons.get(3)).active = false;
      }

      if(this.minecraft.networkManager != null) {
         ((Button)this.buttons.get(1)).active = false;
         ((Button)this.buttons.get(2)).active = false;
         ((Button)this.buttons.get(3)).active = false;
      }

   }

   protected final void onButtonClick(Button var1) {
      if(var1.id == 0) {
         this.minecraft.setCurrentScreen(new OptionsScreen(this, this.minecraft.settings));
      }

      if(var1.id == 1) {
         this.minecraft.setCurrentScreen(new GenerateLevelScreen(this));
      }

      if(this.minecraft.session != null) {
         if(var1.id == 2) {
            this.minecraft.setCurrentScreen(new SaveLevelScreen(this));
         }

         if(var1.id == 3) {
            this.minecraft.setCurrentScreen(new LoadLevelScreen(this));
         }
      }

      if(var1.id == 4) {
         this.minecraft.setCurrentScreen((GuiScreen)null);
         this.minecraft.grabMouse();
      }

   }

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, "Game menu", this.width / 2, 40, 16777215);
      super.render(var1, var2);
   }
}
