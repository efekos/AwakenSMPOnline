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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ParticleColorSelector extends Menu{
    public ParticleColorSelector(PlayerMenuUtility playerMenuUtility) {
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.particle-menu.color-title");
    }

    @Override
    public int getSlots() {
        return 27;
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
        switch (Objects.requireNonNull(e.getCurrentItem()).getType()){
            case BARRIER:
                playerMenuUtility.getOwner().closeInventory();
                break;
            case WHITE_CONCRETE:
                setColor(e,ParticleColor.WHITE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case BLACK_CONCRETE:
                setColor(e,ParticleColor.BLACK);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case BLUE_CONCRETE:
                setColor(e,ParticleColor.BLUE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case CYAN_CONCRETE:
                setColor(e,ParticleColor.CYAN);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case BROWN_CONCRETE:
                setColor(e,ParticleColor.BROWN);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case GRAY_CONCRETE:
                setColor(e,ParticleColor.GRAY);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case GREEN_CONCRETE:
                setColor(e,ParticleColor.GREEN);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case LIGHT_BLUE_CONCRETE:
                setColor(e,ParticleColor.LIGHT_BLUE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case LIGHT_GRAY_CONCRETE:
                setColor(e,ParticleColor.LIGHT_GRAY);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case LIME_CONCRETE:
                setColor(e,ParticleColor.LIME);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case MAGENTA_CONCRETE:
                setColor(e,ParticleColor.MAGENTA);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case ORANGE_CONCRETE:
                setColor(e,ParticleColor.ORANGE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case PINK_CONCRETE:
                setColor(e,ParticleColor.PINK);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case PURPLE_CONCRETE:
                setColor(e,ParticleColor.PURPLE);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case RED_CONCRETE:
                setColor(e,ParticleColor.RED);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case YELLOW_CONCRETE:
                setColor(e,ParticleColor.YELLOW);
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                break;
            case PAPER:
                p.closeInventory();
                MenuManager.openMenu(ParticleMainMenu.class,p);
        }
    }

    @Override
    public void setMenuItems() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String path = "messages.commands.particle-menu.";
        ItemStack close = makeItem(Material.BARRIER, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "btn-close"))));
        inventory.setItem(26,close);
        inventory.setItem(25,makeItem(Material.PAPER,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "btn-back")))));

        inventory.setItem(0,makeItem(Material.WHITE_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-white")))));
        inventory.setItem(1,makeItem(Material.ORANGE_CONCRETE, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-orange")))));
        inventory.setItem(2,makeItem(Material.MAGENTA_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-magenta")))));
        inventory.setItem(3,makeItem(Material.LIGHT_BLUE_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-light-blue")))));
        inventory.setItem(4,makeItem(Material.YELLOW_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-yellow")))));
        inventory.setItem(5,makeItem(Material.LIME_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-lime")))));
        inventory.setItem(6,makeItem(Material.PINK_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-pink")))));
        inventory.setItem(7,makeItem(Material.GRAY_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-gray")))));
        inventory.setItem(8,makeItem(Material.LIGHT_GRAY_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-light-gray")))));
        inventory.setItem(9,makeItem(Material.CYAN_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-cyan")))));
        inventory.setItem(10,makeItem(Material.PURPLE_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-purple")))));
        inventory.setItem(11,makeItem(Material.BLUE_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-blue")))));
        inventory.setItem(12,makeItem(Material.BROWN_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-brown")))));
        inventory.setItem(13,makeItem(Material.GREEN_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-green")))));
        inventory.setItem(14,makeItem(Material.RED_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-red")))));
        inventory.setItem(15,makeItem(Material.BLACK_CONCRETE,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "color-black")))));

        setFillerGlass(FILLER_GLASS);
    }
}
