package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.data.AnimationType;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class AnimationManager {
    public static void playAnimation(Player player, AnimationType animationType, Consumer<Player> spawnPlayer){
        try {
            Location location = player.getLocation();
            World world = player.getWorld();

            switch (animationType){
                case NONE:
                    spawnPlayer.accept(player);
                    break;
                case BLOCK:
                    block: {
                        Location location1 = player.getLocation();
                        Location location2 = player.getLocation().add(0,1,0);
                        Block underneath = player.getLocation().add(0,-1,0).getBlock();

                        if(!location1.getBlock().getType().equals(Material.AIR) || !location2.getBlock().getType().equals(Material.AIR)){
                            playAnimation(player,AnimationType.NONE,spawnPlayer);
                            break;
                        }

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
                                        spawnPlayer.accept(player); // tells him "spawn executed" for now. // EDIT: now actually makes him out of spectator mode and tells him he should be in survival rn
                                    }
                                }.runTaskLater(AwakenSMPOnline.getPlugin(),400/50);


                            }
                        }.runTaskLater(AwakenSMPOnline.getPlugin(),400/50);
                    }
                    break;
                case THUNDER:
                    thunder: {
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

                    }
                    break;
                case BEAM:
                    fkinbeam: {
                        Location loc1 = player.getLocation().add(1,0,0);
                        Location loc2 = player.getLocation().add(0,0,1);
                        Location loc3 = player.getLocation().add(1,0,1);
                        Location loc4 = player.getLocation().add(-1,0,0);
                        Location loc5 = player.getLocation().add(0,0,-1);
                        Location loc6 = player.getLocation().add(-1,0,-1);
                        Location loc7 = player.getLocation().add(1,0,-1);
                        Location loc8 = player.getLocation().add(-1,0,1);
                        AtomicReference<Location> loc9 = new AtomicReference<>(player.getLocation());
                        AtomicReference<Location> loc10 = new AtomicReference<>(player.getLocation().add(0,1,0));

                        // thing to check that around is clear so player wont be able to use this anims blocky thing for removing chests or somethin
                        if(
                                loc1.getBlock().getType()!=Material.AIR ||
                                        loc2.getBlock().getType()!=Material.AIR ||
                                        loc3.getBlock().getType()!=Material.AIR ||
                                        loc4.getBlock().getType()!=Material.AIR ||
                                        loc5.getBlock().getType()!=Material.AIR ||
                                        loc6.getBlock().getType()!=Material.AIR ||
                                        loc7.getBlock().getType()!=Material.AIR ||
                                        loc8.getBlock().getType()!=Material.AIR ||
                                        loc9.get().getBlock().getType()!=Material.AIR ||
                                        loc10.get().getBlock().getType()!=Material.AIR
                        ){
                            playAnimation(player,AnimationType.NONE,spawnPlayer);
                            break;
                        }

                        BlockData block1 = loc1.getBlock().getBlockData().clone();
                        BlockData block2 = loc2.getBlock().getBlockData().clone();
                        BlockData block3 = loc3.getBlock().getBlockData().clone();
                        BlockData block4 = loc4.getBlock().getBlockData().clone();
                        BlockData block5 = loc5.getBlock().getBlockData().clone();
                        BlockData block6 = loc6.getBlock().getBlockData().clone();
                        BlockData block7 = loc7.getBlock().getBlockData().clone();
                        BlockData block8 = loc8.getBlock().getBlockData().clone();
                        AtomicReference<BlockData> block9 = new AtomicReference<>(loc9.get().getBlock().getBlockData().clone());
                        AtomicReference<BlockData> block10 = new AtomicReference<>(loc10.get().getBlock().getBlockData().clone());

                        player.getLocation().add(1,0,0).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(0,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(1,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(-1,0,0).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(0,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(-1,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(1,0,-1).getBlock().setType(Material.DIAMOND_BLOCK);
                        player.getLocation().add(-1,0,1).getBlock().setType(Material.DIAMOND_BLOCK);
                        world.playSound(player.getLocation(),Sound.BLOCK_METAL_PLACE,SoundCategory.BLOCKS,100,1);

                        BukkitRunnable runnable2 = new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.getWorld().spawnParticle(Particle.REDSTONE,player.getLocation().add(0,5,0),100,.5,5,.5,new Particle.DustOptions(Color.AQUA,4.0F));
                            }
                        };

                        AtomicReference<BukkitTask> task = new AtomicReference<>(null);

                        run(o -> {
                            player.getLocation().getBlock().setType(Material.DIAMOND_BLOCK);
                            player.getLocation().add(0,1,0).getBlock().setType(Material.BEACON);
                            player.teleport(player.getLocation().add(0,2,0));
                            task.set(runnable2.runTaskTimer(AwakenSMPOnline.getPlugin(), 0, 1));
                        },400/20);
                        run(o -> {
                            task.get().cancel();


                            player.getWorld().setBlockData(loc1,block1);
                            player.getWorld().setBlockData(loc2, block2);
                            player.getWorld().setBlockData(loc3,block3);
                            player.getWorld().setBlockData(loc4,block4);
                            player.getWorld().setBlockData(loc5,block5);
                            player.getWorld().setBlockData(loc6,block6);
                            player.getWorld().setBlockData(loc7,block7);
                            player.getWorld().setBlockData(loc8,block8);
                            player.getWorld().setBlockData(loc9.get(),block9.get());
                            player.getWorld().setBlockData(loc10.get(),block10.get());
                            spawnPlayer.accept(player);
                        },1000/20);
                    }
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