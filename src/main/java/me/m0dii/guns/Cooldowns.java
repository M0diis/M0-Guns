package me.m0dii.guns;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Cooldowns {
	
	public static HashMap<UUID, Long> COOLDOWNS;
	
	public static long SNIPER_COOLDOWN = 0;
	
	public static void setupcooldown()
	{
		COOLDOWNS = new HashMap<>();
	}
	
	public static void setCooldown(Player player, long duration)
	{
		double cd = System.currentTimeMillis() + (duration * 1000);
		
		COOLDOWNS.put(player.getUniqueId(), (long) cd);
	}
	
	public static long getCooldown(Player player, HashMap<UUID, Long> map)
	{
		return (map.get(player.getUniqueId()) / 1000) - (System.currentTimeMillis() / 1000);
	}
	
	public static boolean checkCooldown(Player player)
	{
		return !COOLDOWNS.containsKey(player.getUniqueId()) || COOLDOWNS.get(player.getUniqueId()) <= System.currentTimeMillis();
	}
}