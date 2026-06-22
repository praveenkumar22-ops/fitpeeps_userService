package user_service.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user_service.DTO.ProfileResponse;
import user_service.DTO.UserRequest;
import user_service.DTO.UserUpdateRequest;
import user_service.models.User;
import user_service.services.ProfileServices;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServices profileServices;

    @PostMapping("/register")
    public ResponseEntity<?> createProfile(@RequestBody UserRequest request){
        System.out.println("hello");
        System.out.println(request);
        ProfileResponse profile=profileServices.createUserProfile(request);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping("/{userId}/update")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,@RequestBody UserUpdateRequest request){
        if(profileServices.getUserDetailsById(userId).isEmpty())return new ResponseEntity<>("User Not Found",HttpStatus.OK);
//        if(profileServices.checkForUser(userId).get().getId()!=req)
        ProfileResponse user=profileServices.updateUserProfile(request,userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Long userId){
        Optional<User> user=profileServices.getUserDetailsById(userId);
        ProfileResponse userResponse=new ProfileResponse(user.get().getName(), user.get().getEmail(), user.get().getUserId());

        // here we will return the optional empty object if a user doesn't exist.
        return user.isEmpty() ?
                new ResponseEntity<>("No Such User Found",HttpStatus.OK) :
                new ResponseEntity<>(userResponse,HttpStatus.OK);
    }

}
