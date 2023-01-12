package pl.ziolson.tacocloud.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.ziolson.tacocloud.Ingredient;
import pl.ziolson.tacocloud.Ingredient.Type;
import pl.ziolson.tacocloud.Order;
import pl.ziolson.tacocloud.Taco;
import pl.ziolson.tacocloud.data.IngredientRepository;
import pl.ziolson.tacocloud.data.TacoRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private IngredientRepository ingredientRepository;
    private TacoRepository tacoRepository;

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "pszenna", Type.WRAP),
//                new Ingredient("COTO", "kukurydziana", Type.WRAP),
//                new Ingredient("GRBF", "mielona wołowina", Type.PROTEIN),
//                new Ingredient("CARN", "kawałki mięsa", Type.PROTEIN),
//                new Ingredient("TMTO", "pomidory pokrojone w kostke", Type.VEGGIES),
//                new Ingredient("LETC", "sałata", Type.VEGGIES),
//                new Ingredient("CHED", "cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "śmietana", Type.SAUCE)
//        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
        model.addAttribute("taco", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            return "design";
        }

        Taco saved = tacoRepository.save(taco);
        order.getTacos().add(saved);
        log.info("Przetwarzanie projektu taco: " + taco);
        return "redirect:/orders/current";
    }

    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ingredient -> ingredient.getType().equals(type))
                .collect(Collectors.toList());
    }
}
