package com.mojang.minecraft.gui;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.gui.Button;
import com.mojang.minecraft.gui.GuiScreen;
import com.mojang.minecraft.gui.LevelDialog;
import com.mojang.minecraft.level.Level;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class LoadLevelScreen extends GuiScreen implements Runnable {

   protected GuiScreen parent;
   private boolean finished = false;
   private boolean loaded = false;
   private String[] levels = null;
   private String status = "";
   protected String title = "Load level";
   boolean frozen = false;
   JFileChooser chooser;
   protected boolean saving = false;
   protected File selectedFile;


   public LoadLevelScreen(GuiScreen var1) {
      this.parent = var1;
   }

   public void run() {
      try {
         if(this.frozen) {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException var2) {
               var2.printStackTrace();
            }
         }

         this.status = "Getting level list..";
         URL var1 = new URL("http://" + this.minecraft.host + "/listmaps.jsp?user=" + this.minecraft.session.username);
         BufferedReader var4 = new BufferedReader(new InputStreamReader(var1.openConnection().getInputStream()));
         this.levels = var4.readLine().split(";");
         if(this.levels.length >= 5) {
            this.setLevels(this.levels);
            this.loaded = true;
            return;
         }

         this.status = this.levels[0];
         this.finished = true;
      } catch (Exception var3) {
         var3.printStackTrace();
         this.status = "Failed to load levels";
         this.finished = true;
      }

   }

   protected void setLevels(String[] var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         ((Button)this.buttons.get(var2)).active = !var1[var2].equals("-");
         ((Button)this.buttons.get(var2)).text = var1[var2];
         ((Button)this.buttons.get(var2)).visible = true;
      }

   }

   public void onOpen() {
      (new Thread(this)).start();

      for(int var1 = 0; var1 < 5; ++var1) {
         this.buttons.add(new Button(var1, this.width / 2 - 100, this.height / 6 + var1 * 24, "---"));
         ((Button)this.buttons.get(var1)).visible = false;
         ((Button)this.buttons.get(var1)).active = false;
      }

      this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 6 + 120 + 12, "Load file..."));
      this.buttons.add(new Button(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
   }

   protected final void onButtonClick(Button var1) {
      if(!this.frozen) {
         if(var1.active) {
            if(this.loaded && var1.id < 5) {
               this.openLevel(var1.id);
            }

            if(this.finished || this.loaded && var1.id == 5) {
               this.frozen = true;
               LevelDialog var2;
               (var2 = new LevelDialog(this)).setDaemon(true);
               SwingUtilities.invokeLater(var2);
            }

            if(this.finished || this.loaded && var1.id == 6) {
               this.minecraft.setCurrentScreen(this.parent);
            }

         }
      }
   }

   protected void openLevel(File var1) {
      File var2 = var1;
      Minecraft var4 = this.minecraft;
      Level var5;
      boolean var10000;
      if((var5 = this.minecraft.levelIo.load(var2)) == null) {
         var10000 = false;
      } else {
         var4.setLevel(var5);
         var10000 = true;
      }

      this.minecraft.setCurrentScreen(this.parent);
   }

   protected void openLevel(int var1) {
      this.minecraft.loadOnlineLevel(this.minecraft.session.username, var1);
      this.minecraft.setCurrentScreen((GuiScreen)null);
      this.minecraft.grabMouse();
   }

   public void render(int var1, int var2) {
      drawFadingBox(0, 0, this.width, this.height, 1610941696, -1607454624);
      drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
      if(this.frozen) {
         drawCenteredString(this.fontRenderer, "Selecting file..", this.width / 2, this.height / 2 - 4, 16777215);

         try {
            Thread.sleep(20L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      } else {
         if(!this.loaded) {
            drawCenteredString(this.fontRenderer, this.status, this.width / 2, this.height / 2 - 4, 16777215);
         }

         super.render(var1, var2);
      }
   }

   public final void onClose() {
      super.onClose();
      if(this.chooser != null) {
         this.chooser.cancelSelection();
      }

   }

   public final void tick() {
      super.tick();
      if(this.selectedFile != null) {
         this.openLevel(this.selectedFile);
         this.selectedFile = null;
      }

   }
}
