package pl.pwr.trash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Listing {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String photo;
    private Integer userId;
    private ListingStatus statusLis;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}