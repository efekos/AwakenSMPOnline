package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.Main;
import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.data.FriendModifications;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.syntax.impl.PlayerArgument;
import me.efekos.simpler.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "modify",description = "Modify what your friends can see",permission = "awakensmp.friend.modify")
public class Modify extends SubCommand {
    public Modify(@NotNull String name) {
        super(name);
    }

    public Modify(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return Friend.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax()
                .withArgument(new PlayerArgument(ArgumentPriority.REQUIRED));
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = Main.getPlayerFromName(args[0]);
        if(data==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.not-player","&b%player% &cis not a player").replace("%player%",args[0])));
            return;
        }

        me.efekos.awakensmponline.data.Friend friend = data.getFriend(player.getName());
        if(friend==null){
            player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.not-friend","&b%player% &cis not your friend.").replace("%player%",data.getName())));
            return;
        }
        FriendModifications modifications = friend.getModifications();

        if(args.length>1){
            if(args.length!=3){
                player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.need-value","&cYou need to enter &etrue &cor &efalse &cas third argument to change a value.")));
                return;
            }

            switch (args[1]){
                case "worldAllowed":
                    modifications.setWorldAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "locationAllowed":
                    modifications.setLocationAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "compassAllowed":
                    modifications.setCompassAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "inventoryAllowed":
                    modifications.setInventoryAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "armorAllowed":
                    modifications.setArmorAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "healthAllowed":
                    modifications.setHealthAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "expAllowed":
                    modifications.setExpAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "foodAllowed":
                    modifications.setFoodAllowed(Boolean.parseBoolean(args[2]));
                    break;
                case "teleportAllowed":
                    modifications.setTeleportAllowed(
                            Boolean.parseBoolean(args[2]));
                    break;
                default:
                    player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.invalid-key","&b%key% &cis not an option.").replace("%key%",args[1])));
                    return;
            }
            friend.setModifications(modifications);
            data.updateFriend(friend.getPlayerId(),friend);
            Main.PLAYER_DATA.update(data.getUuid(),data);
        }

        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("");
        player.spigot().sendMessage(ButtonManager.generateBackButton("/awakensmponline:friend list"));
        player.sendMessage("");
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.header", "&1----------&9Modify what %player% can do&1----------").replace("%player%", args[0])));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.worldAllowed","&9See which dimension you are in: &a%value%").replace("%value%",pSBTLV(modifications.isWorldAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "worldAllowed",!modifications.isWorldAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.locationAllowed","&9See where you are: &a%value%").replace("%value%",pSBTLV(modifications.isLocationAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "locationAllowed",!modifications.isLocationAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.compassAllowed","&9Get a compass that leads to you: &a%value%").replace("%value%",pSBTLV(modifications.isCompassAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "compassAllowed",!modifications.isCompassAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.inventoryAllowed","&9See your inventory: &a%value%").replace("%value%",pSBTLV(modifications.isInventoryAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "inventoryAllowed",!modifications.isInventoryAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.armorAllowed","&9See the items in your hands and your armor: &a%value%").replace("%value%",pSBTLV(modifications.isArmorAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "armorAllowed",!modifications.isArmorAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.healthAllowed","&9See your health status: &a%value%").replace("%value%",pSBTLV(modifications.isHealthAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "healthAllowed",!modifications.isHealthAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.expAllowed","&9See your exp and level: &a%value%").replace("%value%",pSBTLV(modifications.isExpAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "expAllowed",!modifications.isExpAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.foodAllowed","&9See how much hungry you are: &a%value%").replace("%value%",pSBTLV(modifications.isFoodAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "foodAllowed",!modifications.isFoodAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.teleportAllowed","&9Teleport to your location without asking: &a%value%").replace("%value%",pSBTLV(modifications.isTeleportAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "teleportAllowed",!modifications.isTeleportAllowed()));
        player.sendMessage(TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.footer","&1------------------------------------------------")));

    }

    private String pSBTLV(String bool){
        if(bool.equals("true")) return TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.true","&a✔"));
        if(bool.equals("false")) return TranslateManager.translateColors(Main.LANG.getString("commands.friend.modify.false","&c✖"));
        return "";
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
