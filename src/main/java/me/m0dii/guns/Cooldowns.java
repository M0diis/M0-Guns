package me.m0dii.guns;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Cooldowns {
	
	private final M0Guns plugin;
	
	public Cooldowns(M0Guns plugin) {
		this.plugin = plugin;
	}
	
	private HashMap<UUID, Long> cooldowns = new HashMap<>();
	
	private long sniperCooldown = 0;
	
	public void setCooldown(Player player, long duration)
	{
		double cd = System.currentTimeMillis() + (duration * 1000);
		
		cooldowns.put(player.getUniqueId(), (long) cd);
	}
	
	public long getCooldown(Player player)
	{
		return (cooldowns.get(player.getUniqueId()) / 1000) - (System.currentTimeMillis() / 1000);
	}
	
	public boolean checkCooldown(Player player)
	{
		return !cooldowns.containsKey(player.getUniqueId())
				|| cooldowns.get(player.getUniqueId()) <= System.currentTimeMillis();
	}
	
	public HashMap<UUID, Long> getCooldowns()
	{
		return cooldowns;
	}
	
	public void setCooldowns(HashMap<UUID, Long> cds)
	{
		this.cooldowns = cds;
	}
	
	public long getSniperCooldown()
	{
		return sniperCooldown;
	}
	
	public void setSniperCooldown(long sniperCooldown)
	{
		this.sniperCooldown = sniperCooldown;
	}
}