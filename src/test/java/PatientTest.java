import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class PatientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patients *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void patient_patientInstantiatesCorrectly_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    assertTrue(patient instanceof Patient);
  }

  @Test
  public void getName_returnsName_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.getName();
    assertEquals(patient.getName(), "Bill");
  }

  @Test
  public void getBirthdate_returnsBirthdate_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.getBirthdate();
    assertEquals(patient.getBirthdate(), "August 4th, 1984");
  }

  @Test
  public void getDoctorId_returnsDoctorId_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.getDoctorId();
    assertEquals(patient.getDoctorId(), 1);
  }

  @Test
  public void all_returnsAllInstantiatesOfPatient_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.save();
    Patient patientTwo = new Patient("Tim", "May 2nd, 1992", 1);
    patientTwo.save();
    assertEquals(true, Patient.all().contains(patient));
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame(){
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    Patient patientTwo = new Patient("Bill", "August 4th, 1984", 1);
     assertTrue(patient.equals(patientTwo));
  }

  @Test
  public void save_returnsTrueIfPatientsAretheSame(){
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.save();
    assertTrue(Patient.all().get(0).equals(patient));
  }
  @Test
  public void getId_returnsId_true() {
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.save();
    assertTrue(patient.getId() > 0);
  }

  @Test
  public void find_returnsPatientWithSameId_secondTask(){
    Patient patient = new Patient("Bill", "August 4th, 1984", 1);
    patient.save();
    Patient patientTwo = new Patient("Tim", "May 2nd, 1992", 1);
    patientTwo.save();
    assertEquals(Patient.find(patientTwo.getId()), patientTwo);
  }

}
