package me.m0dii.guns;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class GunListener implements Listener
{
    private final M0Guns plugin;
    private final Cooldowns cds;
    
    public GunListener(M0Guns plugin)
    {
        this.plugin = plugin;
        this.cds = plugin.getCooldowns();
    }
    
    @EventHandler(priority= EventPriority.HIGH)
    public void Interract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        
        Location loc = p.getLocation();
        Vector direction = loc.getDirection().normalize();
        
        ItemStack active = e.getPlayer().getInventory().getItemInMainHand();
        
        if(active.getType().equals(Material.NETHERITE_HOE) &&
                p.hasPermission("m0guns.use"))
        {
            String cdmsg = format("&aM0-Guns &2- &aYour next shot is ready in &c"
                    + cds.getSniperCooldown() + " sec!");
            
            if (cds.checkCooldown(e.getPlayer()))
            {
                for (double distance = 0; distance <= 60; distance = distance + 1)
                {
                    double x = direction.getX() * distance;
                    double y = direction.getY() * distance + 1.6;
                    double z = direction.getZ() * distance;
                    
                    loc.add(x,y,z);
                    
                    if (loc.getBlock().getType() != Material.AIR)
                    {
                        p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 2, 0, 0, 0);
                        
                        break;
                    }
                    
                    for (Entity entity : loc.getChunk().getEntities())
                    {
                        if (entity.getLocation().distance(loc) <= plugin.getHitbox() &&
                                entity instanceof LivingEntity && !entity.getName().equals(p.getName()))
                        {
                            LivingEntity living = (LivingEntity) entity;
                            living.damage(plugin.getDamage());
                            
                            
                            p.getWorld().spawnParticle(Particle.SMOKE_LARGE,
                                    loc, 2, 0, 0, 0);
                            
                            if(plugin.knockbackEnabled()) living.setVelocity(direction);
                        }
                    }
                    
                    Location origin = p.getEyeLocation();
                    
                    direction.multiply(15);
                    direction.normalize();
                    
                    for (int i = 0; i < 20; i++)
                    {
                        Location loca = origin.add(direction);
                        loca.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,
                                loca.getX(), loca.getY(), loca.getZ(), 0);
                    }
                    
                    loc.subtract(x,y,z);
                }
                
                if(cds.getSniperCooldown() != 0) e.getPlayer().sendMessage(cdmsg);
                
                cds.setCooldown(e.getPlayer(), cds.getSniperCooldown());
            }
        }
    }
    
    private String format(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
