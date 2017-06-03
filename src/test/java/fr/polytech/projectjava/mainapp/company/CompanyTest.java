package fr.polytech.projectjava.mainapp.company;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
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
		StandardDepartment department1 = new StandardDepartment(company, "A", new Manager(company, "A", "B"));
		StandardDepartment department2 = new StandardDepartment(company, "A", new Manager(company, "A", "B"));
		
		Employee employee1 = new Employee(company, "A", "B");
		Employee employee2 = new Employee(company, "A", "B");
		Employee employee3 = new Employee(company, "A", "B");
		
		int ID1 = employee1.getID();
		int ID2 = employee2.getID();
		int ID3 = employee3.getID();
		
		int employeeCount = company.getEmployeeCount();
		
		company.addEmployee(null);
		assertEquals(employeeCount, company.getEmployeeCount());
		
		assertEquals(employeeCount, company.getEmployeeCount());
		assertFalse(company.getEmployee(ID1).isPresent());
		assertFalse(company.getEmployee(ID2).isPresent());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		department1.addEmployee(employee1);
		assertEquals(employeeCount + 1, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertFalse(company.getEmployee(ID2).isPresent());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		department2.addEmployee(employee2);
		assertEquals(employeeCount + 2, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertTrue(company.getEmployee(ID2).isPresent());
		assertEquals(employee2, company.getEmployee(ID2).get());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		department1.addEmployee(employee2);
		assertEquals(employeeCount + 2, company.getEmployeeCount());
		assertTrue(company.getEmployee(ID1).isPresent());
		assertEquals(employee1, company.getEmployee(ID1).get());
		assertTrue(company.getEmployee(ID2).isPresent());
		assertEquals(employee2, company.getEmployee(ID2).get());
		assertFalse(company.getEmployee(ID3).isPresent());
		
		department2.addEmployee(employee3);
		assertEquals(employeeCount + 3, company.getEmployeeCount());
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
		StandardDepartment department1 = new StandardDepartment(company, "A", new Manager(company, "A", "B"));
		int ID1 = department1.getID();
		
		int departmentCount = company.getDepartmentCount();
		
		assertEquals(departmentCount, company.getDepartmentCount());
		assertTrue(company.getDepartment(ID1).isPresent());
		assertEquals(department1, company.getDepartment(ID1).get());
		
		StandardDepartment department2 = new StandardDepartment(company, "A", new Manager(company, "A", "B"));
		int ID2 = department2.getID();
		
		assertEquals(departmentCount + 1, company.getDepartmentCount());
		assertTrue(company.getDepartment(ID1).isPresent());
		assertEquals(department1, company.getDepartment(ID1).get());
		assertTrue(company.getDepartment(ID2).isPresent());
		assertEquals(department2, company.getDepartment(ID2).get());
		
		assertFalse(company.getDepartment(Integer.MAX_VALUE).isPresent());
	}
	
	@Test
	public void getBoss() throws Exception
	{
		assertEquals(boss, company.getBoss());
	}
	
	@Test
	public void getManagementDepartment() throws Exception
	{
		assertEquals(boss, company.getManagementDepartment().getLeader());
	}
	
	@Test
	public void getName() throws Exception
	{
		assertEquals(COMPANY_NAME, company.getName());
	}
}