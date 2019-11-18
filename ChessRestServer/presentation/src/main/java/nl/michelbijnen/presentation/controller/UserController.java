package nl.michelbijnen.presentation.controller;

import nl.michelbijnen.presentation.*;
import nl.michelbijnen.presentation.ViewModel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends Auth {

    private IUserLogic iUserLogic = Factory.getIUserLogic();

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginViewModel lvm) {
        Authentication authentication = this.iUserLogic.login(lvm.getUsername(), lvm.getPassword());

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
        }

        return ResponseEntity.ok(authentication);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterViewModel rvm) {
        User user = new User();
        user.setUsername(rvm.getUsername());
        user.setPassword(rvm.getPassword());
        user.setFirstName(rvm.getFirstName());
        user.setLastName(rvm.getLastName());
        user.setEmail(rvm.getEmail());
        user.setGender(rvm.getGender());

        if (this.iUserLogic.register(user)) {
            return ResponseEntity.ok("Registered!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
    }

    @PutMapping("/forgotpassword")
    public ResponseEntity forgotPassword(@RequestBody ForgotPasswordViewModel fpvm) {
        if (this.iUserLogic.forgotPassword(fpvm.getEmail())) {
            return ResponseEntity.ok("Password has been reset! Check your email!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
    }

    @GetMapping("/getuser/{privateKey}/{userId}")
    public ResponseEntity getUser(@PathVariable String privateKey, @PathVariable String userId) {
        if (this.authentication(privateKey)) {
            return ResponseEntity.ok(this.iUserLogic.getUser(userId));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized!");
    }

    @PutMapping("/update/{privateKey}")
    public ResponseEntity update(@PathVariable String privateKey, @RequestBody UpdateViewModel uvm) {
        if (this.authentication(privateKey)) {
            User user = new User();
            user.setId(uvm.getUserId());
            user.setUsername(uvm.getUsername());
            user.setPassword(uvm.getPassword());
            user.setEmail(uvm.getEmail());
            user.setFirstName(uvm.getFirstName());
            user.setLastName(uvm.getLastName());
            user.setGender(uvm.getGender());

            if (this.iUserLogic.update(user, uvm.getOldPassword())) {
                return ResponseEntity.ok("User successfully updated!");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
    }

    @PostMapping("/addScore/{privateKey}")
    public ResponseEntity addScore(@PathVariable String privateKey, @RequestBody ScoreViewModel svm) {
        if (this.authentication(privateKey)) {
            if (this.iUserLogic.addScore(svm.getUserId(), Integer.parseInt(svm.getScore()))) {
                return ResponseEntity.ok("Score successfully added!");
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
    }

    @PostMapping("/logout/{privateKey}")
    public ResponseEntity logout(@PathVariable String privateKey, @RequestBody LogoutViewModel lvm) {
        if (this.authentication(privateKey)) {
            //TODO Logout function
            return ResponseEntity.ok("Successfully logged out!");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not authorized");
    }
}
