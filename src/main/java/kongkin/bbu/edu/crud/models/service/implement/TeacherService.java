package kongkin.bbu.edu.crud.models.service.implement;

import kongkin.bbu.edu.crud.models.Teacher;

import java.util.List;

public interface TeacherService {
    List<Teacher> getByStatus(String status);
}
