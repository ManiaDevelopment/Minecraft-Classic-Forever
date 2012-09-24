package com.mojang.minecraft.gui;

import com.mojang.minecraft.ChatLine;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gamemode.SurvivalGameMode;
import com.mojang.minecraft.gui.ChatInputScreen;
import com.mojang.minecraft.gui.FontRenderer;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.tile.Block;
import com.mojang.minecraft.player.Inventory;
import com.mojang.minecraft.render.ShapeRenderer;
import com.mojang.minecraft.render.TextureManager;
import com.mojang.util.MathHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class HUDScreen extends Screen {

   public List chat = new ArrayList();
   private Random random = new Random();
   private Minecraft mc;
   private int width;
   private int height;
   public String hoveredPlayer = null;
   public int ticks = 0;


   public HUDScreen(Minecraft var1, int var2, int var3) {
      this.mc = var1;
      this.width = var2 * 240 / var3;
      this.height = var3 * 240 / var3;
   }

   public final void render(float var1, boolean var2, int var3, int var4) {
      FontRenderer var5 = this.mc.fontRenderer;
      this.mc.renderer.enableGuiMode();
      TextureManager var6 = this.mc.textureManager;
      GL11.glBindTexture(3553, this.mc.textureManager.load("/gui/gui.png"));
      ShapeRenderer var7 = ShapeRenderer.instance;
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3042);
      Inventory var8 = this.mc.player.inventory;
      this.imgZ = -90.0F;
      this.drawImage(this.width / 2 - 91, this.height - 22, 0, 0, 182, 22);
      this.drawImage(this.width / 2 - 91 - 1 + var8.selected * 20, this.height - 22 - 1, 0, 22, 24, 22);
      GL11.glBindTexture(3553, this.mc.textureManager.load("/gui/icons.png"));
      this.drawImage(this.width / 2 - 7, this.height / 2 - 7, 0, 0, 16, 16);
      boolean var9 = this.mc.player.invulnerableTime / 3 % 2 == 1;
      if(this.mc.player.invulnerableTime < 10) {
         var9 = false;
      }

      int var10 = this.mc.player.health;
      int var11 = this.mc.player.lastHealth;
      this.random.setSeed((long)(this.ticks * 312871));
      int var12;
      int var14;
      int var15;
      int var26;
      if(this.mc.gamemode.isSurvival()) {
         for(var12 = 0; var12 < 10; ++var12) {
            byte var13 = 0;
            if(var9) {
               var13 = 1;
            }

            var14 = this.width / 2 - 91 + (var12 << 3);
            var15 = this.height - 32;
            if(var10 <= 4) {
               var15 += this.random.nextInt(2);
            }

            this.drawImage(var14, var15, 16 + var13 * 9, 0, 9, 9);
            if(var9) {
               if((var12 << 1) + 1 < var11) {
                  this.drawImage(var14, var15, 70, 0, 9, 9);
               }

               if((var12 << 1) + 1 == var11) {
                  this.drawImage(var14, var15, 79, 0, 9, 9);
               }
            }

            if((var12 << 1) + 1 < var10) {
               this.drawImage(var14, var15, 52, 0, 9, 9);
            }

            if((var12 << 1) + 1 == var10) {
               this.drawImage(var14, var15, 61, 0, 9, 9);
            }
         }

         if(this.mc.player.isUnderWater()) {
            var12 = (int)Math.ceil((double)(this.mc.player.airSupply - 2) * 10.0D / 300.0D);
            var26 = (int)Math.ceil((double)this.mc.player.airSupply * 10.0D / 300.0D) - var12;

            for(var14 = 0; var14 < var12 + var26; ++var14) {
               if(var14 < var12) {
                  this.drawImage(this.width / 2 - 91 + (var14 << 3), this.height - 32 - 9, 16, 18, 9, 9);
               } else {
                  this.drawImage(this.width / 2 - 91 + (var14 << 3), this.height - 32 - 9, 25, 18, 9, 9);
               }
            }
         }
      }

      GL11.glDisable(3042);

      String var23;
      for(var12 = 0; var12 < var8.slots.length; ++var12) {
         var26 = this.width / 2 - 90 + var12 * 20;
         var14 = this.height - 16;
         if((var15 = var8.slots[var12]) > 0) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float)var26, (float)var14, -50.0F);
            if(var8.popTime[var12] > 0) {
               float var18;
               float var21 = -MathHelper.sin((var18 = ((float)var8.popTime[var12] - var1) / 5.0F) * var18 * 3.1415927F) * 8.0F;
               float var19 = MathHelper.sin(var18 * var18 * 3.1415927F) + 1.0F;
               float var16 = MathHelper.sin(var18 * 3.1415927F) + 1.0F;
               GL11.glTranslatef(10.0F, var21 + 10.0F, 0.0F);
               GL11.glScalef(var19, var16, 1.0F);
               GL11.glTranslatef(-10.0F, -10.0F, 0.0F);
            }

            GL11.glScalef(10.0F, 10.0F, 10.0F);
            GL11.glTranslatef(1.0F, 0.5F, 0.0F);
            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
            GL11.glScalef(-1.0F, -1.0F, -1.0F);
            int var20 = var6.load("/terrain.png");
            GL11.glBindTexture(3553, var20);
            var7.begin();
            Block.blocks[var15].renderFullbright(var7);
            var7.end();
            GL11.glPopMatrix();
            if(var8.count[var12] > 1) {
               var23 = "" + var8.count[var12];
               var5.render(var23, var26 + 19 - var5.getWidth(var23), var14 + 6, 16777215);
            }
         }
      }

      var5.render("0.30", 2, 2, 16777215);
      if(this.mc.settings.showFrameRate) {
         var5.render(this.mc.debug, 2, 12, 16777215);
      }

      if(this.mc.gamemode instanceof SurvivalGameMode) {
         String var24 = "Score: &e" + this.mc.player.getScore();
         var5.render(var24, this.width - var5.getWidth(var24) - 2, 2, 16777215);
         var5.render("Arrows: " + this.mc.player.arrows, this.width / 2 + 8, this.height - 33, 16777215);
      }

      byte var25 = 10;
      boolean var27 = false;
      if(this.mc.currentScreen instanceof ChatInputScreen) {
         var25 = 20;
         var27 = true;
      }

      for(var14 = 0; var14 < this.chat.size() && var14 < var25; ++var14) {
         if(((ChatLine)this.chat.get(var14)).time < 200 || var27) {
            var5.render(((ChatLine)this.chat.get(var14)).message, 2, this.height - 8 - var14 * 9 - 20, 16777215);
         }
      }

      var14 = this.width / 2;
      var15 = this.height / 2;
      this.hoveredPlayer = null;
      if(Keyboard.isKeyDown(15) && this.mc.networkManager != null && this.mc.networkManager.isConnected()) {
         List var22 = this.mc.networkManager.getPlayers();
         GL11.glEnable(3042);
         GL11.glDisable(3553);
         GL11.glBlendFunc(770, 771);
         GL11.glBegin(7);
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.7F);
         GL11.glVertex2f((float)(var14 + 128), (float)(var15 - 68 - 12));
         GL11.glVertex2f((float)(var14 - 128), (float)(var15 - 68 - 12));
         GL11.glColor4f(0.2F, 0.2F, 0.2F, 0.8F);
         GL11.glVertex2f((float)(var14 - 128), (float)(var15 + 68));
         GL11.glVertex2f((float)(var14 + 128), (float)(var15 + 68));
         GL11.glEnd();
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         var23 = "Connected players:";
         var5.render(var23, var14 - var5.getWidth(var23) / 2, var15 - 64 - 12, 16777215);

         for(var11 = 0; var11 < var22.size(); ++var11) {
            int var28 = var14 + var11 % 2 * 120 - 120;
            int var17 = var15 - 64 + (var11 / 2 << 3);
            if(var2 && var3 >= var28 && var4 >= var17 && var3 < var28 + 120 && var4 < var17 + 8) {
               this.hoveredPlayer = (String)var22.get(var11);
               var5.renderNoShadow((String)var22.get(var11), var28 + 2, var17, 16777215);
            } else {
               var5.renderNoShadow((String)var22.get(var11), var28, var17, 15658734);
            }
         }
      }

   }

   public final void addChat(String var1) {
      this.chat.add(0, new ChatLine(var1));

      while(this.chat.size() > 50) {
         this.chat.remove(this.chat.size() - 1);
      }

   }
}
