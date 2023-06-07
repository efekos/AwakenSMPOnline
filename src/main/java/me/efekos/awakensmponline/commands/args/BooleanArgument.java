package me.efekos.awakensmponline.commands.args;

import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class BooleanArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "value";
    }

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {
        ArrayList<ArgumentResult> result = new ArrayList<>();

        result.add(new ArgumentResult().setValue("true").setName("true"));
        result.add(new ArgumentResult().setName("false").setName("false"));

        return result;
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.OPTIONAL;
    }

    @Override
    public boolean handleCorrection(String given) {
        return Boolean.getBoolean(given);
    }
}
