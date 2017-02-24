package com.yoyodyne.tracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Payment {

    @NotNull
    private UUID paymentId;

    @NotNull
    private UUID playerId;

    @NotNull
    private UUID titleId;

    @NotNull
    private Date timestamp;

    @NotNull
    private Long extensionDuration;

    @NotNull
    private Date expirationBasis;

    @NotNull
    private Date expirationDate;

    @NotNull
    private BigDecimal amount;

    @NotNull
    @Length(max = 8)
    private String currency;

    @NotNull
    @Length(max = 8)
    private String type;

    @NotNull
    @Length(max = 255)
    private String token;

    public Payment (UUID paymentId,
                    UUID playerId,
                    UUID titleId,
                    Date timestamp,
                    long extensionDuration,
                    Date expirationBasis,
                    Date expirationDate,
                    BigDecimal amount,
                    String currency,
                    String type,
                    String token) {
        this.paymentId = paymentId;
        this.playerId = playerId;
        this.titleId = titleId;
        this.timestamp = timestamp;
        this.extensionDuration = extensionDuration;
        this.expirationBasis = expirationBasis;
        this.expirationDate = expirationDate;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.token = token;
    }
    
    public Payment() {} // required for deserialization


    @JsonProperty
    public UUID getPaymentId () {
        return this.paymentId;
    }

    @JsonProperty
    public void setPaymentId (String uuidStr) {
        if (uuidStr != null && !uuidStr.isEmpty()) {
            this.paymentId = UUID.fromString( uuidStr );
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
    public Date getTimestamp () {
        return this.timestamp;
    }

    @JsonProperty
    public void setTimestamp (Date timestamp) {
        if (timestamp != null) {
            this.timestamp = timestamp;
        }
    }

    @JsonProperty
    public Long getExtensionDuration () {
        return this.extensionDuration;
    }

    @JsonProperty
    public void setExtensionDuration (Long extensionDuration) {
        if (extensionDuration != null) {
            this.extensionDuration = extensionDuration;
        }
    }

    @JsonProperty
    public Date getExpirationBasis () {
        return this.expirationBasis;
    }

    @JsonProperty
    public void setExpirationBasis (Date expirationBasis) {
        if (expirationBasis != null) {
            this.expirationBasis = expirationBasis;
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
    public BigDecimal getAmount () {
        return this.amount;
    }

    @JsonProperty
    public void setAmount (BigDecimal amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    @JsonProperty
    public String getCurrency () {
        return this.currency;
    }

    @JsonProperty
    public void setCurrency (String currency) {
        if (currency != null && !currency.isEmpty()) {
            this.currency = currency;
        }
    }

    @JsonProperty
    public String getType () {
        return this.type;
    }

    @JsonProperty
    public void setType (String type) {
        if (type != null && !type.isEmpty()) {
            this.type = type;
        }
    }

    @JsonProperty
    public String getToken () {
        return this.token;
    }

    @JsonProperty
    public void setToken (String token) {
        if (token != null && !token.isEmpty()) {
            this.token = token;
        }
    }

}
