package me.efekos.awakensmponline.menu;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.data.ParticleOptions;
import me.efekos.awakensmponline.data.ParticleType;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.simpler.menu.Menu;
import me.efekos.simpler.menu.MenuData;
import me.efekos.simpler.translation.TranslateManager;
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

public class ParticleTypeMenu extends Menu {
    public ParticleTypeMenu(MenuData data) {
        super(data);
    }

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
        return TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.title", "Choose a Particle Type"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        PlayerData data = Main.fetchPlayer(p.getUniqueId());
        ParticleOptions options = data.getParticleOptions();


        switch (event.getCurrentItem().getType()) {
            case PAPER:
                back();
                break;
            case TOTEM_OF_UNDYING:
            case TNT:
            case BEACON:
            case BONE_MEAL:
            case SNOWBALL:
            case DIRT:
                options.setType(translateMaterial(event.getCurrentItem().getType()));
                data.setParticleOptions(options);
                Main.PLAYER_DATA.update(data.getUuid(), data);

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

    private ParticleType translateMaterial(Material material) {
        if (material == Material.TOTEM_OF_UNDYING) return ParticleType.TOTEM;
        if (material == Material.TNT) return ParticleType.EXPLOSION;
        if (material == Material.BEACON) return ParticleType.BEAM;
        if (material == Material.BONE_MEAL) return ParticleType.FOG;
        if (material == Material.SNOWBALL) return ParticleType.POP;
        if (material == Material.DIRT) return ParticleType.BLOCK;
        return ParticleType.POP;
    }

    @Override
    public void fill() {
        inventory.setItem(0, createItem(Material.TOTEM_OF_UNDYING, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.totem", "&eTotem"))));
        inventory.setItem(1, createItem(Material.TNT, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.explosion", "&eExplosion"))));
        inventory.setItem(2, createItem(Material.BEACON, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.beam", "&eBeam"))));
        inventory.setItem(3, createItem(Material.BONE_MEAL, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.fog", "&eFog"))));
        inventory.setItem(4, createItem(Material.SNOWBALL, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.snowball", "&eSnowball"))));
        inventory.setItem(5, createItem(Material.DIRT, TranslateManager.translateColors(Main.LANG.getString("menus.options_particle_type.types.block", "&eBlock"))));

        PlayerData data = Main.fetchPlayer(owner.getUniqueId());
        ParticleOptions options = data.getParticleOptions();

        for (int i = 0; i < 6; i++) {
            ItemStack item = inventory.getItem(i);
            ItemMeta meta = item.getItemMeta();

            boolean isThis = options.getType() == translateMaterial(item.getType());

            meta.setLore(Arrays.asList(" ", TranslateManager.translateColors(isThis ? Main.LANG.getString("menus.options.selected", "&6You currently selected this") : Main.LANG.getString("menus.options.unselected", "&aClick to select this"))));
            if (isThis) meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }

        inventory.setItem(26, createItem(Material.PAPER, TranslateManager.translateColors(Main.LANG.getString("menus.buttons.back", "&eBack"))));

        fillEmptyWith(createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
    }
}
