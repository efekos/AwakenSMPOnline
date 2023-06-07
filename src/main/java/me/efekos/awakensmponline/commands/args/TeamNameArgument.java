package me.efekos.awakensmponline.commands.args;

import me.efekos.simpler.commands.syntax.Argument;
import me.efekos.simpler.commands.syntax.ArgumentPriority;
import me.efekos.simpler.commands.syntax.ArgumentResult;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamNameArgument extends Argument {
    @Override
    public String getPlaceHolder() {
        return "name";
    }

    @Override
    public ArrayList<ArgumentResult> getList(Player player, String current) {
        return new ArrayList<>();
    }

    @Override
    public ArgumentPriority getPriority() {
        return ArgumentPriority.REQUIRED;
    }

    private final List<String> chars = Arrays.asList("q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m","Q","W","E","R","T","Y","U","I","O","P","A","S","D","D","F","G","H","J","K","L","Z","X","C","V","B","N","M","_");

    @Override
    public boolean handleCorrection(String given) {


        return given.length()>=2&&
                given.length()<=32&&
                Arrays.stream(given.split("")).allMatch(chars::contains);
    }
}
