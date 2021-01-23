package com.boxever.DTO;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GistDTO {
    private String description;
    private FileDTO files;
    @SerializedName("public")
    private boolean isPublic;
    private String url;
    private String forks_url;
    private String commits_url;
    private String id;
    private String node_id;
    private String git_pull_url;
    private String git_push_url;
    private String html_url;
    private String created_at;
    private String updated_at;
    private int comments;
    private String user;
    private String comments_url;
    private OwnerDTO owner;
    private boolean truncated;
}
