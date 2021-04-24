package service.integration;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class Lab4Integration {
  private Service service;

  @BeforeEach
  public void setUp() {
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
  public void tearDown() throws IOException {
    Path file = Paths.get("src/test/resources/fisiere/Studenti.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    file = Paths.get("src/test/resources/fisiere/Teme.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
    file = Paths.get("src/test/resources/fisiere/Note.xml");
    Files.write(file, Collections.singletonList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>"), StandardCharsets.UTF_8);
  }

  public void addStudent() {
    Student student = new Student("123", "name", 100, "some@email.com");
    assertNull(service.addStudent(student));
  }

  public void addAssignment() {
    assertNull(service.addTema(new Tema("123", "abc", 1, 1)));
  }

  public void addGrade() {
    service.addNota(new Nota("1","123","123",10.0, LocalDate.now()),"asd");
    assertEquals(10.0, service.addNota(new Nota("1","123","123",10.0, LocalDate.now()),"asd"));
  }

  @Test
  public void addStudent_integration() {
    addStudent();
  }

  @Test
  public void addAssignment_integration() {
    addStudent();
    addAssignment();
  }

  @Test
  public void addGrade_integration() {
    addStudent();
    addAssignment();
    addGrade();
  }
}
