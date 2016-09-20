import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class DoctorTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void doctor_doctorInstantiatesCorrectly_true() {
    Doctor doctor = new Doctor("Frank", "heart");
    assertTrue(doctor instanceof Doctor);
  }

  @Test
  public void getName_returnsName_true() {
    Doctor doctor = new Doctor("Frank", "heart");
    doctor.getName();
    assertEquals(doctor.getName(), "Frank");
  }

  @Test
  public void getSpecialty_returnsSpecialty_true() {
    Doctor doctor = new Doctor("Frank", "heart");
    doctor.getSpecialty();
    assertEquals(doctor.getSpecialty(), "heart");
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Doctor firstDoctor = new Doctor("Frank", "heart");
    Doctor secondDoctor = new Doctor("Frank", "heart");
    assertTrue(firstDoctor.equals(secondDoctor));
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Doctor doctor = new Doctor("Frank", "heart");
    Doctor doctorTwo = new Doctor("Jim", "lungs");
    doctor.save();
    doctorTwo.save();
    assertEquals(true, Doctor.all().get(0).equals(doctor));
    assertEquals(true, Doctor.all().get(1).equals(doctorTwo));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Doctor myDoctor = new Doctor("Frank", "heart");
    myDoctor.save();
    assertTrue(Doctor.all().get(0).equals(myDoctor));
  }

  @Test
  public void getId_doctorsInstantiateWithAnID_true() {
    Doctor myDoctor = new Doctor("Frank", "heart");
    myDoctor.save();
    assertTrue(myDoctor.getId()>0);
  }

  @Test
  public void save_assignsIdToObject() {
    Doctor myDoctor = new Doctor("Frank", "heart");
    myDoctor.save();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(myDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void find_returnsDoctorWithSameId_secondTask(){
    Doctor firstDoctor = new Doctor("Frank", "heart");
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Jim", "lungs");
    secondDoctor.save();
    assertEquals(Doctor.find(secondDoctor.getId()), secondDoctor);
  }

}
