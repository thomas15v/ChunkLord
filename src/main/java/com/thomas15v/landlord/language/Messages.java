package com.thomas15v.landlord.language;

/**
 * Created by thomas15v on 17/05/15.
 */
public enum Messages {
    ALREADY_CLAIMED_BY("Already Claimed by %s!"),
    LAND_CLAIMED("Land claimed!"),
    LAND_UNCLAIMED("Land unclaimed!"),
    NO_CLAIM_TO_UNCLAIM("No land found to unclaim!"),
    NO_ACCES_TO_UNCLAIM("You can't unclaim %s land!"),
    NO_CLAIM_FOUND("No claim found!"),
    CLAIM_INFO("Claim at: World: %s X: %s Z: %s\n" +
            "Owner: %s\n" +
            "Trusted: %s\n" +
            "ClaimTrusted: %s"),
    HELP_MESSAGE("-=LandLord Help=-\n" +
            "/claim : Use this to claim land\n" +
            "/unclaim : Use this to unclaim land you own\n" +
            "/unclaimall : Unclaims all your plots asks for confirmation\n" +
            "/trust <playername> : Use this to add a friend globally\n" +
            "/claimtrust <player> : Use this to add a friend to a specific plot\n" +
            "/untrust <player> : Use this to remove a player from your globally friend list\n" +
            "/claimuntrust <player> : Use this to remove a player from a specific plot\n" +
            "/claiminfo : Displays information about the claim your standing in\n" +
            "/tenantinfo <player> : Displays plot information");

    private String defaultMessage;
    Messages(String defaultMessage){
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
