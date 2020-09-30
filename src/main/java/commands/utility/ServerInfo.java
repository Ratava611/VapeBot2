package commands.utility;

import bot.CommandInterface;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Dillon Domnick. @since 9/30/2020
 *
 * User: Dillon
 * Time: 12:51 PM
 * Version: 1.0
 *
 * displays most recent information about the current guild
 */
public class ServerInfo implements CommandInterface {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        Guild guild = event.getGuild();

        String genInfo = String.format(
                "**Owner**: <@%s>\n"
                        + "**Region**: %s\n"
                        + "**Creation Date**: %s\n"
                        + "**Verification Level**: %s",
                guild.getOwnerId(),
                guild.getRegion().getName(),
                guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME),
                guild.getVerificationLevel().name().toLowerCase().replaceAll("_", " "));

        String memInfo = String.format(
                "**Total # of roles**: %s\n"
                        + "**Total # of members**: %s\n"
                        + "**Members online**: %s\n"
                        + "**Members offline**: %s\n"
                        + "**# of bot members**: %s",
                guild.getRoleCache().size(),
                guild.getMemberCache().size(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.ONLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.OFFLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getUser().isBot()).count());

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Server info for " + guild.getName())
                .setThumbnail(guild.getIconUrl())
                .addField("General info", genInfo, false)
                .addField("Member info", memInfo, false);

        event.getChannel().sendMessage(embed.build()).queue();

    }

    /* (non-Javadoc)
     * @see commands.CommandInterface#getHelp()
     */
    @Override
    public String getHelp() {
        return "Shows information for this server";
    }

    /* (non-Javadoc)
     * @see commands.CommandInterface#getInvoke()
     */
    @Override
    public String getInvoke() {
        return "serverinfo";
    }
}
