package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.data.AnimationType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class AnimationManager {
    public static void playAnimation(Player player, AnimationType animationType, Consumer<Player> spawnPlayer){
        try {
            Location location = player.getLocation();
            World world = player.getWorld();

            switch (animationType){
                case NONE:
                case CURSE:
                    spawnPlayer.accept(player);
                    break;
                case BLOCK:
                    Location location1 = player.getLocation();
                    Location location2 = player.getLocation().add(0,1,0);
                    Block underneath = player.getLocation().add(0,-1,0).getBlock();

                    location1.getBlock().setType(underneath.getType());
                    world.playSound(location1, Sound.BLOCK_STONE_PLACE, SoundCategory.BLOCKS,100,1);

                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            location2.getBlock().setType(underneath.getType());
                            world.playSound(location2,Sound.BLOCK_STONE_PLACE,SoundCategory.BLOCKS,100,1);

                            new BukkitRunnable(){
                                @Override
                                public void run() {
                                    location1.getBlock().setType(Material.AIR);
                                    location2.getBlock().setType(Material.AIR);
                                    world.spawnParticle(Particle.BLOCK_DUST,location1,100,.1,.1,.1,underneath.getBlockData());
                                    world.spawnParticle(Particle.BLOCK_DUST,location2,100,.1,.1,.1,underneath.getBlockData());
                                    world.playSound(location1,Sound.BLOCK_STONE_BREAK,SoundCategory.BLOCKS,100,1);
                                    world.playSound(location2,Sound.BLOCK_STONE_BREAK,SoundCategory.BLOCKS,100,1);
                                    spawnPlayer.accept(player); // tells him "spawn executed" for now.
                                }
                            }.runTaskLater(AwakenSMPOnline.getPlugin(),400/50);


                        }
                    }.runTaskLater(AwakenSMPOnline.getPlugin(),400/50);

                    break;
                case THUNDER:

                    BukkitRunnable runnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            world.strikeLightningEffect(location);
                        }
                    };
                    run(o -> runnable.run(),200/50);
                    run(o -> runnable.run(),600/50);
                    run(o -> runnable.run(),800/50);
                    run(o -> runnable.run(),900/50);
                    run(o -> runnable.run(),1100/50);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            runnable.run();
                            spawnPlayer.accept(player);

                        }
                    }.runTaskLater(AwakenSMPOnline.getPlugin(),1400/50);

                    break;
                case BEAM:

                    player.getLocation().add(1,0,0).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(0,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(1,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(-1,0,0).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(0,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(-1,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(1,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                    player.getLocation().add(-1,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                    world.playSound(player.getLocation(),Sound.BLOCK_METAL_PLACE,SoundCategory.BLOCKS,100,1);

                    AtomicBoolean a = new AtomicBoolean(true);
                    run(o -> {
                        player.getLocation().getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(0,1,0).getBlock().setType(Material.BEACON);
                        player.teleport(player.getLocation().add(0,2,0));
                    },400/20);
                    run(o -> {
                        a.set(false);
                        spawnPlayer.accept(player);
                    },1000/50);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void run(Consumer<Object> consumer,long delay){
        new BukkitRunnable(){
            @Override
            public void run() {
                consumer.accept("");
            }
        }.runTaskLater(AwakenSMPOnline.getPlugin(),delay);
    }
}