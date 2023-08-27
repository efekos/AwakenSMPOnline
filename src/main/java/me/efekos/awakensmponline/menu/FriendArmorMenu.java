package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.simpler.translation.TranslateManager;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.PlayerInventory;

public class FriendArmorMenu extends Menu {
    public FriendArmorMenu(MenuData data) {
        super(data);
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    public String getTitle() {
        return TranslateManager.translateColors(AwakenSMPOnline.LANG.getString("menus.friend_armor.title","%player%'s Armor")
                .replace("%player%",((PlayerInventory)data.get("invToOpen")).getHolder().getName())
        );
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void onOpen(InventoryOpenEvent event) {

    }

    @Override
    public void fill() {
        PlayerInventory inventory = (PlayerInventory) data.get("invToOpen");
        if(inventory==null) return;

        if(inventory.getHelmet()!=null) getInventory().setItem(0,inventory.getHelmet());
        if(inventory.getChestplate()!=null) getInventory().setItem(1,inventory.getChestplate());
        if(inventory.getLeggings()!=null) getInventory().setItem(2,inventory.getLeggings());
        if(inventory.getBoots()!=null) getInventory().setItem(3,inventory.getBoots());
        getInventory().setItem(7,inventory.getItemInMainHand());
        getInventory().setItem(8,inventory.getItemInOffHand());

        data.set("invToOpen",null);
    }
}
