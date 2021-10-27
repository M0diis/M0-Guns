package me.m0dii.guns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class GunsCommand implements CommandExecutor {

    private final M0Guns plugin;
    
    public GunsCommand(M0Guns plugin)
    {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args)
    {
        if(args.length == 0)
        {
            return true;
        }
        
        String subcmd = args[0].toLowerCase();
        
        boolean state = this.plugin.getState();
    
        if(!sender.hasPermission("m0guns.admin"))
        {
    		return reply(sender, "&aM0-Guns &2- &aYou don't have permission.");
        }
        
        Player p = (Player) sender;
    
        if(subcmd.equals("toggle"))
        {
            this.plugin.setState(!state);
    
            reply(p, "&aM0-Guns &2- &aGuns are now " + (state ? "&coff." : "&aon."));
        }
    
        if(subcmd.equalsIgnoreCase("damage"))
        {
            if(args.length < 2)
            {
                return reply(p, "&cUsage: /guns damage <amount>");
            }
            
            try
            {
                this.plugin.setDamage(Integer.parseInt(args[1]));

                return reply(p, "&aM0-Guns &2- &aDamage has been set to &2" + args[1]);
            }
            catch (NumberFormatException e)
            {
                return reply(p, "&aM0-Guns &2- &aDamage has to be a number!&a");
            }
        }
    
        if(subcmd.equals("cooldown"))
        {
            if(args.length < 2)
            {
                return reply(p, "&cUsage: /guns cooldown <seconds>");
            }
            
            try
            {
                this.plugin.getCooldowns().setSniperCooldown(Long.parseLong(args[1]));
    
                return reply(p, "&aM0-Guns &2- &aCooldown has been set to &2" + args[1]);
            }
            catch (NumberFormatException e)
            {
                return reply(p, "&aM0-Guns &2- &aCooldown has to be a number!&a");
            }
        }
    
        if(subcmd.equals("hitbox"))
        {
            if(args.length < 2)
            {
                return reply(p, "&cUsage: /guns hitbox <offset>");
            }
            
            try
            {
                this.plugin.setHitbox(Double.parseDouble(args[1]));
                
                return reply(p, "&aM0-Guns &2- &aKnockback has been set to &2" + args[1]);
            }
            catch (NumberFormatException e)
            {
                return reply(p, "&aM0-Guns &2- &aCooldown has to be a number!&a");
            }
        }
    
        if(subcmd.equals("knockback"))
        {
            reply(p, "&aM0-Guns &2- &aGuns are now " + (this.plugin.knockbackEnabled()
                    ? "&coff." : "&aon."));
    
            this.plugin.setKnockback(!this.plugin.knockbackEnabled());
        }
    
        return true;
    }
    
    private boolean reply(CommandSender sender, String message)
    {
        sender.sendMessage(format(message));
        
        return true;
    }
    
    private String format(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
