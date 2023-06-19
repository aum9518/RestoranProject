package peaksoft.dto;

import lombok.Builder;

@Builder
public record SignInRequest(String email,String password) {
    public SignInRequest {
    }
}
