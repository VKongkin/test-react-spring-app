package kongkin.bbu.edu.crud.models.repository;

import kongkin.bbu.edu.crud.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Integer> {
    List<Teacher> findAllByStatus(String status);
}
