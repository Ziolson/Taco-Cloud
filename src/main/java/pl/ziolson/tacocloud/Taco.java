package pl.ziolson.tacocloud;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Taco {

    private Long id;
    private Date createdAt;
    @NotNull
    @Size(min = 5, message = "Nazwa musi składać się z przynajmniej pięciu znaków.")
    private String name;
    @Size(min = 1, message = "Musisz wybrać przynajmniej jeden składnik.")
    private List<Ingredient> ingredients;
}
