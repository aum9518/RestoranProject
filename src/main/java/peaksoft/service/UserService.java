package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.dtoUser.PaginationResponse;
import peaksoft.dto.dtoUser.UserRequest;
import peaksoft.dto.dtoUser.UserResponse;

public interface UserService {

    PaginationResponse getAllUsers(int pageSize,int currentPage);
    SimpleResponse registerToJob (UserRequest userRequest);
    SimpleResponse acceptUser(Long restaurantId, Long userId,String word);
    SimpleResponse updateUserById(Long id, UserRequest userRequest);
    SimpleResponse deleteUserById(Long id);
    UserResponse getUserById(Long id);
    SimpleResponse saveUser(Long restaurantId,UserRequest userRequest);
}
