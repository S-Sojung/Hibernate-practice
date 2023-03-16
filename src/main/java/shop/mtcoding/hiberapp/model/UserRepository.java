package shop.mtcoding.hiberapp.model;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;
    // User user = User.builder().email("1234").id(1L);

    @Transactional
    public User save(User user) {
        em.persist(user);
        return user;
    };

    @Transactional
    public User update(User user) {
        // primary key 가 있는 키면 업데이트 해주고 없으면 insert를 해줌... 주의
        return em.merge(user);
    };

    @Transactional
    public void delete(User user) {
        em.remove(user);
    };

    public User findById(Long id) {
        return em.find(User.class, id);
    };

    public List<User> findAll(int page, int row) {
        // 하이버네이트가 지원하는 쿼리
        return em.createQuery("select u from User u", User.class)
                .setFirstResult(page * row) // 0페이지 // page*2 ... //page*row
                .setMaxResults(row) // 2 개씩 볼거임 //row 개씩 본다 가능
                .getResultList();
        // 테이블 이름을 사용하지 않고, 클래스 명을 부름
        // return em.createQuery("select u from User u where u.id=1");
    };

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    };
}
