package com.yoyodyne.tracker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Achievement {

    @NotNull
    private UUID achievementId;

    @NotNull
    private UUID titleId;

    @NotNull
    @Length(max = 255)
    private String name;

    @NotNull
    @Length(max = 4000)
    private String description;

    @NotNull
    @Length(max = 255)
    private String sort;

    public Achievement (UUID achievementId, UUID titleId, String name, String description, String sort) {
        this.achievementId = achievementId;
        this.titleId = titleId;
        this.description = description;
        this.name = name;
        this.sort = sort;
    }
    
    public Achievement() {} // required for deserialization


    @JsonProperty
    public UUID getAchievementId () {
        return this.achievementId;
    }

    @JsonProperty
    public void setAchievementId (String uuidStr) {
        if (uuidStr != null && !uuidStr.isEmpty()) {
            this.achievementId = UUID.fromString( uuidStr );
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
    public String getName () {
        return this.name;
    }

    @JsonProperty
    public void setName (String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    @JsonProperty
    public String getDescription () {
        return this.description;
    }

    @JsonProperty
    public void setDescription (String description) {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        }
    }

    @JsonProperty
    public String getSort () {
        return this.sort;
    }

    @JsonProperty
    public void setSort (String sort) {
        if (sort != null && !sort.isEmpty()) {
            this.sort = sort;
        }
    }

}
