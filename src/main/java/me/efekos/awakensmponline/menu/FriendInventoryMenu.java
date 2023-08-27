package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.Main;
import me.efekos.simpler.translation.TranslateManager;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class FriendInventoryMenu extends Menu {
    public FriendInventoryMenu(MenuData data) {
        super(data);
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public int getRows() {
        return 4;
    }

    @Override
    public String getTitle() {
        return TranslateManager.translateColors(Main.LANG.getString("menus.friend_inventory.title","%player%'s Inventory")
                .replace("%player%",((PlayerInventory)data.get("invToOpen")).getHolder().getName()+"")
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
        if(inventory==null)return;

        for (int i = 0; i <= 35; i++) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack!=null)
            getInventory().setItem(i,itemStack);
        }
        data.set("invToOpen",null);
    }
}
