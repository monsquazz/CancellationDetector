package intcraft.canceldetect;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import intcraft.canceldetect.CancellationDetector.CancelListener;

public class PvPCancelDetector extends JavaPlugin implements Listener
{
    private CancellationDetector<EntityDamageByEntityEvent> detector = new CancellationDetector<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class);
	
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(this, this);
		
		detector.addListener(new CancelListener<EntityDamageByEntityEvent>()
		{
			@Override
			public void onCancelled(Plugin plugin, EntityDamageByEntityEvent event)
			{
			
    			String damagee = event.getEntity().toString();
    			String damager = event.getDamager().toString();
			
    			if (event.getEntity() instanceof Player)
                    damagee = ((Player)event.getEntity()).getName();
                if (event.getDamager() instanceof Player)
                    damager = ((Player)event.getDamager()).getName();

                // log it in console
                System.out.println("[CANCELDETECT] Player " + damagee + " is immune to " + damager + "s " + event.getCause() + ", cancelled by " + plugin);
        		
        		// message OPs ingame
        		for (Player p : Bukkit.getOnlinePlayers())
                    if(p.isOp())
                        p.sendMessage(ChatColor.DARK_RED + "[CANCELDETECT] " + ChatColor.RED + damagee + ChatColor.WHITE + " is immune to " + ChatColor.RED + damager + ChatColor.WHITE + "s " + event.getCause() + " cancelled by " + ChatColor.LIGHT_PURPLE + plugin);
			}
		});
	}
	
	@Override
	public void onDisable()
	{
		// Incredibly important!
		detector.close();
	}
	
	// For testing
	/*@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e)
	{
		e.setCancelled(true);
	}
	*/
}
