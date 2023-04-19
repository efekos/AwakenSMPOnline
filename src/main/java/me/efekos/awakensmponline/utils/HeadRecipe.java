package me.efekos.awakensmponline.utils;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeadRecipe {

    public static ShapedRecipe getDefault(AwakenSMPOnline plugin){
        ShapedRecipe headRecipe = new ShapedRecipe(new NamespacedKey(plugin,"reviveHead"),getHead(plugin.getConfig()));
        headRecipe.shape("DND","TWT","DND");
        headRecipe.setIngredient('D',Material.DIAMOND_BLOCK);
        headRecipe.setIngredient('N',Material.NETHERITE_INGOT);
        headRecipe.setIngredient('T',Material.TOTEM_OF_UNDYING);
        headRecipe.setIngredient('W',Material.NETHER_STAR);
        return headRecipe;
    }

    public static ItemStack getHead(Configuration config){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD,1);
        ItemMeta headMeta = head.getItemMeta();
        Objects.requireNonNull(headMeta).setDisplayName(ColorTranslator.translateColorCodes(Objects.requireNonNull(config.getString("recipe.head.name"))));
        List<String> lore = new ArrayList<>();
        lore.add(ColorTranslator.translateColorCodes(Objects.requireNonNull(config.getString("recipe.head.desc"))));
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        return head;
    }

    public static ShapedRecipe getRecipeFromConfig(FileConfiguration config,AwakenSMPOnline plugin){
        ShapedRecipe headRecipe = new ShapedRecipe(new NamespacedKey(plugin,"reviveHead"),getHead(plugin.getConfig()));
        List<String> shape = config.getStringList("recipe.shape");
        headRecipe.shape(shape.get(0), shape.get(1), shape.get(2));

        for (String shapePiece : shape) {
            headRecipe.setIngredient(shapePiece.charAt(0), Objects.requireNonNull(Material.matchMaterial(Objects.requireNonNull(config.getString("recipe.materials." + shapePiece.charAt(0))))));
            headRecipe.setIngredient(shapePiece.charAt(1), Objects.requireNonNull(Material.matchMaterial(Objects.requireNonNull(config.getString("recipe.materials." + shapePiece.charAt(1))))));
            headRecipe.setIngredient(shapePiece.charAt(2), Objects.requireNonNull(Material.matchMaterial(Objects.requireNonNull(config.getString("recipe.materials." + shapePiece.charAt(2))))));
        }
        return headRecipe;
    }

}
