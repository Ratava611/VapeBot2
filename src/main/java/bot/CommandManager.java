package bot;

import commands.utility.Ping;
import commands.utility.ServerInfo;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Dillon Domnick. @since 9/27/2020
 *
 * User: Dillon
 * Time: 8:15 PM
 * Version: 1.0
 *
 * manages commands
 */
public class CommandManager {

    private final static Map<String, CommandInterface> cmds = new HashMap<>();

    public CommandManager()
    {
        addCommand(new Ping());
        addCommand(new ServerInfo());
        //addCommand(new Help(this));
        //addCommand(new Cat());
        //addCommand(new Dog());
        //addCommand(new Meme(random));
        //addCommand(new UserInfo());
        //addCommand(new Kick());
        //addCommand(new Ban());
        //addCommand(new Unban());
        //addCommand(new Join());
        //addCommand(new Leave());
        //addCommand(new Play());
        //addCommand(new Stop());
        //addCommand(new Purge());
        //addCommand(new Calculator());
        //addCommand(new Skip());
        //addCommand(new Queue());
    }

    private void addCommand(CommandInterface cmd) {
        if (!cmds.containsKey(cmd.getInvoke())) {
            cmds.put(cmd.getInvoke(), cmd);
        }
    }

    public void handleCommand(GuildMessageReceivedEvent event)
    {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Enums.PREFIX), "").split("\\s+");

        final String invoke = split[0].toLowerCase();

        if(cmds.containsKey(invoke))
        {
            final List<String> args = Arrays.asList(split).subList(1, split.length);
            event.getChannel().sendTyping().queue();
            cmds.get(invoke).handle(args, event);
        }
    }
}
