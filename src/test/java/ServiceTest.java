import domain.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaFileRepository;
import repository.NotaXMLRepo;
import repository.StudentFileRepository;
import repository.StudentXMLRepo;
import repository.TemaFileRepository;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

  private Service service;

  @BeforeEach
  void setUp() {
    String filenameStudent = "src/test/resources/fisiere/Studenti.xml";
    String filenameTema = "src/test/resources/fisiere/Teme.xml";
    String filenameNota = "src/test/resources/fisiere/Note.xml";

    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);

    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);

    service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
  }

  @AfterEach
  void tearDown() throws IOException {
    Path file = Paths.get("src/test/resources/fisiere/Studenti.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    file = Paths.get("src/test/resources/fisiere/Teme.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    file = Paths.get("src/test/resources/fisiere/Note.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);

  }

  @Test
  void tc_1_addStudent_validStudentGroup() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_2_addStudent_invalidStudentGroup() {
    Student student = new Student("123", "name", -552, "some@email.com");
    assertThrows(ValidationException.class, ()->service.addStudent(student));
  }
  @Test
  void tc_3_addStudent_validStudentName() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_4_addStudent_invalidStudentName() {
    Student student = new Student("123", "", 100, "some@email.com");
    assertThrows(ValidationException.class, ()->service.addStudent(student));
  }
  @Test
  void tc_5_addStudent_validStudentEmail() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_6_addStudent_invalidStudentEmail() {
    Student student = new Student("123", "name", 100, "");
    assertThrows(ValidationException.class, ()->service.addStudent(student));
  }
  @Test
  void tc_7_addStudent_validStudentId() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_8_addStudent_invalidStudentId() {
    Student student = new Student("", "name", 100, "some@email.com");
    assertThrows(ValidationException.class, ()->service.addStudent(student));
  }

  @Test
  void tc_9_addStudent_validStudentGroup_BVA2() {
    Student student = new Student("123", "name", 1, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_10_addStudent_validStudentGroup_BVA3() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  @Test
  void tc_11_addStudent_invalidStudentGroup_BVA4() {
    Student student = new Student("123", "name", Integer.MAX_VALUE+1, "some@email.com");
    assertThrows(ValidationException.class, ()->service.addStudent(student));
  }
}