package com.mojang.minecraft.gui;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.LevelNameScreen;
import com.mojang.minecraft.gui.LoadLevelScreen;
import java.io.File;

public final class SaveLevelScreen extends LoadLevelScreen {

   public SaveLevelScreen(GuiScreen var1) {
      super(var1);
      this.title = "Save level";
      this.saving = true;
   }

   public final void onOpen() {
      super.onOpen();
      ((Button)this.buttons.get(5)).text = "Save file...";
   }

   protected final void setLevels(String[] var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         ((Button)this.buttons.get(var2)).text = var1[var2];
         ((Button)this.buttons.get(var2)).visible = true;
         ((Button)this.buttons.get(var2)).active = this.minecraft.session.haspaid;
      }

   }

   public final void render(int var1, int var2) {
      super.render(var1, var2);
      if(!this.minecraft.session.haspaid) {
         drawFadingBox(this.width / 2 - 80, 72, this.width / 2 + 80, 120, -536870912, -536870912);
         drawCenteredString(this.fontRenderer, "Premium only!", this.width / 2, 80, 16748688);
         drawCenteredString(this.fontRenderer, "Purchase the game to be able", this.width / 2, 96, 14712960);
         drawCenteredString(this.fontRenderer, "to save your levels online.", this.width / 2, 104, 14712960);
      }
   }

   protected final void openLevel(File var1) {
      if(!var1.getName().endsWith(".mine")) {
         var1 = new File(var1.getParentFile(), var1.getName() + ".mine");
      }

      File var2 = var1;
      Minecraft var3 = this.minecraft;
      this.minecraft.levelIo.save(var3.level, var2);
      this.minecraft.setCurrentScreen(this.parent);
   }

   protected final void openLevel(int var1) {
      this.minecraft.setCurrentScreen(new LevelNameScreen(this, ((Button)this.buttons.get(var1)).text, var1));
   }
}
