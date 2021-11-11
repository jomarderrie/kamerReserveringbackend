package com.example.taskworklife.dto.kamer;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class KamerImageDto {
    @NotNull
    @Size(min=4, max=255)
    private String displayName;

    @Image
    private String image;
}
