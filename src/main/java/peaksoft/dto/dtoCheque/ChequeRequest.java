package peaksoft.dto.dtoCheque;

import lombok.Builder;

import java.time.ZonedDateTime;
@Builder
public record ChequeRequest(int priceAverage) {
    public ChequeRequest {
    }
}
