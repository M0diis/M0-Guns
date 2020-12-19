package me.m0dii.guns;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

	
	@Override
 	public void onEnable() 
 	{ 
 		System.out.println("M0-Guns is now enabled!");
 		
 		this.getCommand("guns").setExecutor(new Commands());
		 
 		Cooldowns.setupcooldown();
 		
 		this.manager = Bukkit.getPluginManager();
	  
		this.manager.registerEvents(this, this);
 	}

 	public PluginManager manager;
	public static boolean knockback = true;
	public static boolean state = true;
	public static int damage = 0;
	public static double hitbox = 1.2;
	
    @Override
 	public void onDisable() 
 	{
 		this.manager.disablePlugin(this);
 		
 		System.out.println("M0-Guns is now disabled!");
 	}
    
	@EventHandler(priority=EventPriority.HIGH)
    public void Interract(PlayerInteractEvent e)
	{  
		Player player = e.getPlayer();

		Location loc = player.getLocation();
		Vector direction = loc.getDirection().normalize();
		
		ItemStack active = e.getPlayer().getInventory().getItemInMainHand();
		
		if(active.getType() == Material.DIAMOND_HOE)
		{
			String cdmsg = format("&aM0-Guns &2- &aYour next shot is ready in &c" + Cooldowns.SNIPER_COOLDOWN + " " +
					"&csec!");
			
			if (Cooldowns.checkCooldown(e.getPlayer()))
			{
				for (double distance = 0; distance <= 60; distance = distance + 1)
				{
					double x = direction.getX() * distance;
					double y = direction.getY() * distance + 1.6;
					double z = direction.getZ() * distance;
					
					loc.add(x,y,z);
					
					if (loc.getBlock().getType() != Material.AIR)
					{
						player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 2, 0, 0, 0);
						
						break;
					}
					
					for (Entity entity : loc.getChunk().getEntities())
					{
						if (entity.getLocation().distance(loc) <= hitbox && entity instanceof LivingEntity && entity.getName() != player.getName())
						{
							LivingEntity living = (LivingEntity) entity;
							living.damage(damage);
							
							
							player.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 2, 0, 0, 0);
							
							if(knockback) living.setVelocity(direction);
						}
					}
					
					Location origin = player.getEyeLocation();
					direction.multiply(15);
					direction.normalize();
					for (int i = 0; i < 20; i++)
					{
						Location loca = origin.add(direction);
						loca.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loca.getX(), loca.getY(), loca.getZ(), 0);
					}

					loc.subtract(x,y,z);
				}
				
				if(Cooldowns.SNIPER_COOLDOWN != 0) e.getPlayer().sendMessage(cdmsg);
				Cooldowns.setCooldown(e.getPlayer(), Cooldowns.SNIPER_COOLDOWN);
			}
		}
	}
	
	public String format(String text)
	{
		return ChatColor.translateAlternateColorCodes('&', text);
	}
}
