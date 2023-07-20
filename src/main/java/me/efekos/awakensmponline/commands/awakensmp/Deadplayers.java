package me.efekos.awakensmponline.commands.awakensmp;

import me.efekos.awakensmponline.commands.AwakenSMP;
import me.efekos.awakensmponline.config.LangConfig;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.annotations.Command;
import me.efekos.simpler.commands.CoreCommand;
import me.efekos.simpler.commands.SubCommand;
import me.efekos.simpler.commands.syntax.Syntax;
import me.efekos.simpler.commands.translation.TranslateManager;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Command(name = "deadplayers",description = "A list of dead players.",permission = "awakensmp.cmd.deadplayers")
public class Deadplayers extends SubCommand {
    public Deadplayers(@NotNull String name) {
        super(name);
    }

    public Deadplayers(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    @Override
    public Class<? extends CoreCommand> getParent() {
        return AwakenSMP.class;
    }

    @Override
    public @NotNull Syntax getSyntax() {
        return new Syntax();
    }

    @Override
    public void onPlayerUse(Player player, String[] args) {
        ArrayList<PlayerData> all = PlayerDataManager.getAll();
        ArrayList<String> names = new ArrayList<>();
        for(PlayerData data:all){
            if(!data.isAlive()){
                names.add(data.getName());
            }
        }

        player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.header")));
        if(names.size()==0){
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.no-one")));
        } else
        names.forEach(name->{
            player.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.format")
                    .replace("%player%",name)
            ));
        });
    }

    @Override
    public void onConsoleUse(ConsoleCommandSender sender, String[] args) {
        ArrayList<PlayerData> all = PlayerDataManager.getAll();
        ArrayList<String> names = new ArrayList<>();
        for(PlayerData data:all){
            if(!data.isAlive()){
                names.add(data.getName());
            }
        }

        sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.header")));
        if(names.size()==0){
            sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.no-one")));
        } else
            names.forEach(name->{
                sender.sendMessage(TranslateManager.translateColors(LangConfig.get("commands.awakensmp.deadplayers.format")
                        .replace("%player%",name)
                ));
            });
    }
}
