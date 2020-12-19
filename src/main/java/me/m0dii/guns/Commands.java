package me.m0dii.guns;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args)
    {
        if(args.length == 0)
        {
            return true;
        }
        
        String arg0 = args[0].toLowerCase();
        
        boolean state = Main.state;
    	   
        if (sender instanceof Player)
        {
            Player p = (Player) sender;

            if(arg0.equalsIgnoreCase("toggle"))
            {
                if(state)
                {
                	Main.state = false;
                	
                	p.sendMessage(format("&aM0-Guns &2- &aGuns are now &2off."));
                }
                else 
                { 
                	Main.state = true;
                	
                	p.sendMessage(format("&aM0-Guns &2- &aGuns are now &2on."));
                }
            }
            
            if(arg0.equalsIgnoreCase("damage"))
            {
            	try 
            	{
            		Main.damage = Integer.parseInt(args[1]);
            		
            		p.sendMessage(format("&aM0-Guns &2- &aDamage has been set to &2") + args[1]);
            	}
            	catch (NumberFormatException e) 
            	{
            		p.sendMessage(format("&aM0-Guns &2- &aDamage has to be a number!&a"));
            	}
            }
            
            if(arg0.equalsIgnoreCase("cooldown"))
            {
            	try 
            	{
            		Cooldowns.SNIPER_COOLDOWN = Long.parseLong(args[1]);
            		
            		p.sendMessage(format("&aM0-Guns &2- &aCooldown has been set to &2") + args[1]);
            	}
            	catch (NumberFormatException e) 
            	{
            		p.sendMessage(format("&aM0-Guns &2- &aCooldown has to be a number!&a"));
            	}
            }
            
            if(arg0.equalsIgnoreCase("hitbox"))
            {
            	try 
            	{
            		Main.hitbox = Double.parseDouble(args[1]);
            		
            		p.sendMessage(format("&aM0-Guns &2- &aKnockback has been set to &2") + args[1]);
            	}
            	catch (NumberFormatException e) 
            	{
            		p.sendMessage(format("&aM0-Guns &2- &aCooldown has to be a number!&a"));
            	}
            }
            
            if(arg0.equalsIgnoreCase("knockback"))
            {
                if(Main.knockback)
                {
                	Main.knockback = false;
                	
                	p.sendMessage(
                	        format("&aM0-Guns &2- &aKnockback is now &2off!"));
                }
                else 
                { 
                	Main.knockback = true;
                	p.sendMessage( format("&aM0-Guns &2- &aKnockback is now &2on!"));
                }
            }
        }
        else 
        {
    		sender.sendMessage(
    		        format("&aM0-Guns &2- &aYou dont have access to the commands!"));

        }
    	
        return true;
    }
    
    public String format(String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
