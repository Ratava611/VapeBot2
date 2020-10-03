package commands.utility;

import bot.CommandInterface;
import bot.Enums;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dillon Domnick. @since 10/1/2020
 *
 * User: Dillon
 * Time: 11:21 PM
 * Version: 1.0
 *
 * User info
 */
public class UserInfo implements CommandInterface {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessage(
                    "Missing arguements, check `" + Enums.PREFIX + "help " + getInvoke() + "`").queue();
            return;
        }

        String joined = String.join("", args);

        //TODO there's no way importing an entire repository for one thing is efficient here
        List<User> users = FinderUtil.findUsers(joined, event.getJDA());
        System.out.println(users.get(0));

        if (users.isEmpty())
        {
            List<Member> members = FinderUtil.findMembers(joined, event.getGuild());
            if (members.isEmpty())
            {
                event.getChannel().sendMessage("No users found for `" + joined + "'").queue();
                return;
            }

            users = members.stream().map(Member::getUser).collect(Collectors.toList());
        }

        User user = users.get(0);
        Member member = event.getGuild().getMember(user);

        MessageEmbed embed = new EmbedBuilder()
                .setColor(member.getColor())
                .setThumbnail(user.getEffectiveAvatarUrl().replaceFirst(".gif", ".png"))
                .addField("Username", String.format("%#s", user), false)
                .addField("Display name", member.getEffectiveName(), false)
                .addField("User Id + Mention", String.format("%s {%s}", user.getId(), member.getAsMention()), false)
                .addField("Account Created", user.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
                .addField("Joined Server", member.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME), false)
                .addField("Connection Status", member.getOnlineStatus().name().replaceAll("_", " "), false)
                .addField("Bot Status", user.isBot() ? "Yes" : "No", false)
                .build();

        event.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String getHelp() {
        return "Displays information about the specified user.\n" +
                "Usage: `" + Enums.PREFIX + getInvoke() + " [username/@user/user id]`";
    }

    @Override
    public String getInvoke() {
        return "userinfo";
    }

}
