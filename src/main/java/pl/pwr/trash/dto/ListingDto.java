package pl.pwr.trash.dto;

import pl.pwr.trash.model.ListingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ListingDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String photo;
    private Integer userId;
    private ListingStatus statusLis;
    private LocalDateTime createdAt;
}
