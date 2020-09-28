package bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

/**
 * Created by Dillon Domnick. @since 9/27/2020
 *
 * User: Dillon
 * Time: 8:17 PM
 * Version: 1.0
 *
 * probably useless lol
 */
public interface CommandInterface {

    void handle(List<String> args, GuildMessageReceivedEvent event);
    String getHelp();
    String getInvoke();
}

