package shop.mtcoding.hiberapp.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter // dto로 받으면 여기에 setter 안해도 된다.
@Getter
// ORM을 실현해주는 하이버네이터 라는 얘가 항상 디폴트 생성자를 부른다.
@NoArgsConstructor
@Table(name = "user_tb")
@Entity
public class User {
    @Id // javax = Jakarta
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터 베이스의 정체성에 맞게 사용하라.
    private Long id;
    private String username;
    @JsonIgnore // 얘는 파싱하지 마라...: 화면에 표시 안할 데이터
    private String password;
    private String email;
    @CreationTimestamp
    private Timestamp createdAt;

    // public void save(String username, String password, String email){
    // this.username = username;
    // this.password = password;
    // this.email = email;
    // }

    @Builder // 선택적 매개변수로 사용 가능
    public User(Long id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void update(String password, String email) {
        this.password = password;
        this.email = email;
    }

}

// DTO는 다른세상에서 오는 임시 데이터
// 다른 세상에 있는걸 그대로 받아서 그대로 사용
// 세터 필요없음 게터만 필요
// 세터 대신 상태 변경이 필요한 메서드를 만드는 것.
// 단, 초기화 할때 만드는 것이라면, 생성자로 만들자.
// 시간의 흐름에 따라 수정 되는 것. 은 메서드로 만들자.
// 세터는 의미있는.. 이름을 가지자..! 하지만 DTO는 상관없다.