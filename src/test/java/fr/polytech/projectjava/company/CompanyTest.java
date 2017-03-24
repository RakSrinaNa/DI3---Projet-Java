package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-24
 */
public class CompanyTest
{
	private static final String COMPANY_NAME = "Company";
	private Company company;
	private Boss boss;
	
	@Before
	public void setUp() throws Exception
	{
		boss = new Boss("A", "B");
		company = new Company(COMPANY_NAME, boss);
	}
	
	@Test
	public void getSetEmployee() throws Exception
	{
		Employee employee1 = new Employee("A", "B", null);
		Employee employee2 = new Employee("A", "B", null);
		Employee employee3 = new Employee("A", "B", null);
		
		int ID1 = employee1.getID();
		int ID2 = employee2.getID();
		int ID3 = employee3.getID();
		
		assertEquals(0, company.getEmployeeCount());
		assertFalse(company.getEmployee(ID1).isPresent());
		assertFalse(company.getEmployee(ID2).isPresent());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		company.addEmployee(employee1);
		assertEquals(1, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertFalse(company.getEmployee(ID2).isPresent());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		company.addEmployee(employee2);
		assertEquals(2, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertTrue(company.getEmployee(ID2).isPresent());
		assertEquals(employee2, company.getEmployee(ID2).get());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		company.addEmployee(employee2);
		assertEquals(2, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertTrue(company.getEmployee(ID2).isPresent());
		assertEquals(employee2, company.getEmployee(ID2).get());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		company.addEmployee(employee3);
		assertEquals(3, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertTrue(company.getEmployee(ID2).isPresent());
		assertEquals(employee2, company.getEmployee(ID2).get());
		assertTrue(company.getEmployee(ID3).isPresent());
		assertEquals(employee3, company.getEmployee(ID3).get());
	}
	
	@Test
	public void getDepartment() throws Exception
	{
		StandardDepartment department1 = new StandardDepartment("A");
		StandardDepartment department2 = new StandardDepartment("A");
		int ID1 = department1.getID();
		int ID2 = department2.getID();
		
		Employee employee1 = new Employee("A", "B", department1);
		Employee employee2 = new Employee("A", "B", department2);
		Employee employee3 = new Employee("A", "B", department2);
		
		assertEquals(0, company.getDepartmentCount());
		assertFalse(company.getDepartment(ID1).isPresent());
		assertFalse(company.getDepartment(ID2).isPresent());
		
		company.addEmployee(employee1);
		assertEquals(1, company.getDepartmentCount());
		assertTrue(company.getDepartment(ID1).isPresent());
		assertEquals(department1, company.getDepartment(ID1).get());
		assertFalse(company.getDepartment(ID2).isPresent());
		
		company.addEmployee(employee2);
		assertEquals(2, company.getDepartmentCount());
		assertTrue(company.getDepartment(ID1).isPresent());
		assertEquals(department1, company.getDepartment(ID1).get());
		assertTrue(company.getDepartment(ID2).isPresent());
		assertEquals(department2, company.getDepartment(ID2).get());
		
		company.addEmployee(employee3);
		assertEquals(2, company.getDepartmentCount());
		assertTrue(company.getDepartment(ID1).isPresent());
		assertEquals(department1, company.getDepartment(ID1).get());
		assertTrue(company.getDepartment(ID2).isPresent());
		assertEquals(department2, company.getDepartment(ID2).get());
	}
	
	@Test
	public void getBoss() throws Exception
	{
		assertEquals(boss, company.getBoss());
	}
	
	@Test
	public void getManagementDepartment() throws Exception
	{
		assertEquals(boss, company.getManagementDepartment().getManager());
	}
	
	@Test
	public void getName() throws Exception
	{
		assertEquals(COMPANY_NAME, company.getName());
	}
}