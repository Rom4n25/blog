package pl.romanek.blog.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.romanek.blog.entity.Role;
import pl.romanek.blog.repository.RoleRepository;

@Profile("jpa")
@Repository
public class JpaRoleRepository implements RoleRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Role save(Role role) {
        em.persist(role);
        return role;
    }
}
