package prog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import prog.Model.Person;

import java.util.Optional;
@Repository
public interface PersonRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByEmail(String email);
}
