package me.efekos.awakensmponline.commands.args;

import me.efekos.awakensmponline.data.Friend;
import me.efekos.awakensmponline.data.PlayerData;
import me.efekos.awakensmponline.files.PlayerDataManager;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FriendArgument extends Argument {

    @Override
    public String getPlaceHolder() {
        return "player";
    }

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {
        PlayerData data = PlayerDataManager.fetch(player.getUniqueId());
        ArrayList<ArgumentResult> results = new ArrayList<>();

        for(Friend friend:data.getFriends()){
            results.add(new ArgumentResult().setValue(friend.getLastName()).setName(friend.getLastName()));
        }

        return results;
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    @Override
    public boolean handleCorrection(String given) {
        return true;
    }
}
