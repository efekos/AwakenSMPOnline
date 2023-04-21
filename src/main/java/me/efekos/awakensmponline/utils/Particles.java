package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.classes.ParticleColor;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Particles {

    private static Color getColor(ParticleColor color){
        switch (color){
            case WHITE:
                return Color.fromRGB(0xe6e6e6);
            case ORANGE:
                return Color.fromRGB(0xba6015);
            case MAGENTA:
                return Color.fromRGB(0x953a8d);
            case LIGHT_BLUE:
                return Color.fromRGB(0x2b86a3);
            case YELLOW:
                return Color.fromRGB(0xbea22);
            case LIME:
                return Color.fromRGB(0x609517);
            case PINK:
                return Color.fromRGB(0xb6687f);
            case GRAY:
                return Color.fromRGB(0x353b3d);
            case LIGHT_GRAY:
                return Color.fromRGB(0x757571);
            case CYAN:
                return Color.fromRGB(0x107575);
            case PURPLE:
                return Color.fromRGB(0x66258);
            case BLUE:
                return Color.fromRGB(0x2d337f);
            case BROWN:
                return Color.fromRGB(0x623f25);
            case GREEN:
                return Color.fromRGB(0x465d10);
            case RED:
                return Color.fromRGB(0x84221c);
            case BLACK:
                return Color.fromRGB(0x151518);
        }
        return Color.WHITE;
    }

    public static Material getColorBlock(ParticleColor color){
        switch (color){
            case GRAY:
                return Material.GRAY_CONCRETE;
            case RED:
                return Material.RED_CONCRETE;
            case BLUE:
                return Material.BLUE_CONCRETE;
            case CYAN:
                return Material.CYAN_CONCRETE;
            case LIME:
                return Material.LIME_CONCRETE;
            case PINK:
                return Material.PINK_CONCRETE;
            case BLACK:
                return Material.BLACK_CONCRETE;
            case BROWN:
                return Material.BROWN_CONCRETE;
            case GREEN:
                return Material.GREEN_CONCRETE;
            case WHITE:
                return Material.WHITE_CONCRETE;
            case ORANGE:
                return Material.ORANGE_CONCRETE;
            case PURPLE:
                return Material.PURPLE_CONCRETE;
            case YELLOW:
                return Material.YELLOW_CONCRETE;
            case MAGENTA:
                return Material.MAGENTA_CONCRETE;
            case LIGHT_GRAY:
                return Material.LIGHT_GRAY_CONCRETE;
            case LIGHT_BLUE:
                return Material.LIGHT_BLUE_CONCRETE;
        }
        return Material.WHITE_CONCRETE;
    }

    public static void Spawn(Player p){
        PlayerData data = PlayerDataManager.getDataFromUniqueId(p.getUniqueId());
        assert data != null;

        switch (data.getParticleOptions().getType()){
            case PUFF:
                Particle.DustOptions dustOptions = new Particle.DustOptions(getColor(data.getParticleOptions().getColor()),5.0F);
                p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().add(0,1,0), 300,0.5,1,0.5,dustOptions);
                break;
            case POP:
                p.getWorld().spawnParticle(Particle.CLOUD,p.getLocation().add(0,1,0),100,0,1,0);
                break;
            case EXPLOSION:
                p.getWorld().spawnParticle(Particle.EXPLOSION_LARGE,p.getLocation().add(0,1,0),5,1,1,1);
                break;
            case BEAM:
                for(double i=0;i != 100;i += 0.5){
                    Particle.DustOptions dustOptions2 = new Particle.DustOptions(getColor(data.getParticleOptions().getColor()),3.0F);
                    p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation().add(0, i, 0), 100, .4, 0.6, .4, dustOptions2);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e){e.printStackTrace();}
                }
                break;
            case DUST:
                p.getWorld().spawnParticle(Particle.TOWN_AURA,p.getLocation().add(0,1,0),300,.3,.5,.3);
                break;
            case POTION:
                p.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, p.getLocation().add(0, 0, 0), 1500, 0, 1, 0);
                break;
            case TOTEM:
                p.getWorld().spawnParticle(Particle.TOTEM, p.getLocation().add(0, 1, 0), 1000, 0, 0, 0, 1);
                break;
            case BLOCK:
                p.getWorld().spawnParticle(Particle.BLOCK_CRACK,p.getLocation().add(0,1,0),100,.25,.25,.25,Particles.getColorBlock(data.getParticleOptions().getColor()).createBlockData());
                p.getWorld().spawnParticle(Particle.BLOCK_CRACK,p.getLocation().add(0,2,0),100,.25,.25,.25,Particles.getColorBlock(data.getParticleOptions().getColor()).createBlockData());
        }
    }
}
