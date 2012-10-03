package com.mojang.minecraft.model;

import com.mojang.minecraft.model.AnimalModel;
import com.mojang.minecraft.model.CreeperModel;
import com.mojang.minecraft.model.HumanoidModel;
import com.mojang.minecraft.model.Model;
import com.mojang.minecraft.model.PigModel;
import com.mojang.minecraft.model.SheepFurModel;
import com.mojang.minecraft.model.SheepModel;
import com.mojang.minecraft.model.SkeletonModel;
import com.mojang.minecraft.model.SpiderModel;
import com.mojang.minecraft.model.ZombieModel;

public final class ModelManager {

   private HumanoidModel human = new HumanoidModel(0.0F);
   private HumanoidModel armoredHuman = new HumanoidModel(1.0F);
   private CreeperModel creeper = new CreeperModel();
   private SkeletonModel skeleton = new SkeletonModel();
   private ZombieModel zombie = new ZombieModel();
   private AnimalModel pig = new PigModel();
   private AnimalModel sheep = new SheepModel();
   private SpiderModel spider = new SpiderModel();
   private SheepFurModel sheepFur = new SheepFurModel();


   public final Model getModel(String var1) {
      return (Model)(var1.equals("humanoid")?this.human:(var1.equals("humanoid.armor")?this.armoredHuman:(var1.equals("creeper")?this.creeper:(var1.equals("skeleton")?this.skeleton:(var1.equals("zombie")?this.zombie:(var1.equals("pig")?this.pig:(var1.equals("sheep")?this.sheep:(var1.equals("spider")?this.spider:(var1.equals("sheep.fur")?this.sheepFur:null)))))))));
   }
}
