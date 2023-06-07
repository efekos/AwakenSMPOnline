package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.data.ParticleColor;
import me.efekos.awakensmponline.data.ParticleOptions;
import me.efekos.awakensmponline.data.ParticleType;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ParticleManager {

    public static Material translateColor(ParticleColor type){
        if(type==ParticleColor.WHITE) return Material.WHITE_CONCRETE;
        if(type==ParticleColor.ORANGE) return Material.ORANGE_CONCRETE;
        if(type==ParticleColor.MAGENTA) return Material.MAGENTA_CONCRETE;
        if(type==ParticleColor.LIGHT_BLUE) return Material.LIGHT_BLUE_CONCRETE;
        if(type==ParticleColor.YELLOW) return Material.YELLOW_CONCRETE;
        if(type==ParticleColor.LIME) return Material.LIME_CONCRETE;
        if(type==ParticleColor.PINK) return Material.PINK_CONCRETE;
        if(type==ParticleColor.GRAY) return Material.GRAY_CONCRETE;
        if(type==ParticleColor.LIGHT_GRAY) return Material.LIGHT_GRAY_CONCRETE;
        if(type==ParticleColor.CYAN) return Material.CYAN_CONCRETE;
        if(type==ParticleColor.PURPLE) return Material.PURPLE_CONCRETE;
        if(type==ParticleColor.BLUE) return Material.BLUE_CONCRETE;
        if(type==ParticleColor.BROWN) return Material.BROWN_CONCRETE;
        if(type==ParticleColor.GREEN) return Material.GREEN_CONCRETE;
        if(type==ParticleColor.RED) return Material.RED_CONCRETE;
        if(type==ParticleColor.BLACK) return Material.BLACK_CONCRETE;
        return null;
    }

    public static Color translateBlock(Material block){
        if(block==Material.WHITE_CONCRETE) return Color.fromRGB(208,214,215);
        if(block==Material.ORANGE_CONCRETE) return Color.fromRGB(225,99,3);
        if(block==Material.MAGENTA_CONCRETE) return Color.fromRGB(168,48,159);
        if(block==Material.LIGHT_BLUE_CONCRETE) return Color.fromRGB(35,137,199);
        if(block==Material.YELLOW_CONCRETE) return Color.fromRGB(242,176,21);
        if(block==Material.LIME_CONCRETE) return Color.fromRGB(95,170,25);
        if(block==Material.PINK_CONCRETE) return Color.fromRGB(214,102,144);
        if(block==Material.GRAY_CONCRETE) return Color.fromRGB(54,57,61);
        if(block==Material.LIGHT_GRAY_CONCRETE) return Color.fromRGB(125,125,115);
        if(block==Material.CYAN_CONCRETE) return Color.fromRGB(21,119,136);
        if(block==Material.PURPLE_CONCRETE) return Color.fromRGB(101,32,157);
        if(block==Material.BLUE_CONCRETE) return Color.fromRGB(44,46,143);
        if(block==Material.BROWN_CONCRETE) return Color.fromRGB(97,60,32);
        if(block==Material.GREEN_CONCRETE) return Color.fromRGB(73,91,36);
        if(block==Material.RED_CONCRETE) return Color.fromRGB(143,35,35);
        if(block==Material.BLACK_CONCRETE) return Color.fromRGB(10,12,17);
        return null;
    }

    public static void spawnParticle(ParticleOptions options, Player player){
        World world = player.getWorld();

        switch (options.getType()){
            case TOTEM:
                world.spawnParticle(Particle.TOTEM,player.getLocation().add(0,1,0),100);
                break;
            case EXPLOSION:
                world.spawnParticle(Particle.EXPLOSION_NORMAL,player.getLocation().add(0,1,0),50,5,5,5);
                break;
            case POP:
                world.spawnParticle(Particle.CLOUD,player.getLocation().add(0,1,0),5);
                break;
            case BLOCK:
                world.spawnParticle(Particle.BLOCK_DUST,player.getLocation(),10,translateColor(options.getColor()).createBlockData());
                break;
            case FOG:
                world.spawnParticle(Particle.REDSTONE, player.getLocation().add(0,1,0), 300,0.5,1,0.5,new Particle.DustOptions(translateBlock(translateColor(options.getColor())),5.0F));
                break;
            case BEAM:
                Particle.DustOptions dustOptions = new Particle.DustOptions(translateBlock(translateColor(options.getColor())),3.0F);
                for(double i=0;i != 100;i += 0.5){
                    double finalI = i;
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            world.spawnParticle(Particle.REDSTONE, player.getLocation().add(0, finalI, 0), 100, .4, 0.6, .4, dustOptions);
                        }
                    }.runTaskLater(AwakenSMPOnline.getPlugin(),Math.round(i/4));
                }
                break;
        }
    }
}
