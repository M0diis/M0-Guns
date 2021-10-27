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

public class M0Guns extends JavaPlugin implements Listener
{
	private PluginManager pm;
	
	@Override
 	public void onEnable() 
 	{
 		this.getCommand("guns").setExecutor(new GunsCommand(this));
		 
 		this.pm = Bukkit.getPluginManager();
		this.pm.registerEvents(new GunListener(this), this);
	 
		this.cds = new Cooldowns(this);
		
		getLogger().info("M0-Guns is now enabled!");
 	}
	 
	private boolean knockback = true;
	private boolean state = true;
	private int damage = 0;
	private double hitbox = 1.2;
	private Cooldowns cds;
	
	public Cooldowns getCooldowns()
	{
		return this.cds;
	}
	
	public boolean getState()
	{
		return this.state;
	}
	
	public void setState(boolean state)
	{
		this.state = state;
	}
	
	public int getDamage()
	{
		return this.damage;
	}
	
	public void setDamage(int damage)
	{
		this.damage = damage;
	}
	
	public boolean knockbackEnabled()
	{
		return this.knockback;
	}
	
	public void setKnockback(boolean knockback)
	{
		this.knockback = knockback;
	}
	
	public double getHitbox()
	{
		return hitbox;
	}
	
	public void setHitbox(double hitbox)
	{
		this.hitbox = hitbox;
	}
	
	@Override
 	public void onDisable() 
 	{
 		this.pm.disablePlugin(this);
	  
		getLogger().info("M0-Guns is now disabled!");
 	}
}
