package kongkin.bbu.edu.crud.controller;

import kongkin.bbu.edu.crud.models.Teacher;
import kongkin.bbu.edu.crud.models.service.implement.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeacherController {
    private final TeacherService teacherService;
    @GetMapping("/teachers/active")
    public ResponseEntity<Object> getActiveTeacher(){
        List<Teacher> list = teacherService.getByStatus("ACT");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
