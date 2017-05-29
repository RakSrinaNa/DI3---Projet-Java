package fr.polytech.projectjava.mainapp.company;

import fr.polytech.projectjava.mainapp.company.departments.ManagementDepartment;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.Log;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
	private ObservableList<EmployeeCheck> checks;
	private ObservableList<Manager> managers = FXCollections.observableArrayList();
	
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
		this.checks = FXCollections.observableArrayList();
		employees.addListener(new ListChangeListener<Employee>() //Keep track the what happens to the employee list in order to update the manager list
		{
			@Override
			public void onChanged(Change<? extends Employee> c)
			{
				while(c.next())
				{
					if(c.wasAdded() || c.wasReplaced())
					{
						for(Employee emp : c.getAddedSubList())
							if(emp instanceof Manager)
								managers.add((Manager) emp);
					}
					else if(c.wasRemoved() || c.wasReplaced())
					{
						for(Employee emp : c.getRemoved())
							if(emp instanceof Manager)
								managers.remove(emp);
					}
				}
			}
		});
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
	 * Get all the employees of the company.
	 *
	 * @return A list of the employees.
	 */
	public ObservableList<Employee> getEmployees()
	{
		return employees;
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
		return getName();
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
	 * Get the name property.
	 *
	 * @return The name porperty.
	 */
	public SimpleStringProperty nameProperty()
	{
		return name;
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
		{
			departments.add(department);
			Log.info("Department " + department + " added to the company " + this);
		}
	}
	
	/**
	 * Add an employee to the company.
	 *
	 * @param employee The employee to add.
	 */
	public void addEmployee(Employee employee)
	{
		if(!employees.contains(employee))
		{
			employees.add(employee);
			Log.info("Employee " + employee + " added to the company " + this);
		}
	}
	
	/**
	 * Remove an employee from the company.
	 * If the employee is currently affected to a department, he/she won't be removed from the company.
	 *
	 * @param employee The employee to remove.
	 */
	public void removeEmployee(Employee employee)
	{
		if(employee.getWorkingDepartment() != null)
			employee.getWorkingDepartment().removeEmployee(employee);
		employee.getChecks().forEach(c -> checks.remove(c));
		employees.remove(employee);
		Log.info("Employee " + employee + " removed from the company " + this);
	}
	
	public void removeDepartment(StandardDepartment department)
	{
		if(department.getEmployees().size() > 0)
		{
			Log.warning("Department " + department + "is not empty so can't be removed from the company " + this);
			return;
		}
		departments.remove(department);
		Log.info("Department" + department + " removed from the company " + this);
	}
	
	/**
	 * Register an employee check to add it into the global check list.
	 *
	 * @param check The check to register.
	 */
	public void registerCheck(EmployeeCheck check)
	{
		checks.add(check);
	}
	
	/**
	 * UnRegister an employee check to remove it from the global check list.
	 *
	 * @param check The check to unregister.
	 */
	public void unregisterCheck(EmployeeCheck check)
	{
		checks.remove(check);
	}
	
	/**
	 * Serialize the object.
	 *
	 * @param oos The object stream.
	 *
	 * @throws IOException If the serialization failed.
	 */
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
		oos.writeInt(checks.size());
		for(int i = 0; i < checks.size(); i++)
			oos.writeObject(checks.get(i));
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
	
	/**
	 * Get the boss property.
	 *
	 * @return The boss property.
	 */
	private SimpleObjectProperty<Boss> bossProperty()
	{
		return boss;
	}
	
	/**
	 * Deserialize an object.
	 *
	 * @param ois The object stream.
	 *
	 * @throws IOException            If the deserialization failed.
	 * @throws ClassNotFoundException If the file doesn't represent the correct class.
	 */
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
		managers = FXCollections.observableArrayList();
		int empSize = ois.readInt();
		for(int i = 0; i < empSize; i++)
		{
			Object emp = ois.readObject();
			if(emp instanceof Manager)
			{
				employees.add((Manager) emp);
				managers.add((Manager) emp);
			}
			else
				employees.add((Employee) emp);
		}
		employees.addListener(new ListChangeListener<Employee>()
		{
			@Override
			public void onChanged(Change<? extends Employee> c)
			{
				while(c.next())
				{
					if(c.wasAdded() || c.wasReplaced())
					{
						for(Employee emp : c.getAddedSubList())
							if(emp instanceof Manager)
								managers.add((Manager) emp);
					}
					else if(c.wasRemoved() || c.wasReplaced())
					{
						for(Employee emp : c.getRemoved())
							if(emp instanceof Manager)
								managers.remove(emp);
					}
				}
			}
		});
		
		checks = FXCollections.observableArrayList();
		int chkSize = ois.readInt();
		for(int i = 0; i < chkSize; i++)
			checks.add((EmployeeCheck) ois.readObject());
	}
	
	/**
	 * Get all the checks of the company.
	 *
	 * @return The check list.
	 */
	public ObservableList<EmployeeCheck> getChecks()
	{
		return checks;
	}
	
	/**
	 * Get the departments.
	 *
	 * @return The departments.
	 */
	public ObservableList<StandardDepartment> getDepartements()
	{
		return departments;
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
	 * Get the managers of the company.
	 *
	 * @return The managers.
	 */
	public ObservableList<Manager> getManagers()
	{
		return managers;
	}
}
