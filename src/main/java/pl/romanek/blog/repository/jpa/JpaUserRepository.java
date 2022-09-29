package pl.romanek.blog.repository.jpa;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import pl.romanek.blog.entity.User;
import pl.romanek.blog.repository.UserRepository;

@Profile("jpa")
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<User> findById(Integer id) {
        User user = em.find(User.class, id);
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        User user = (User) em.createQuery("SELECT user FROM User user WHERE user.username=" + username)
                .getSingleResult();

        return user == null ? Optional.empty() : Optional.of(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT user FROM User user").getResultList();
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        em.createQuery("DELETE FROM User user WHERE user.id=" + id).executeUpdate();
    }

    @Override
    public void deleteByUsername(String username) {
        em.createQuery("DELETE FROM User user WHERE user.username=" + username).executeUpdate();
    }
}
