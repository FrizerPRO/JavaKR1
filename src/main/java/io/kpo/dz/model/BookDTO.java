package io.kpo.dz.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookDTO {

    private Long id;

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

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private Integer amount;

}
