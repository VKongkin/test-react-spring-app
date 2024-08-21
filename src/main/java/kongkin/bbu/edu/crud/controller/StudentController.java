package kongkin.bbu.edu.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kongkin.bbu.edu.crud.Config.webConfig;
import kongkin.bbu.edu.crud.SharePoint.Services.SharePointService;
import kongkin.bbu.edu.crud.models.Student;
import kongkin.bbu.edu.crud.models.service.StudentService;
import kongkin.bbu.edu.crud.models.service.implement.StudentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class StudentController extends webConfig {
    private final StudentService studentService;
    private final SharePointService sharePointService;
    private final StudentServiceImpl studentServiceImpl;

    @GetMapping("/students")
    public ResponseEntity<Object> getAllStudent(){

            List<Student> list = studentService.getAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable("id") Integer id){
        Student student = studentService.getById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @GetMapping("/students/active")
    public ResponseEntity<Object> getActiveStudent(){

     return null;
    }



    @PostMapping("/students/create")
    public ResponseEntity<Object> create(@RequestBody Student req){
        studentService.create(req);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    @PostMapping("/students/update")
    public ResponseEntity<Object> update(@RequestBody Student req){
        studentService.update(req);
        return new ResponseEntity<>("Update sucees", HttpStatus.OK);
    }

    @PostMapping("/students/delete")
    public ResponseEntity<Object> delete(@RequestBody Student student){
        studentService.delete(student);
        return new ResponseEntity<>("Delete success",HttpStatus.OK);
    }

    @PostMapping("/students/createFile")
    public ResponseEntity<Student> createStudent(@RequestParam("student") String studentJson,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws NoSuchAlgorithmException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Student student = objectMapper.readValue(studentJson, Student.class);
        log.info("Received student data: {}", student);

        String hashedFilename = studentService.hashFilename(file.getOriginalFilename());
        String datetimeNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String finalFilename = datetimeNow + "_" + hashedFilename;
        if (file != null) {

            sharePointService.uploadFile(file, finalFilename);
            log.info("Received file: {}", finalFilename);
            // Save the file with the final filename
        } else {
            log.info("No file uploaded");
        }

        studentService.createWithFile(student, file,finalFilename);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
