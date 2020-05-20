package fr.ziedelth.towers.listeners.player;

import fr.ziedelth.towers.utils.GameStates;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChange implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!GameStates.isState(GameStates.GAME)) {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
