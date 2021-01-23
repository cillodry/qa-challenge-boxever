package com.boxever.DTO;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;

@Data
public class FileDTO {
    @SerializedName("loremIpsum.txt")
    private HashMap container;
}
