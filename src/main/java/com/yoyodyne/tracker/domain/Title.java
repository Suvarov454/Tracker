package com.yoyodyne.tracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Title {

    @NotNull
    private UUID titleId;

    @NotNull
    @Length(max = 255)
    private String name;

    public Title (UUID titleId, String name) {
	this.titleId = titleId;
	this.name = name;
    }

    @JsonProperty 
    public UUID getTitleId () {
	return this.titleId;
    }

    @JsonProperty 
    public String getName() {
	return this.name;
    }

}
