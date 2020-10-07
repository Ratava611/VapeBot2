package bot;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
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

    private final static Map<String, CommandInterface> cmds = new HashMap<String, CommandInterface>();
    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public CommandManager() {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(""))
                .setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner())
                .filterInputsBy(new FilterBuilder().includePackage("commands.")));
        Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

        for (Class<?> command : allClasses) {
            try {
                addCommand((CommandInterface) command.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                logger.info(e.getMessage());
                logger.info("Is " + command.getName() + " fully implemented?");
            }
        }
    }

    private void addCommand(CommandInterface cmd) {
        if (!cmds.containsKey(cmd.getInvoke())) {
            cmds.put(cmd.getInvoke(), cmd);
            logger.info("Added command: " + cmd.getClass().getName());
        }
    }

    public void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Enums.PREFIX), "").split("\\s+");

        final String invoke = split[0].toLowerCase();

        if (cmds.containsKey(invoke))
        {

            CommandInterface command = cmds.get(invoke);
            if (this.compareTo(event.getMember(), event.getChannel(), command.getPermissionLevel()))
            {
                final List<String> args = Arrays.asList(split).subList(1, split.length);
                event.getChannel().sendTyping().queue();
                cmds.get(invoke).handle(args, event);
            }
            else
            {
                event.getChannel().sendMessage("You don't have required permission: " +
                                command.getPermissionLevel().getName()).queue();
            }
        }
    }
    public boolean compareTo(Member author, TextChannel channel, Permission permissionLevel) {
        return permissionLevel == null || author.getPermissions(channel).contains(permissionLevel);
    }
}
