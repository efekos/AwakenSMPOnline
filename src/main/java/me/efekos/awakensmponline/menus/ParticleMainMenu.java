package me.efekos.awakensmponline.menus;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ParticleMainMenu extends Menu {
    public ParticleMainMenu(PlayerMenuUtility playerMenuUtility) {
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.particle-menu.main-title");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        Player p = (Player) e.getWhoClicked();
        switch (Objects.requireNonNull(e.getCurrentItem()).getType()){
            case BARRIER:
                p.closeInventory();
                break;
            case WHITE_WOOL:
                p.closeInventory();
                MenuManager.openMenu(ParticleColorSelector.class,p);
                break;
            case EMERALD:
                p.closeInventory();
                MenuManager.openMenu(ParticleTypeSelector.class,p);
                break;
        }
    }

    @Override
    public void setMenuItems() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String path = "messages.commands.particle-menu.";
        ItemStack close = makeItem(Material.BARRIER, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "btn-close"))));
        inventory.setItem(22,close);

        inventory.setItem(11,makeItem(Material.WHITE_WOOL, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "main-color")))));
        inventory.setItem(15,makeItem(Material.EMERALD, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "main-style")))));

        setFillerGlass(FILLER_GLASS);
    }
}
