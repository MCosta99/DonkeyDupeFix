package me.mcosta.donkeydupefix;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class DonkeyDupeFix extends JavaPlugin {

    protected Cattivoni listaCattivoni;


    public DonkeyDupeFix(){
        this.listaCattivoni = new Cattivoni(this);
    }

    @Override
    public void onEnable() {
        getLogger().info("DonkeyDupeFix Plugin started ...");

        Bukkit.getPluginManager().registerEvents(new Listeners(this), this);
    }

    @Override
    public void onDisable() {
        getLogger().warning("----------------------------------------------");
        getLogger().warning("UTENTI CHE HANNO DUPLICATO IN QUESTA SESSIONE");
        for (String p : listaCattivoni.getPlayers()){
            getLogger().warning(p);
        }
        getLogger().warning("----------------------------------------------");
    }
}
