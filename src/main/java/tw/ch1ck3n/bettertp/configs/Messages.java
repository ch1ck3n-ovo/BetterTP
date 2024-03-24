package tw.ch1ck3n.bettertp.configs;

import tw.ch1ck3n.bettertp.BetterTP;

import java.util.ArrayList;
import java.util.List;

public class Messages {

    public final String prefix = "§7[§aBetterTP§7]§r";

    public final String cannotExecuteInConsole;

    public final String cannotTeleportCrossWorld;

    public final String commandInCoolDown;

    public final String invalidCommand;

    public final String invalidArgument;

    public final String noCommandPermission;

    public final String anchorAdded;

    public final String anchorEdited;

    public final String anchorExisted;

    public final String anchorListTitle;

    public final String anchorListForm;

    public final String anchorNotFound;

    public final String anchorRemoved;

    public final String noAnchorFound;

    public final String playerTeleport;

    public final String worldNotFound;

    public final List<String> helpStrings;

    public final List<String> informationStrings;

    public Messages() {
        this.cannotExecuteInConsole = BetterTP.INSTANCE.getConfig().getString("message.cannot-execute-in-console").replaceAll("&", "§");
        this.cannotTeleportCrossWorld = BetterTP.INSTANCE.getConfig().getString("message.cannot-teleport-cross-world").replaceAll("&", "§");
        this.commandInCoolDown = BetterTP.INSTANCE.getConfig().getString("message.command-in-cool-down").replaceAll("&", "§");
        this.invalidCommand = BetterTP.INSTANCE.getConfig().getString("message.invalid-command").replaceAll("&", "§");
        this.invalidArgument = BetterTP.INSTANCE.getConfig().getString("message.invalid-argument").replaceAll("&", "§");
        this.noCommandPermission = BetterTP.INSTANCE.getConfig().getString("message.no-command-permission").replaceAll("&", "§");
        this.anchorAdded = BetterTP.INSTANCE.getConfig().getString("message.anchor-added").replaceAll("&", "§");
        this.anchorEdited = BetterTP.INSTANCE.getConfig().getString("message.anchor-edited").replaceAll("&", "§");
        this.anchorExisted = BetterTP.INSTANCE.getConfig().getString("message.anchor-existed").replaceAll("&", "§");
        this.anchorListTitle = BetterTP.INSTANCE.getConfig().getString("message.anchor-list-title").replaceAll("&", "§");
        this.anchorListForm = BetterTP.INSTANCE.getConfig().getString("message.anchor-item-form").replaceAll("&", "§");
        this.anchorNotFound = BetterTP.INSTANCE.getConfig().getString("message.anchor-not-found").replaceAll("&", "§");
        this.anchorRemoved = BetterTP.INSTANCE.getConfig().getString("message.anchor-removed").replaceAll("&", "§");
        this.noAnchorFound = BetterTP.INSTANCE.getConfig().getString("message.no-anchor-found").replaceAll("&", "§");
        this.playerTeleport = BetterTP.INSTANCE.getConfig().getString("message.player-teleport").replaceAll("&", "§");
        this.worldNotFound = BetterTP.INSTANCE.getConfig().getString("message.world-not-found").replaceAll("&", "§");
        List<String> help = new ArrayList<>();
        for (String s: BetterTP.INSTANCE.getConfig().getStringList("message.help")) help.add(s.replaceAll("&", "§"));
        this.helpStrings = help;
        List<String> information = new ArrayList<>();
        for (String s: BetterTP.INSTANCE.getConfig().getStringList("message.information")) information.add(s.replaceAll("&", "§"));
        this.informationStrings = information;
    }
}
