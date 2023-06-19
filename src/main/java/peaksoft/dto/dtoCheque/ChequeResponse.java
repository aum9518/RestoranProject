package peaksoft.dto.dtoCheque;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
public record ChequeResponse(Long id,
                             String waiterFullName,
                             List<MenuItem> items,
                             int priceAverage,
                             int service,
                             int grandTotal) {
    public ChequeResponse {
    }

    public ChequeResponse(Long id, int priceAverage) {
        this(id, null, null, priceAverage, 0, 0);
    }
}
