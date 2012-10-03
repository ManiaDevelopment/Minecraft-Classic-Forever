package com.mojang.minecraft.level;

import com.mojang.minecraft.ProgressBarDisplay;
import com.mojang.minecraft.level.Level;
import com.mojang.minecraft.level.LevelObjectInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class LevelIO {

   private ProgressBarDisplay progressBar;


   public LevelIO(ProgressBarDisplay var1) {
      this.progressBar = var1;
   }

   public final boolean save(Level var1, File var2) {
      try {
         FileOutputStream var5 = new FileOutputStream(var2);
         save(var1, (OutputStream)var5);
         var5.close();
         return true;
      } catch (Exception var4) {
         var4.printStackTrace();
         if(this.progressBar != null) {
            this.progressBar.setText("Failed!");
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
            ;
         }

         return false;
      }
   }

   public final Level load(File var1) {
      try {
         FileInputStream var5 = new FileInputStream(var1);
         Level var2 = this.load((InputStream)var5);
         var5.close();
         return var2;
      } catch (Exception var4) {
         var4.printStackTrace();
         if(this.progressBar != null) {
            this.progressBar.setText("Failed!");
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var3) {
            ;
         }

         return null;
      }
   }

   public final boolean saveOnline(Level var1, String var2, String var3, String var4, String var5, int var6) {
      if(var4 == null) {
         var4 = "";
      }

      if(this.progressBar != null && this.progressBar != null) {
         this.progressBar.setTitle("Saving level");
      }

      try {
         if(this.progressBar != null && this.progressBar != null) {
            this.progressBar.setText("Compressing..");
         }

         ByteArrayOutputStream var7 = new ByteArrayOutputStream();
         save(var1, (OutputStream)var7);
         var7.close();
         byte[] var10 = var7.toByteArray();
         if(this.progressBar != null && this.progressBar != null) {
            this.progressBar.setText("Connecting..");
         }

         HttpURLConnection var12;
         (var12 = (HttpURLConnection)(new URL("http://" + var2 + "/level/save.html")).openConnection()).setDoInput(true);
         var12.setDoOutput(true);
         var12.setRequestMethod("POST");
         DataOutputStream var13;
         (var13 = new DataOutputStream(var12.getOutputStream())).writeUTF(var3);
         var13.writeUTF(var4);
         var13.writeUTF(var5);
         var13.writeByte(var6);
         var13.writeInt(var10.length);
         if(this.progressBar != null) {
            this.progressBar.setText("Saving..");
         }

         var13.write(var10);
         var13.close();
         BufferedReader var11;
         if(!(var11 = new BufferedReader(new InputStreamReader(var12.getInputStream()))).readLine().equalsIgnoreCase("ok")) {
            if(this.progressBar != null) {
               this.progressBar.setText("Failed: " + var11.readLine());
            }

            var11.close();
            Thread.sleep(1000L);
            return false;
         } else {
            var11.close();
            return true;
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         if(this.progressBar != null) {
            this.progressBar.setText("Failed!");
         }

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var8) {
            ;
         }

         return false;
      }
   }

   public final Level loadOnline(String var1, String var2, int var3) {
      if(this.progressBar != null) {
         this.progressBar.setTitle("Loading level");
      }

      try {
         if(this.progressBar != null) {
            this.progressBar.setText("Connecting..");
         }

         HttpURLConnection var6;
         (var6 = (HttpURLConnection)(new URL("http://" + var1 + "/level/load.html?id=" + var3 + "&user=" + var2)).openConnection()).setDoInput(true);
         if(this.progressBar != null) {
            this.progressBar.setText("Loading..");
         }

         DataInputStream var7;
         if((var7 = new DataInputStream(var6.getInputStream())).readUTF().equalsIgnoreCase("ok")) {
            return this.load((InputStream)var7);
         } else {
            if(this.progressBar != null) {
               this.progressBar.setText("Failed: " + var7.readUTF());
            }

            var7.close();
            Thread.sleep(1000L);
            return null;
         }
      } catch (Exception var5) {
         var5.printStackTrace();
         if(this.progressBar != null) {
            this.progressBar.setText("Failed!");
         }

         try {
            Thread.sleep(3000L);
         } catch (InterruptedException var4) {
            ;
         }

         return null;
      }
   }

   public final Level load(InputStream var1) {
      if(this.progressBar != null) {
         this.progressBar.setTitle("Loading level");
      }

      if(this.progressBar != null) {
         this.progressBar.setText("Reading..");
      }

      try {
         DataInputStream var10;
         if((var10 = new DataInputStream(new GZIPInputStream(var1))).readInt() != 656127880) {
            return null;
         } else {
            byte var12;
            if((var12 = var10.readByte()) > 2) {
               return null;
            } else if(var12 <= 1) {
               String var14 = var10.readUTF();
               String var15 = var10.readUTF();
               long var3 = var10.readLong();
               short var5 = var10.readShort();
               short var6 = var10.readShort();
               short var7 = var10.readShort();
               byte[] var8 = new byte[var5 * var6 * var7];
               var10.readFully(var8);
               var10.close();
               Level var11;
               (var11 = new Level()).setData(var5, var7, var6, var8);
               var11.name = var14;
               var11.creator = var15;
               var11.createTime = var3;
               return var11;
            } else {
               Level var2;
               LevelObjectInputStream var13;
               (var2 = (Level)(var13 = new LevelObjectInputStream(var10)).readObject()).initTransient();
               var13.close();
               return var2;
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
         System.out.println("Failed to load level: " + var9.toString());
         return null;
      }
   }

   public static void save(Level var0, OutputStream var1) {
      try {
         DataOutputStream var3;
         (var3 = new DataOutputStream(new GZIPOutputStream(var1))).writeInt(656127880);
         var3.writeByte(2);
         ObjectOutputStream var4;
         (var4 = new ObjectOutputStream(var3)).writeObject(var0);
         var4.close();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   public static byte[] decompress(InputStream var0) {
      try {
         DataInputStream var3;
         byte[] var1 = new byte[(var3 = new DataInputStream(new GZIPInputStream(var0))).readInt()];
         var3.readFully(var1);
         var3.close();
         return var1;
      } catch (Exception var2) {
         throw new RuntimeException(var2);
      }
   }
}
