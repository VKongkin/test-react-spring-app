package kongkin.bbu.edu.crud.models.service;

import kongkin.bbu.edu.crud.models.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface StudentService {
    List<Student> getAll();
    Student getById(Integer Id);

    void create(Student student);
    Student createWithFile(Student student, MultipartFile file, String fileName) throws NoSuchAlgorithmException;
    String hashFilename(String originalFilename) throws NoSuchAlgorithmException;

    void update(Student student);
    void delete(Student student);
}
