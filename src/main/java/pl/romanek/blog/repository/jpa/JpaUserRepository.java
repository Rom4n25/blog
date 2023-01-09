package pl.romanek.blog.repository.jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.UserRepository;

@Profile("jpa")
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<User> findById(Integer id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = em.createQuery("SELECT user FROM User user JOIN FETCH user.role WHERE user.username=:username");
        query.setParameter("username", username);
        User user = (User) query.getSingleResult();
        return Optional.ofNullable(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT user FROM User user JOIN FETCH user.role").getResultList();
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        em.createQuery("DELETE FROM User user WHERE user.id=" + id).executeUpdate();
    }

    @Override
    public void deleteByUsername(String username) {
        Query query = em.createQuery("DELETE FROM User user WHERE user.username=:username");
        query.setParameter("username", username);
        query.executeUpdate();
    }
}
