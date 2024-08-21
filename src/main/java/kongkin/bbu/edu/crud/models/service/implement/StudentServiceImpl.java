package kongkin.bbu.edu.crud.models.service.implement;

import kongkin.bbu.edu.crud.models.Student;
import kongkin.bbu.edu.crud.models.service.StudentService;
import kongkin.bbu.edu.crud.models.repository.StudentRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepo studentRepo;



    @Override
    public List<Student> getAll() {
        return studentRepo.findAll();
    }

    @Override
    public Student getById(Integer Id) {
        return studentRepo.findById(Id).orElse(null);
    }





    @Override
    public void create(Student student) {
        student.setStatus("ACT");
        studentRepo.save(student);
    }

    @Override
    public Student createWithFile(Student student, MultipartFile file, String fileName) throws NoSuchAlgorithmException {
        if (file != null && !file.isEmpty()) {
//            String datetimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            String filename = datetimeNow + "_" + hashFilename(file.getOriginalFilename());
            student.setImagePath(fileName);  // Save the filename with extension
        }
        return studentRepo.save(student);
    }

    @Override
    public String hashFilename(String originalFilename) throws NoSuchAlgorithmException {
        String extension = "";
        int i = originalFilename.lastIndexOf('.');
        if (i > 0) {
            extension = originalFilename.substring(i);
            originalFilename = originalFilename.substring(0, i);
        }

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(originalFilename.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString() + extension;
    }


    @Override
    public void update(Student student) {
        String message = "Not found";
        var checkStu = getById(student.getId());
        if (checkStu == null){
            log.error("No id found");
        }
        student.setStatus("ACT");
        studentRepo.save(student);

    }

    @Override
    public void delete(Student student) {
        var checkStu = getById(student.getId());
        if (checkStu == null){
            log.error("No id found");
        }
        student.setName(student.getName());
        student.setDOB(student.getDOB());
        student.setAddress(student.getAddress());
        student.setStatus("DEL");
        studentRepo.save(student);

    }
}
