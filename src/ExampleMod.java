package intcraft.canceldetect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import intcraft.canceldetect.CancellationDetector.CancelListener;

public class ExampleMod extends JavaPlugin implements Listener {
    private CancellationDetector<BlockPlaceEvent> detector = new CancellationDetector<BlockPlaceEvent>(BlockPlaceEvent.class);
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		
		detector.addListener(new CancelListener<BlockPlaceEvent>() {
			@Override
			public void onCancelled(Plugin plugin, BlockPlaceEvent event) {
				System.out.println(event + " cancelled by " + plugin);
			}
		});
	}
	
	@Override
	public void onDisable() {
		// Incredibly important!
		detector.close();
	}
	
	// For testing
	/*
	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {
		e.setCancelled(true);
	}
	*/
}
