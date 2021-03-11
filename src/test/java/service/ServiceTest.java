package service;

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
  void addStudent_validStudent() {
    Student student = new Student("123", "name", 12, "some@email.com");
    try {
      Student addedStudent = service.addStudent(student);
      assertNull(addedStudent);
    } catch(ValidationException e) {
      fail();
    }
  }

  @Test
  void addStudent_invalidStudent() {
    Student student = new Student("123", "name", -552, "some@email.com");
    try {
      service.addStudent(student);
      fail();
    } catch(ValidationException ignored) {

    }
  }
}