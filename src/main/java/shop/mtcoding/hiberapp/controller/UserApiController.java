package shop.mtcoding.hiberapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.hiberapp.model.User;
import shop.mtcoding.hiberapp.model.UserJpaRepository;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserApiController {

    // private final UserRepository userRepository;
    private final UserJpaRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);

        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}") // 주소로 받은 데이터는 신뢰할 수 없음!! 권한 및 인증 체크
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {// 얘는 id와 날짜가 없음
        // User userPS = userRepository.findById(id);
        User userPS = userRepository.findById(id).get(); // .get() 무조건 있어 라는 의미
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // 모든 정보 (id와 날짜가 있는) userPS에 받은 DTO 값으로 업데이트 해줌.
        userPS.update(user.getPassword(), user.getEmail());
        // User updateUserPS = userRepository.update(userPS);
        User updateUserPS = userRepository.save(userPS); // pk를 들고 있으면 update 해주고, 없으면 insert 해줌

        return new ResponseEntity<>(updateUserPS, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}") // 삭제한 후에는 돌려줄 데이터가 없음. responseDTO를 담아서 보내면 됨...
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다.", HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(userPS);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/users") // 삭제한 후에는 돌려줄 데이터가 없음. responseDTO를 담아서 보내면 됨...
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {
        // List<User> userListPS = userRepository.findAll(page, 2);

        Page<User> userListPS = userRepository.findAll(PageRequest.of(page, 2));

        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}") // 삭제한 후에는 돌려줄 데이터가 없음. responseDTO를 담아서 보내면 됨...
    public ResponseEntity<?> findUserOne(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("해당 유저가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }
}
