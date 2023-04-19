package me.efekos.awakensmponline.menus;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.ParticleColor;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ArmorMenu extends Menu{
    public ArmorMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void back() throws MenuManagerException, MenuManagerNotSetupException {
        super.back();
    }

    @Override
    protected void reloadItems() {
        super.reloadItems();
    }

    @Override
    protected void reload() throws MenuManagerException, MenuManagerNotSetupException {
        super.reload();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return super.getInventory();
    }

    /**
     * This will fill all the empty slots with "filler glass"
     */
    @Override
    public void setFillerGlass() {
        super.setFillerGlass();
    }

    /**
     * @param itemStack Placed into every empty slot when ran
     */
    @Override
    public void setFillerGlass(ItemStack itemStack) {
        super.setFillerGlass(itemStack);
    }

    /**
     * @param material    The material to base the ItemStack on
     * @param displayName The display name of the ItemStack
     * @param lore        The lore of the ItemStack, with the Strings being automatically color coded with ColorTranslator
     * @return The constructed ItemStack object
     */
    @Override
    public ItemStack makeItem(Material material, String displayName, String... lore) {
        return super.makeItem(material, displayName, lore);
    }

    @Override
    public String getMenuName() {
        return "zırh görme";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    private void setColor(InventoryClickEvent e,ParticleColor color){
        Player p = (Player) e.getWhoClicked();
        PlayerData data = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());
        assert data != null;
        data.getParticleOptions().setColor(color);
        DeadPlayersJSON.updateData(data.getPlayerUniqueId(),data);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
    }

    @Override
    public void setMenuItems() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        PlayerInventory armorInventory = playerMenuUtility.getData("invToOpen", PlayerInventory.class);
        if(armorInventory.getHelmet() != null) inventory.setItem(0,armorInventory.getHelmet());
        if(armorInventory.getChestplate() != null) inventory.setItem(1,armorInventory.getChestplate());
        if(armorInventory.getLeggings() != null) inventory.setItem(2,armorInventory.getLeggings());
        if(armorInventory.getBoots() != null) inventory.setItem(3,armorInventory.getBoots());
    }
}
