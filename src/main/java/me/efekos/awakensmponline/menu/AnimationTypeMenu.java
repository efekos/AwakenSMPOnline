package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.AnimationType;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.commands.translation.TranslateManager;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class AnimationTypeMenu extends Menu {
    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public String getTitle() {
        return TranslateManager.translateColors(LangConfig.get("menus.options_animation_type.title"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        PlayerData data = PlayerDataManager.fetch(p.getUniqueId());


        switch (event.getCurrentItem().getType()) {
            case PAPER:
                back();
                break;
            case BLACK_STAINED_GLASS_PANE:
                break;
            default:
                data.setSelectedAnimation(translateMaterial(event.getCurrentItem().getType()));
                PlayerDataManager.update(data.getUuid(),data);

                p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 100, 1);
                refresh();
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    private AnimationType translateMaterial(Material material){
        if(material == Material.BARRIER) return AnimationType.NONE;
        if(material == Material.STONE) return AnimationType.BLOCK;
        if(material == Material.BEACON) return AnimationType.BEAM;
        if(material == Material.BLAZE_POWDER) return AnimationType.THUNDER;
        return AnimationType.NONE;
    }

    @Override
    public void fill() {
        inventory.setItem(0,createItem(Material.BARRIER,TranslateManager.translateColors(LangConfig.get("menus.options_animation_type.types.none"))));
        inventory.setItem(1,createItem(Material.STONE,TranslateManager.translateColors(LangConfig.get("menus.options_animation_type.types.block"))));
        inventory.setItem(2,createItem(Material.BLAZE_POWDER,TranslateManager.translateColors(LangConfig.get("menus.options_animation_type.types.thunder"))));
        inventory.setItem(3,createItem(Material.BEACON,TranslateManager.translateColors(LangConfig.get("menus.options_animation_type.types.beam"))));

        PlayerData data = PlayerDataManager.fetch(owner.getUniqueId());
        AnimationType type = data.getSelectedAnimation();

        for (int i = 0; i < 4; i++) {
            ItemStack item = inventory.getItem(i);
            ItemMeta meta = item.getItemMeta();

            boolean isThis = type == translateMaterial(item.getType());

            meta.setLore(Arrays.asList(" ", TranslateManager.translateColors(LangConfig.get(isThis ? "menus.options.selected" : "menus.options.unselected"))));
            if(isThis) meta.addEnchant(Enchantment.MENDING,1,true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }

        inventory.setItem(26,createItem(Material.PAPER,TranslateManager.translateColors(LangConfig.get("menus.buttons.back"))));

        fillEmptyWith(createItem(Material.BLACK_STAINED_GLASS_PANE," "));
    }

    public AnimationTypeMenu(MenuData data) {
        super(data);
    }
}
