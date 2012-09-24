package com.mojang.minecraft.gui;

import com.mojang.minecraft.gui.LoadLevelScreen;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

final class LevelDialog extends Thread {

   // $FF: synthetic field
   private LoadLevelScreen screen;


   LevelDialog(LoadLevelScreen var1) {
	   super();
      this.screen = var1;
   }

   public final void run() {
      JFileChooser var1;
      LoadLevelScreen var2;
      try {
         LoadLevelScreen var10000 = this.screen;
         var1 = new JFileChooser();
         var10000.chooser = var1;
         FileNameExtensionFilter var3 = new FileNameExtensionFilter("Minecraft levels", new String[]{"mine"});
         var2 = this.screen;
         this.screen.chooser.setFileFilter(var3);
         var2 = this.screen;
         this.screen.chooser.setMultiSelectionEnabled(false);
         int var7;
         if(this.screen.saving) {
            var2 = this.screen;
            var7 = this.screen.chooser.showSaveDialog(this.screen.minecraft.canvas);
         } else {
            var2 = this.screen;
            var7 = this.screen.chooser.showOpenDialog(this.screen.minecraft.canvas);
         }

         if(var7 == 0) {
            (var2 = this.screen).selectedFile = this.screen.chooser.getSelectedFile();
         }
      } finally {
         boolean var6 = false;
         var2 = this.screen;
         this.screen.frozen = false;
         var1 = null;
         var2 = this.screen;
         this.screen.chooser = var1;
      }

   }
}
