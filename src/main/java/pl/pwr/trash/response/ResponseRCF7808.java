package pl.pwr.trash.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ResponseRCF7808 {
    private final String type;
    private final String title;
    private final String description;
    private final int status;
    private final String instance;
}

