package shop.mtcoding.hiberapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> { // Entity의 클래스타입, entity의 P.K 타입

}
