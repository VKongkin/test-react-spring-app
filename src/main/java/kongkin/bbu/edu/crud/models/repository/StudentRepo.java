package kongkin.bbu.edu.crud.models.repository;

import kongkin.bbu.edu.crud.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findByName(Student name);
}
