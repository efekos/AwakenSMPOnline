package me.efekos.awakensmponline.recipe;

import me.efekos.awakensmponline.config.GameConfig;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.exceptions.InvalidRecipeException;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class RecipeManager {
    private static ShapedRecipe lastLoadedRecipe;

    public static ShapedRecipe getLastLoadedRecipe() {
        return lastLoadedRecipe;
    }

    private static ItemStack createHead(){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD,1);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        assert meta != null;
        meta.setDisplayName(TranslateManager.translateColors(LangConfig.get("items.revive_head.name")));
        meta.setLore(Collections.singletonList(TranslateManager.translateColors(LangConfig.get("items.revive_head.description"))));
        skull.setItemMeta(meta);

        return skull;
    }

    public static ShapedRecipe loadDefaultRecipe(JavaPlugin plugin){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"revive_head"),createHead());

        recipe.shape("DND","TWT","DND");
        recipe.setIngredient('D',Material.DIAMOND_BLOCK);
        recipe.setIngredient('T',Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('W',Material.NETHER_STAR);
        recipe.setIngredient('N',Material.NETHERITE_INGOT);

        return recipe;
    }

    public static ShapedRecipe loadConfigRecipe(JavaPlugin plugin) throws InvalidRecipeException {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin,"revive_head"),createHead());

        List<String> shapeStrings = GameConfig.get().getStringList("recipe.shape");

        recipe.shape(shapeStrings.get(0),shapeStrings.get(1),shapeStrings.get(2));

        ArrayList<Character> addedMaterials = new ArrayList<>();
        for (String shapeString:shapeStrings){

            // make sure shape piece is 3 chars
            if(shapeString.length()>3) throw new InvalidRecipeException(shapeString + "Is "+shapeString.length()+" characters, but it must be 3.");

            for(char character:shapeString.toCharArray()){

                // get rid of registering same key more than one times
                if(!addedMaterials.contains(character)){
                    addedMaterials.add(character);

                    //make sure given key exists
                    String materialString = GameConfig.get().getString("recipe.materials." + character);
                    if(materialString==null) throw new InvalidRecipeException("Key '"+character+"' is used in shape, but there is no material for this key.");

                    //make sure given key is a valid material
                    Material material = Material.matchMaterial(materialString);
                    if(material==null) throw new InvalidRecipeException("recipe.materials."+character +" is not a valid material. Please see https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html for valid materials.");

                    recipe.setIngredient(character, material);
                }

            }
        }

        return recipe;
    }
}
