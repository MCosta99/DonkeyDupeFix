package me.mcosta.donkeydupefix;

import org.bukkit.entity.Player;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Cattivoni {

    private DonkeyDupeFix plugin;
    private HashMap<Player, EntityMountEvent> plist;

    public Cattivoni(DonkeyDupeFix p){
        this.plugin=p;
        this.plist = new HashMap<>();
    }

    public void add(Player player, EntityMountEvent event)
    {
        this.plist.put(player,event);
    }
    public void remove(Player player)
    {
        this.plist.remove(player);
    }
    public EntityMountEvent get(Player player)
    {
        return  this.plist.get(player);
    }
    public boolean contains(Player player){
        return this.plist.containsKey(player);
    }

    public ArrayList<String> getPlayers(){
        ArrayList<String> output = new ArrayList<>();
        for (Player p : plist.keySet()){
            output.add(p.getName().toString());
        }
        return output;
    }

}
