package com.mojang.minecraft.gui;

import com.mojang.minecraft.GameSettings;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.ControlsScreen;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.OptionButton;

public final class OptionsScreen extends GuiScreen {

   private GuiScreen parent;
   private String title = "Options";
   private GameSettings settings;


   public OptionsScreen(GuiScreen var1, GameSettings var2) {
      this.parent = var1;
      this.settings = var2;
   }

   public final void onOpen() {
      for(int var1 = 0; var1 < this.settings.settingCount; ++var1) {
         this.buttons.add(new OptionButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.settings.getSetting(var1)));
      }

      this.buttons.add(new Button(100, this.width / 2 - 100, this.height / 6 + 120 + 12, "Controls..."));
      this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
   }

   protected final void onButtonClick(Button var1) {
      if(var1.active) {
         if(var1.id < 100) {
            this.settings.toggleSetting(var1.id, 1);
            var1.text = this.settings.getSetting(var1.id);
         }

         if(var1.id == 100) {
            this.minecraft.setCurrentScreen(new ControlsScreen(this, this.settings));
         }

         if(var1.id == 200) {
            this.minecraft.setCurrentScreen(this.parent);
         }

      }
   }

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
      super.render(var1, var2);
   }
}
