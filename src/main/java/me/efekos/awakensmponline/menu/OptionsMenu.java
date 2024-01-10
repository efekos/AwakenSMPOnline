package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.Main;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.menu.MenuManager;
import me.efekos.simpler.translation.TranslateManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class OptionsMenu extends Menu {
    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 3;
    }

    public OptionsMenu(MenuData data) {
        super(data);
    }

    @Override
    public String getTitle() {
        return TranslateManager.translateColors(Main.LANG.getString("menus.options.title", "Options"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();

        switch (event.getCurrentItem().getType()) {
            case TOTEM_OF_UNDYING:
                MenuManager.Open(p, ParticleTypeMenu.class);
                break;
            case WHITE_CONCRETE:
                MenuManager.Open(p, ParticleColorsMenu.class);
                break;
            case BEACON:
                MenuManager.Open(p, AnimationTypeMenu.class);
                break;
        }
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void fill() {
        inventory.setItem(12, createItem(Material.TOTEM_OF_UNDYING, TranslateManager.translateColors(Main.LANG.getString("menus.options.type", "&eParticle Type"))));
        inventory.setItem(13, createItem(Material.BEACON, TranslateManager.translateColors(Main.LANG.getString("menus.options.animation", "&eRevive Animation"))));
        inventory.setItem(14, createItem(Material.WHITE_CONCRETE, TranslateManager.translateColors(Main.LANG.getString("menus.options.color", "&eParticle Color"))));
        fillEmptyWith(createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
    }
}
