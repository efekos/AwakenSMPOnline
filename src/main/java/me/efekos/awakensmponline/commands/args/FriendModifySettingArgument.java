package me.efekos.awakensmponline.commands.args;

import me.efekos.simpler.Utils;
import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendModifySettingArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "setting";
    }

    private static final ArrayList<String> avaliable = Utils.fromStreamToArrayList(Arrays.stream(new String[]{"worldAllowed","locationAllowed","compassAllowed","inventoryAllowed","armorAllowed","healthAllowed","expAllowed","foodAllowed","teleportAllowed"}));

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {

        return Utils.fromStreamToArrayList(avaliable.stream().map(s -> new ArgumentResult().setName(s).setValue(s)));
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.OPTIONAL;
    }

    @Override
    public boolean handleCorrection(String given) {
        return avaliable.contains(given);
    }
}
