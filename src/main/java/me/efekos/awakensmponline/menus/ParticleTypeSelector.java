package me.efekos.awakensmponline.menus;

import me.efekos.awakensmponline.AwakenSMPOnline;
import me.efekos.awakensmponline.classes.ParticleType;
import me.efekos.awakensmponline.classes.PlayerData;
import me.efekos.awakensmponline.files.DeadPlayersJSON;
import me.efekos.awakensmponline.utils.Particles;
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

public class ParticleTypeSelector extends Menu {
    public ParticleTypeSelector(PlayerMenuUtility playerMenuUtility) {
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
        return AwakenSMPOnline.getPlugin().getConfig().getString("messages.commands.particle-menu.type-title");
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    private void eee(InventoryClickEvent e, ParticleType type){
        Player p = (Player) e.getWhoClicked();
        PlayerData data = DeadPlayersJSON.getDataFromUniqueId(p.getUniqueId());
        assert data != null;
        data.getParticleOptions().setType(type);
        DeadPlayersJSON.updateData(data.getPlayerUniqueId(),data);
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
            Player p = (Player) e.getWhoClicked();
            switch (Objects.requireNonNull(e.getCurrentItem()).getType()){
                case BARRIER:
                    p.closeInventory();
                    break;
                case TNT:
                    eee(e,ParticleType.EXPLOSION);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case SNOWBALL:
                    eee(e,ParticleType.POP);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case LINGERING_POTION:
                    eee(e,ParticleType.PUFF);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case GUNPOWDER:
                    eee(e,ParticleType.DUST);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case BEACON:
                    eee(e,ParticleType.BEAM);
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case PAPER:
                    p.closeInventory();
                    MenuManager.openMenu(ParticleMainMenu.class,p);
                    break;
                case POTION:
                    eee(e,ParticleType.POTION);
                    p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case TOTEM_OF_UNDYING:
                    eee(e,ParticleType.TOTEM);
                    p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
                    break;
                case DIRT:
                    eee(e,ParticleType.BLOCK);
                    p.playSound(p.getLocation(),Sound.ENTITY_EXPERIENCE_ORB_PICKUP,100,1);
            }
    }

    @Override
    public void setMenuItems() {
        FileConfiguration cf = AwakenSMPOnline.getPlugin().getConfig();
        String path = "messages.commands.particle-menu.";
        ItemStack close = makeItem(Material.BARRIER, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "btn-close"))));
        inventory.setItem(22,close);
        inventory.setItem(26,makeItem(Material.PAPER,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "btn-back")))));

        inventory.setItem(0,makeItem(Material.TNT, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-explosion")))));
        inventory.setItem(1,makeItem(Material.SNOWBALL, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-pop")))));
        inventory.setItem(2,makeItem(Material.LINGERING_POTION, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-puff")))));
        inventory.setItem(3,makeItem(Material.GUNPOWDER, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-dust")))));
        inventory.setItem(4,makeItem(Material.BEACON, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-beam")))));
        inventory.setItem(5,makeItem(Material.POTION, ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-potion")))));
        inventory.setItem(6,makeItem(Material.TOTEM_OF_UNDYING,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-totem")))));
        inventory.setItem(7,makeItem(Material.DIRT,ColorTranslator.translateColorCodes(Objects.requireNonNull(cf.getString(path + "type-block")))));

        setFillerGlass(FILLER_GLASS);
    }
}
