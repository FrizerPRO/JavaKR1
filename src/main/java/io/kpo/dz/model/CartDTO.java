package io.kpo.dz.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartDTO {

    private Long id;

    @NotNull
    private Integer amount;

    private List<Long> books;

}
