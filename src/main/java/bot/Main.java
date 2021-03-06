package bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.io.*;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dillon Domnick (DDomnick)
 * @version Mar 6, 2019
 */
public class Main {

    /**
     * Fields
     */
    private static final String TOKEN = getToken();

    private static String getToken() {
        Scanner in = null;
        try {
            in = new Scanner(new FileReader("src/main/resources/TOKEN.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (in != null && in.hasNext()) {
            return in.next();
        }
        return null;
    }

    /**
     * Constructor
     */
    public static void main(String[] args)
    {
        new Main();
    }

    /**
     * Sets up VapeBot
     */
    private Main()
    {
        Logger logger = LoggerFactory.getLogger(Main.class);
        try
        {
            JDA vb = JDABuilder.createDefault(TOKEN)
                    .setActivity(Activity.watching("Aggretsuko"))
                    .disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
                    .setBulkDeleteSplittingEnabled(false)
                    .setLargeThreshold(50)
                    .addEventListeners(new EventListener())
                    //.enableIntents(GatewayIntent.GUILD_PRESENCES) # For later if I need it, reduces performance
                    .build().awaitReady();
            logger.info("Connected.");
        }
        catch (IllegalArgumentException e)
        {
            logger.info("No login details provided! Please provide a botToken in the config.");
        }
        catch (LoginException e)
        {
            logger.info("The botToken provided in the Config.json was incorrect.");
            logger.info("Did you modify the Config.json after it was created?");
        }
        catch (InterruptedException e)
        {
            logger.info("Our login thread was interrupted!");
        }
    }
}
