package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.departments.ManagementDepartment;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.company.staff.Manager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Optional;

/**
 * Represent a company.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Company implements Serializable
{
	private ObservableList<Employee> employees = FXCollections.observableArrayList();
	private ObservableList<StandardDepartment> departments = FXCollections.observableArrayList();
	private SimpleStringProperty name;
	private SimpleObjectProperty<Boss> boss;
	private ManagementDepartment managementDepartment;
	
	/**
	 * Construct a company with its name and boss.
	 *
	 * @param name The company's name.
	 * @param boss The company's boss.
	 */
	public Company(String name, Boss boss)
	{
		this.name = new SimpleStringProperty(name);
		this.boss = new SimpleObjectProperty<>(boss);
		this.managementDepartment = new ManagementDepartment(this, boss);
	}
	
	/**
	 * Get an employee by its ID.
	 *
	 * @param ID The ID of the employee to find.
	 *
	 * @return An optional of the requested employee.
	 */
	public Optional<Employee> getEmployee(int ID)
	{
		for(Employee employee : getEmployees())
			if(employee.getID() == ID)
				return Optional.of(employee);
		return Optional.empty();
	}
	
	/**
	 * Get a department by its ID.
	 *
	 * @param ID The ID of the department to look for.
	 *
	 * @return An optional of the requested department.
	 */
	public Optional<StandardDepartment> getDepartment(int ID)
	{
		for(StandardDepartment department : departments)
			if(department.getID() == ID)
				return Optional.of(department);
		return Optional.empty();
	}
	
	@Override
	public String toString()
	{
		return "Company: \t" + getName() + "\nManagement department: \t[" + getManagementDepartment() + "]\nDepartments: \t" + departments + "\nEmployees: \t" + getEmployees();
	}
	
	/**
	 * Remove a manager from the management department.
	 *
	 * @param manager The manager to remove.
	 */
	public void removeFromManagementTeam(Manager manager)
	{
		managementDepartment.removeEmployee(manager);
		manager.setManaging(false);
	}
	
	/**
	 * Add a manager to the management department.
	 *
	 * @param manager The manager to add.
	 */
	public void addToManagementTeam(Manager manager)
	{
		managementDepartment.addEmployee(manager);
		manager.setManaging(true);
	}
	
	/**
	 * Add a department to the company.
	 *
	 * @param department The department to add.
	 */
	public void addDepartment(StandardDepartment department)
	{
		if(!departments.contains(department))
			departments.add(department);
	}
	
	public void addEmployee(Employee employee)
	{
		if(!employees.contains(employee))
			employees.add(employee);
	}
	
	public void removeEmployee(Employee employee)
	{
		if(employee.getWorkingDepartment() == null)
			employees.remove(employee);
	}
	
	/**
	 * Get the boss of the company.
	 *
	 * @return The boss.
	 */
	public Boss getBoss()
	{
		return bossProperty().get();
	}
	
	private SimpleObjectProperty<Boss> bossProperty()
	{
		return boss;
	}
	
	/**
	 * Get the number of departments in the company.
	 *
	 * @return The department count.
	 */
	public int getDepartmentCount()
	{
		return departments.size();
	}
	
	/**
	 * Get the number of employees in the company.
	 *
	 * @return The employee count.
	 */
	public int getEmployeeCount()
	{
		return getEmployees().size();
	}
	
	/**
	 * Get all the employees of the company.
	 *
	 * @return A list of the employees.
	 */
	public ObservableList<Employee> getEmployees()
	{
		return employees;
	}
	
	/**
	 * Get the management department of the company.
	 *
	 * @return The management department.
	 */
	public ManagementDepartment getManagementDepartment()
	{
		return managementDepartment;
	}
	
	/**
	 * Get the name of the company.
	 *
	 * @return The company's name.
	 */
	public String getName()
	{
		return nameProperty().get();
	}
	
	public SimpleStringProperty nameProperty()
	{
		return name;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(getName());
		oos.writeObject(getBoss());
		oos.writeObject(getManagementDepartment());
		oos.writeInt(departments.size());
		for(int i = 0; i < departments.size(); i++)
			oos.writeObject(departments.get(i));
		oos.writeInt(employees.size());
		for(int i = 0; i < employees.size(); i++)
			oos.writeObject(employees.get(i));
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		name = new SimpleStringProperty((String) ois.readObject());
		boss = new SimpleObjectProperty<>((Boss) ois.readObject());
		
		managementDepartment = (ManagementDepartment) ois.readObject();
		departments = FXCollections.observableArrayList();
		int dptSize = ois.readInt();
		for(int i = 0; i < dptSize; i++)
			departments.add((StandardDepartment) ois.readObject());
		
		employees = FXCollections.observableArrayList();
		int empSize = ois.readInt();
		for(int i = 0; i < empSize; i++)
		{
			Object emp = ois.readObject();
			if(emp instanceof Manager)
				employees.add((Manager) emp);
			else
				employees.add((Employee) emp);
		}
	}
}
