package me.efekos.awakensmponline.commands.friend;

import me.efekos.awakensmponline.commands.Friend;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.FriendModifications;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.awakensmponline.utils.ButtonManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.PlayerArgument;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Command(name = "modify",description = "Modify what your friends can see",permission = "awakensmp.command.friend.modify")
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
                .withArgument(new PlayerArgument());
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        PlayerData data = PlayerDataManager.get(args[0]);
        if(data==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.not-player").replace("%player%",args[0])));
            return;
        }

        me.efekos.awakensmponline.data.Friend friend = data.getFriend(player.getName());
        if(friend==null){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.not-friend").replace("%player%",data.getName())));
            return;
        }
        FriendModifications modifications = friend.getModifications();

        if(args.length>1){
            if(args.length!=3){
                player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.need-value")));
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
                    player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.invalid-key").replace("%key%",args[1])));
                    return;
            }
            friend.setModifications(modifications);
            data.updateFriend(friend.getPlayerId(),friend);
            PlayerDataManager.update(data.getUuid(),data);
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
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.header").replace("%player%", args[0])));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.worldAllowed").replace("%value%",pSBTLV(modifications.isWorldAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "worldAllowed",!modifications.isWorldAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.locationAllowed").replace("%value%",pSBTLV(modifications.isLocationAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "locationAllowed",!modifications.isLocationAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.compassAllowed").replace("%value%",pSBTLV(modifications.isCompassAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "compassAllowed",!modifications.isCompassAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.inventoryAllowed").replace("%value%",pSBTLV(modifications.isInventoryAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "inventoryAllowed",!modifications.isInventoryAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.armorAllowed").replace("%value%",pSBTLV(modifications.isArmorAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "armorAllowed",!modifications.isArmorAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.healthAllowed").replace("%value%",pSBTLV(modifications.isHealthAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "healthAllowed",!modifications.isHealthAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.expAllowed").replace("%value%",pSBTLV(modifications.isExpAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "expAllowed",!modifications.isExpAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.foodAllowed").replace("%value%",pSBTLV(modifications.isFoodAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "foodAllowed",!modifications.isFoodAllowed()));
        player.spigot().sendMessage(new TextComponent(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.teleportAllowed").replace("%value%",pSBTLV(modifications.isTeleportAllowed()+"")))),new TextComponent(" "), ButtonManager.generateModifyToggleButton(args[0], "teleportAllowed",!modifications.isTeleportAllowed()));
        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.friend.modify.footer")));

    }

    private String pSBTLV(String bool){
        if(bool.equals("true")) return TranslateManager.translateColors(LangConfig.get("commands.friend.modify.true"));
        if(bool.equals("false")) return TranslateManager.translateColors(LangConfig.get("commands.friend.modify.false"));
        return "";
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {

    }
}
