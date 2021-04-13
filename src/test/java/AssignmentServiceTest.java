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
import validation.ValidationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class AssignmentServiceTest {

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

  @Test
  public void tc_1_addAssignment_validNonExistingAssignment() {
    assertNull(service.addTema(new Tema("123", "abc", 1, 1)));
  }
//
//  @Test
//  public void tc_2_addAssignment_invalidAssignment() {
//    assertThrows(ValidationException.class,
//        () -> service.addTema(new Tema("", "", 1, 1)));
//  }

    @Test
    public void tc_3_addAssignment_alreadyExistingAssignment(){
        service.addTema(new Tema("123","abc",1,1));
        assertNotNull(service.addTema(new Tema("123","abc",1,1)));
        assertEquals(1, service.getAllTeme().spliterator().getExactSizeIfKnown());
    }

  @Test
  public void tc_4_addAssignment_invalidID() {
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema(null, "sdaf", 1, 1)));
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("", "fafdsa", 1, 1)));
  }

  @Test
  public void tc_5_addAssignment_invalidDescription() {
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("dsfa", null, 1, 1)));
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("fdsf", "", 1, 1)));
  }

  @Test
  public void tc_6_addAssignment_invalidDeadline() {
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("dsfa", "fdg", -2, 1)));
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("fdsf", "gfd", 155, 1)));
  }

  @Test
  public void tc_7_addAssignment_invalidPrimire() {
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("dsfa", "fdg", 10, -1)));
    assertThrows(ValidationException.class,
        () -> service.addTema(new Tema("fdsf", "gfd", 10, 1222)));
  }
}
