package peaksoft.dto;

import lombok.Builder;
import peaksoft.dto.dtoUser.UserResponse;

import java.util.List;
@Builder
public record PaginationResponse(List<UserResponse> userResponseList,
                                 int size,
                                 int page) {
    public PaginationResponse {
    }
}
