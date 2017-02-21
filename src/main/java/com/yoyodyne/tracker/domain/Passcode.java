package com.yoyodyne.tracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Passcode {

    @NotNull
    @Length(max = 255)
    private String passcode;

    private UUID seed;

    public Passcode (String passcode, UUID seed) {
	this.passcode = passcode;
	this.seed = seed;
    }
    
    public Passcode() {} // required for deserialization

    @JsonProperty
    public String getPasscode () {
	return this.passcode;
    }

    @JsonProperty
    public void setPasscode (String passcode) {
	if (passcode != null && !passcode.isEmpty()) {
	    this.passcode = passcode;
	}
    }

    @JsonProperty
    public UUID getSeed () {
	return this.seed;
    }

    @JsonProperty
    public void setSeed (String uuidStr) {
	if (uuidStr != null && !uuidStr.isEmpty()) {
	    this.seed = UUID.fromString( uuidStr );
	}
    }

}
