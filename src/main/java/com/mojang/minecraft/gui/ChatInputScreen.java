package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.net.NetworkManager;
import com.mojang.minecraft.net.PacketType;
import org.lwjgl.input.Keyboard;

public final class ChatInputScreen extends GuiScreen {

   private String message = "";
   private int counter = 0;


   public final void onOpen() {
      Keyboard.enableRepeatEvents(true);
   }

   public final void onClose() {
      Keyboard.enableRepeatEvents(false);
   }

   public final void tick() {
      ++this.counter;
   }

   protected final void onKeyPress(char var1, int var2) {
      if(var2 == 1) {
         this.minecraft.setCurrentScreen((GuiScreen)null);
      } else if(var2 == 28) {
         NetworkManager var10000 = this.minecraft.networkManager;
         String var4 = this.message.trim();
         NetworkManager var3 = var10000;
         if((var4 = var4.trim()).length() > 0) {
            var3.netHandler.send(PacketType.CHAT_MESSAGE, new Object[]{Integer.valueOf(-1), var4});
         }

         this.minecraft.setCurrentScreen((GuiScreen)null);
      } else {
         if(var2 == 14 && this.message.length() > 0) {
            this.message = this.message.substring(0, this.message.length() - 1);
         }

         if("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\\\"#%/()=+?[]{}<>@|$;".indexOf(var1) >= 0 && this.message.length() < 64 - (this.minecraft.session.username.length() + 2)) {
            this.message = this.message + var1;
         }

      }
   }

   public final void render(int var1, int var2) {
      drawBox(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
      drawString(this.fontRenderer, "> " + this.message + (this.counter / 6 % 2 == 0?"_":""), 4, this.height - 12, 14737632);
   }

   protected final void onMouseClick(int var1, int var2, int var3) {
      if(var3 == 0 && this.minecraft.hud.hoveredPlayer != null) {
         if(this.message.length() > 0 && !this.message.endsWith(" ")) {
            this.message = this.message + " ";
         }

         this.message = this.message + this.minecraft.hud.hoveredPlayer;
         var1 = 64 - (this.minecraft.session.username.length() + 2);
         if(this.message.length() > var1) {
            this.message = this.message.substring(0, var1);
         }
      }

   }
}
