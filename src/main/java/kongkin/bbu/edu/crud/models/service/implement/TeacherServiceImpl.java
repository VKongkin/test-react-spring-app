package kongkin.bbu.edu.crud.models.service.implement;

import kongkin.bbu.edu.crud.models.Teacher;
import kongkin.bbu.edu.crud.models.repository.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{
    private final TeacherRepo teacherRepo;

    @Override
    public List<Teacher> getByStatus(String status) {
        return teacherRepo.findAllByStatus(status);
    }
}
