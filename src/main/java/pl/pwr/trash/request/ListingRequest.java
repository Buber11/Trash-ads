package pl.pwr.trash.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListingRequest {

    @NotBlank(message = "Tytuł nie może być pusty")
    @Size(max = 100, message = "Tytuł może mieć maksymalnie 100 znaków")
    private String title;

    @NotBlank(message = "Opis nie może być pusty")
    @Size(max = 1000, message = "Opis może mieć maksymalnie 1000 znaków")
    private String description;

    @NotNull(message = "Cena jest wymagana")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cena musi być większa niż 0")
    private BigDecimal price;

    @NotBlank(message = "Zdjęcie (URL) jest wymagane")
    @Pattern(regexp = "^(http|https)://.*$", message = "URL zdjęcia musi zaczynać się od http:// lub https://")
    private String photo;
}