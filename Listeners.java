package me.mcosta.donkeydupefix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Listeners implements Listener {

    DonkeyDupeFix plugin;
    ArrayList<String> cattivoniPortale = new ArrayList<>();

    public Listeners(DonkeyDupeFix p) {
        this.plugin=p;
    }

    public ArrayList<String> getCattivoniPortale(){
        return cattivoniPortale;
    }

    @EventHandler
    public void onMount(EntityMountEvent e) {
        if (e.getMount() instanceof ChestedHorse && e.getEntity() instanceof Player) {
            this.plugin.listaCattivoni.add((Player) e.getEntity(), e);
            //Bukkit.getLogger().warning("Aggiunto dalla lista dei cattivi:" + ((Player) e.getEntity()).getName().toString());
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if (e.getEntity() instanceof Player && this.plugin.listaCattivoni.contains((Player)e.getEntity())){
            this.plugin.listaCattivoni.remove((Player)e.getEntity());
            //Bukkit.getLogger().warning("Eliminato dalla lista dei cattivi:" + ((Player)e.getEntity()).getName().toString());
        }

    }

    @EventHandler
    public void onPortalEnter(EntityPortalEnterEvent e) {
        if (e.getEntity() instanceof ChestedHorse || e.getEntity() instanceof Llama) {
            e.getEntity().setFallDistance(0.0F);

            for (Entity forsePlayer : e.getEntity().getNearbyEntities(e.getEntity().getLocation().getX(),e.getEntity().getLocation().getY(),e.getEntity().getLocation().getZ())){
                if (forsePlayer instanceof Player)
                {
                    if (!(cattivoniPortale.contains(((Player)forsePlayer).getName().toString()))){
                        Bukkit.getLogger().warning(((Player)forsePlayer).getName().toString() +" ha mandato un animale con chest attraverso un portale!");
                        NotificoOnlineAdmins(((Player)forsePlayer));
                        cattivoniPortale.add(((Player)forsePlayer).getName().toString());

                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (this.plugin.listaCattivoni.contains(e.getPlayer())) {
            ArrayList<String> involved = new ArrayList<>();
            Player aiuto = null;
            for (HumanEntity player : ((ChestedHorse)this.plugin.listaCattivoni.get(e.getPlayer()).getMount()).getInventory().getViewers())
            {
                involved.add(player.getName());
                if (player instanceof Player)
                {
                    aiuto=(Player)player;
                }

            }
            if (involved.size() != 0) {
                StringBuilder participants = new StringBuilder(e.getPlayer().getName());
                for (String party : involved)
                    participants.append(" e " + party);
                Bukkit.getLogger().warning(" ==> DUPING CON DONKEYDUPE FATTO DA: " + participants.toString());

                NotificoOnlineAdmins(participants);
                aiuto.closeInventory();
            }

        }
    }

    private void NotificoOnlineAdmins(Object players){

        for(Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.hasPermission("mc.donkey.notify")) {
                if (players instanceof StringBuilder){
                    p.sendMessage(ChatColor.RED+ "[ALERT] => "+ ((StringBuilder)players).toString() + " STANNO DUPLICANDO OGGETTI!");
                }
                if (players instanceof Player){
                    p.sendMessage(ChatColor.RED+ "[ALERT] => "+ ((Player) players).getName() + "STA DUPLICANDO OGGETTI!");
                }
            }
        }

    }



}
