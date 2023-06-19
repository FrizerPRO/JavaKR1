package io.kpo.dz.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookOutDTO {
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String authorName;

    @NotNull
    @Size(max = 255)
    private String genre;

    @NotNull
    private Double price;
}
