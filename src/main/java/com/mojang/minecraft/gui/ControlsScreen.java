package com.mojang.minecraft.gui;

import com.mojang.minecraft.GameSettings;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.OptionButton;

public final class ControlsScreen extends GuiScreen {

   private GuiScreen parent;
   private String title = "Controls";
   private GameSettings settings;
   private int selected = -1;


   public ControlsScreen(GuiScreen var1, GameSettings var2) {
      this.parent = var1;
      this.settings = var2;
   }

   public final void onOpen() {
      for(int var1 = 0; var1 < this.settings.bindings.length; ++var1) {
         this.buttons.add(new OptionButton(var1, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), this.settings.getBinding(var1)));
      }

      this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
   }

   protected final void onButtonClick(Button var1) {
      for(int var2 = 0; var2 < this.settings.bindings.length; ++var2) {
         ((Button)this.buttons.get(var2)).text = this.settings.getBinding(var2);
      }

      if(var1.id == 200) {
         this.minecraft.setCurrentScreen(this.parent);
      } else {
         this.selected = var1.id;
         var1.text = "> " + this.settings.getBinding(var1.id) + " <";
      }
   }

   protected final void onKeyPress(char var1, int var2) {
      if(this.selected >= 0) {
         this.settings.setBinding(this.selected, var2);
         ((Button)this.buttons.get(this.selected)).text = this.settings.getBinding(this.selected);
         this.selected = -1;
      } else {
         super.onKeyPress(var1, var2);
      }
   }

   public final void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
      super.render(var1, var2);
   }
}
