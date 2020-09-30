package bot;

//import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by Dillon Domnick. @since 9/27/2020
 *
 * User: Dillon
 * Time: 1:30 AM
 * Version: 1.0
 *
 * listens for events
 */
public class EventListener extends ListenerAdapter {

    /**
     * Fields
     */
    private final CommandManager manager = new CommandManager(); // Initializes command parser
    private final Logger logger = LoggerFactory.getLogger(EventListener.class);

    /**
     * When this is fired all available entities are cached and accessible.
     * @param event Indicates that JDA finished loading all entities.
     */
    @Override
    public void onReady(ReadyEvent event)
    {
        logger.info(String.format("Listener logged in as %#s", event.getJDA().getSelfUser()));
        super.onReady(event);
    }

    /**
     * Logs private messages
     * @param event on message received
     */
    public void onMessageReceived(MessageReceivedEvent event)
    {
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        String msg = message.getContentDisplay();       //This is the MessageChannel that the message was sent to.

        //These are provided with every event in JDA
        //JDA jda = event.getJDA();
        //long responseNumber = event.getResponseNumber();

        if (event.isFromType(ChannelType.PRIVATE) && !author.isBot()) //If this message was sent to a PrivateChannel
        {
            event.getChannel().sendMessage("This hasn't been implemented yet. But thanks for checking in!").queue();
            logger.info(String.format("[PRIV]<%s>: %s", author.getName(), msg));
        }
        else if (event.isFromType(ChannelType.TEXT))   //If this message was sent to a Group. This is CLIENT only!
        {
            TextChannel textChannel = event.getTextChannel();
            logger.info(String.format("[GRP: %s]<%s>: %s", textChannel.getName(), author, msg));
        }
    }

    /**
     * Passes prefixed commands to the command manager
     * @param event on any server channel message received event that we have permissions in
     */
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        if (!event.getAuthor().isBot() && !event.getMessage().isWebhookMessage()
                && event.getMessage().getContentRaw().startsWith(Enums.PREFIX))
        {
            manager.handleCommand(event);
        }
    }
}

