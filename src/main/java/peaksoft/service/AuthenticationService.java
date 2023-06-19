package peaksoft.service;

import peaksoft.dto.AdminTokenRequest;
import peaksoft.dto.AuthenticationResponse;
import peaksoft.dto.SignInRequest;

public interface AuthenticationService {

    AuthenticationResponse getAdminToken(AdminTokenRequest adminTokenRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);

}
