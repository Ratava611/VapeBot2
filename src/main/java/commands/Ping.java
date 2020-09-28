package commands;

import bot.CommandInterface;
import bot.Enums;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

/**
 * Created by Dillon Domnick. @since 9/27/2020
 *
 * User: Dillon
 * Time: 8:21 PM
 * Version: 1.0
 *
 * pings
 */
public class Ping implements CommandInterface {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        event.getChannel().sendMessageFormat("Ping is %sms", event.getJDA().getGatewayPing()).queue();
    }

    @Override
    public String getHelp()
    {
        return "Usage: `" + Enums.PREFIX + getInvoke() + "`";
    }

    @Override
    public String getInvoke()
    {
        return "ping";
    }
}
