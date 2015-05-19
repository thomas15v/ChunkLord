package com.thomas15v.chunklord.language;

/**
 * Created by thomas15v on 17/05/15.
 */
public enum Messages {
    ALREADY_CLAIMED_BY("Already Claimed by %s!", true),
    LAND_CLAIMED("Land claimed!", true),
    LAND_UNCLAIMED("Land unclaimed!", true),
    NO_CLAIM_TO_UNCLAIM("No land found to unclaim!", true),
    NO_ACCES_TO_UNCLAIM("You can't unclaim %s land!", true),
    NO_CLAIM_FOUND("No claim found!", true),
    CLAIM_INFO("Claim at: World: %s X: %s Z: %s\n" +
            "Owner: %s\n" +
            "Trusted: %s\n" +
            "ClaimTrusted: %s\n", true),
    HELP_MESSAGE("-=LandLord Help=-\n" +
            "/claim : Use this to claim land\n" +
            "/unclaim : Use this to unclaim land you own\n" +
            "/unclaimall : Unclaims all your claims asks for confirmation\n" +
            "/trust <playername> : Use this to add a friend globally\n" +
            "/claimtrust <player> : Use this to add a friend to a specific claim\n" +
            "/untrust <player> : Use this to remove a player from your globally friend list\n" +
            "/claimuntrust <player> : Use this to remove a player from a specific claim\n" +
            "/claiminfo : Displays information about the claim your standing in\n" +
            "/tenantinfo <player> : Displays claim information", true),
    NO_ACCESS_TO_CLAIM("You don't have access to %s claim!\nIf this is your friend claim ask him to do /trust %s", false),
    UNAUTHORIZED_ACCESS_TO_CLAIM("%s tried to access your claim\nIf this is your friend you can do /trust %s to allow it", false);

    private String defaultMessage;
    private boolean repeat;

    Messages(String defaultMessage, boolean repeat){
        this.defaultMessage = defaultMessage;
        this.repeat = repeat;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
