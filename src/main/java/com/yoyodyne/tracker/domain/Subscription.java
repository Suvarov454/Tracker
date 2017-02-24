package com.yoyodyne.tracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Subscription {

    @NotNull
    private UUID subscriptionId;

    @NotNull
    private UUID playerId;

    @NotNull
    private UUID titleId;

    @NotNull
    private Date expirationDate;

    @NotNull
    private Long level;

    private String name;

    public Subscription (UUID subscriptionId, UUID playerId, UUID titleId, Date expirationDate, Long level, String name) {
        this.subscriptionId = subscriptionId;
        this.playerId = playerId;
        this.titleId = titleId;
        this.expirationDate = expirationDate;
        this.level = level;
        this.name = name;
    }
    
    public Subscription() {} // required for deserialization


    @JsonProperty
    public UUID getSubscriptionId () {
        return this.subscriptionId;
    }

    @JsonProperty
    public void setSubscriptionId (String uuidStr) {
        if (uuidStr != null && !uuidStr.isEmpty()) {
            this.subscriptionId = UUID.fromString( uuidStr );
        }
    }

    @JsonProperty
    public UUID getPlayerId () {
        return this.playerId;
    }

    @JsonProperty
    public void setPlayerId (String uuidStr) {
        if (uuidStr != null && !uuidStr.isEmpty()) {
            this.playerId = UUID.fromString( uuidStr );
        }
    }

    @JsonProperty
    public UUID getTitleId () {
        return this.titleId;
    }

    @JsonProperty
    public void setTitleId (String uuidStr) {
        if (uuidStr != null && !uuidStr.isEmpty()) {
            this.titleId = UUID.fromString( uuidStr );
        }
    }

    @JsonProperty
    public Date getExpirationDate () {
        return this.expirationDate;
    }

    @JsonProperty
    public void setExpirationDate (Date expirationDate) {
        if (expirationDate != null) {
            this.expirationDate = expirationDate;
        }
    }

    @JsonProperty
    public Long getLevel () {
        return this.level;
    }

    @JsonProperty
    public void setLevel (Long level) {
        if (level != null) {
            this.level = level;
        }
    }

    @JsonProperty
    public String getName () {
        return this.name;
    }

    @JsonProperty
    public void setName (String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

}
