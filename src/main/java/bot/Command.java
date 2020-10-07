package bot;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Created by Dillon Domnick. @since 10/7/2020
 *
 * User: Dillon
 * Time: 6:51 PM
 * Version: 1.0
 *
 * extends thing
 */
public abstract class Command {

    /**
     * Fields
     */
    private Permission permissionLevel = null; // TODO allow multiple permissions

    /**
     * Methods
     */
    /**
    public boolean compareTo(Member author, TextChannel channel) {
        return permissionLevel == null || author.getPermissions(channel).contains(permissionLevel);
    }
     **/

    public Permission getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Permission permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

}
