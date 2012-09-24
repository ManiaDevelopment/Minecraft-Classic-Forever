package com.mojang.minecraft;

import com.mojang.minecraft.KeyBinding;
import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.render.TextureManager;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.lwjgl.input.Keyboard;

public final class GameSettings {

   private static final String[] renderDistances = new String[]{"FAR", "NORMAL", "SHORT", "TINY"};
   public boolean music = true;
   public boolean sound = true;
   public boolean invertMouse = false;
   public boolean showFrameRate = false;
   public int viewDistance = 0;
   public boolean viewBobbing = true;
   public boolean anaglyph = false;
   public boolean limitFramerate = false;
   public KeyBinding forwardKey = new KeyBinding("Forward", 17);
   public KeyBinding leftKey = new KeyBinding("Left", 30);
   public KeyBinding backKey = new KeyBinding("Back", 31);
   public KeyBinding rightKey = new KeyBinding("Right", 32);
   public KeyBinding jumpKey = new KeyBinding("Jump", 57);
   public KeyBinding buildKey = new KeyBinding("Build", 48);
   public KeyBinding chatKey = new KeyBinding("Chat", 20);
   public KeyBinding toggleFogKey = new KeyBinding("Toggle fog", 33);
   public KeyBinding saveLocationKey = new KeyBinding("Save location", 28);
   public KeyBinding loadLocationKey = new KeyBinding("Load location", 19);
   public KeyBinding[] bindings;
   private Minecraft minecraft;
   private File settingsFile;
   public int settingCount;


   public GameSettings(Minecraft var1, File var2) {
      this.bindings = new KeyBinding[]{this.forwardKey, this.leftKey, this.backKey, this.rightKey, this.jumpKey, this.buildKey, this.chatKey, this.toggleFogKey, this.saveLocationKey, this.loadLocationKey};
      this.settingCount = 8;
      this.minecraft = var1;
      this.settingsFile = new File(var2, "options.txt");
      this.load();
   }

   public final String getBinding(int var1) {
      return this.bindings[var1].name + ": " + Keyboard.getKeyName(this.bindings[var1].key);
   }

   public final void setBinding(int var1, int var2) {
      this.bindings[var1].key = var2;
      this.save();
   }

   public final void toggleSetting(int var1, int var2) {
      if(var1 == 0) {
         this.music = !this.music;
      }

      if(var1 == 1) {
         this.sound = !this.sound;
      }

      if(var1 == 2) {
         this.invertMouse = !this.invertMouse;
      }

      if(var1 == 3) {
         this.showFrameRate = !this.showFrameRate;
      }

      if(var1 == 4) {
         this.viewDistance = this.viewDistance + var2 & 3;
      }

      if(var1 == 5) {
         this.viewBobbing = !this.viewBobbing;
      }

      if(var1 == 6) {
         this.anaglyph = !this.anaglyph;
         TextureManager var7 = this.minecraft.textureManager;
         Iterator var3 = this.minecraft.textureManager.textureImages.keySet().iterator();

         int var4;
         BufferedImage var5;
         while(var3.hasNext()) {
            var4 = ((Integer)var3.next()).intValue();
            var5 = (BufferedImage)var7.textureImages.get(Integer.valueOf(var4));
            var7.load(var5, var4);
         }

         var3 = var7.textures.keySet().iterator();

         while(var3.hasNext()) {
            String var8 = (String)var3.next();

            try {
               if(var8.startsWith("##")) {
                  var5 = TextureManager.load1(ImageIO.read(TextureManager.class.getResourceAsStream(var8.substring(2))));
               } else {
                  var5 = ImageIO.read(TextureManager.class.getResourceAsStream(var8));
               }

               var4 = ((Integer)var7.textures.get(var8)).intValue();
               var7.load(var5, var4);
            } catch (IOException var6) {
               var6.printStackTrace();
            }
         }
      }

      if(var1 == 7) {
         this.limitFramerate = !this.limitFramerate;
      }

      this.save();
   }

   public final String getSetting(int var1) {
      return var1 == 0?"Music: " + (this.music?"ON":"OFF"):(var1 == 1?"Sound: " + (this.sound?"ON":"OFF"):(var1 == 2?"Invert mouse: " + (this.invertMouse?"ON":"OFF"):(var1 == 3?"Show FPS: " + (this.showFrameRate?"ON":"OFF"):(var1 == 4?"Render distance: " + renderDistances[this.viewDistance]:(var1 == 5?"View bobbing: " + (this.viewBobbing?"ON":"OFF"):(var1 == 6?"3d anaglyph: " + (this.anaglyph?"ON":"OFF"):(var1 == 7?"Limit framerate: " + (this.limitFramerate?"ON":"OFF"):"")))))));
   }

   private void load() {
      try {
         if(this.settingsFile.exists()) {
            BufferedReader var1 = new BufferedReader(new FileReader(this.settingsFile));
            String var2 = null;

            while((var2 = var1.readLine()) != null) {
               String[] var5;
               if((var5 = var2.split(":"))[0].equals("music")) {
                  this.music = var5[1].equals("true");
               }

               if(var5[0].equals("sound")) {
                  this.sound = var5[1].equals("true");
               }

               if(var5[0].equals("invertYMouse")) {
                  this.invertMouse = var5[1].equals("true");
               }

               if(var5[0].equals("showFrameRate")) {
                  this.showFrameRate = var5[1].equals("true");
               }

               if(var5[0].equals("viewDistance")) {
                  this.viewDistance = Integer.parseInt(var5[1]);
               }

               if(var5[0].equals("bobView")) {
                  this.viewBobbing = var5[1].equals("true");
               }

               if(var5[0].equals("anaglyph3d")) {
                  this.anaglyph = var5[1].equals("true");
               }

               if(var5[0].equals("limitFramerate")) {
                  this.limitFramerate = var5[1].equals("true");
               }

               for(int var3 = 0; var3 < this.bindings.length; ++var3) {
                  if(var5[0].equals("key_" + this.bindings[var3].name)) {
                     this.bindings[var3].key = Integer.parseInt(var5[1]);
                  }
               }
            }

            var1.close();
         }
      } catch (Exception var4) {
         System.out.println("Failed to load options");
         var4.printStackTrace();
      }
   }

   private void save() {
      try {
         PrintWriter var1;
         (var1 = new PrintWriter(new FileWriter(this.settingsFile))).println("music:" + this.music);
         var1.println("sound:" + this.sound);
         var1.println("invertYMouse:" + this.invertMouse);
         var1.println("showFrameRate:" + this.showFrameRate);
         var1.println("viewDistance:" + this.viewDistance);
         var1.println("bobView:" + this.viewBobbing);
         var1.println("anaglyph3d:" + this.anaglyph);
         var1.println("limitFramerate:" + this.limitFramerate);

         for(int var2 = 0; var2 < this.bindings.length; ++var2) {
            var1.println("key_" + this.bindings[var2].name + ":" + this.bindings[var2].key);
         }

         var1.close();
      } catch (Exception var3) {
         System.out.println("Failed to save options");
         var3.printStackTrace();
      }
   }

}
