package user_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import user_service.DTO.ProfileResponse;
import user_service.DTO.UserRequest;
import user_service.DTO.UserUpdateRequest;
import user_service.models.User;
import user_service.repos.UserRepo;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProfileServices {
    private final UserRepo userRepo;

    public ProfileResponse convetToProfileResponse(User request){
        return new ProfileResponse(request.getName(),request.getEmail(),request.getUserId());
    }

    // create User profile
    public ProfileResponse createUserProfile(UserRequest request){
        User user=new User();
        user.setName(request.name());
        user.setBirthDate(request.birthDate());
        user.setEmail(request.email());
        User savedUser=userRepo.save(user);
        return convetToProfileResponse(savedUser);
    }
    // update the user profile
    public ProfileResponse updateUserProfile(UserUpdateRequest request, Long userId){
        Optional<User> existedUser=getUserDetailsById(userId);
        User user=existedUser.get();
        user.setEmail(request.email());
        return new ProfileResponse(user.getName(),user.getEmail(),user.getUserId());
    }

    // deletetion of user
    public Optional<ProfileResponse> deleteUser(Long userId){
        Optional<User> user=getUserDetailsById(userId);
        userRepo.deleteById(userId);
        return Optional.of(convetToProfileResponse(user.get()));
    }

    public Optional<User> getUserDetailsById(Long userId) {return userRepo.findById(userId);}
}
