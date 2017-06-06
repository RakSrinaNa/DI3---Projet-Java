package fr.polytech.projectjava.mainapp.company.departments;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Person;
import fr.polytech.projectjava.utils.Log;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;

/**
 * Represent a department
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public abstract class Department<B extends Person & Serializable, E extends Employee & Serializable> implements Serializable
{
	private static final long serialVersionUID = 3644405617796041285L;
	protected static int NEXT_ID = 0;
	protected Company company;
	private SimpleObjectProperty<B> leader = new SimpleObjectProperty<>();
	private SimpleIntegerProperty memberCount = new SimpleIntegerProperty();
	private int ID;
	private SimpleStringProperty name;
	private ObservableList<E> employees = FXCollections.observableArrayList();
	
	/**
	 * Construct a department of a company with its name.
	 *
	 * @param company The company the department is in.
	 * @param name    The name of the department.
	 * @param leader  The leader of this department.
	 */
	public Department(Company company, String name, B leader)
	{
		this.ID = NEXT_ID++;
		this.company = company;
		this.name = new SimpleStringProperty(name);
		this.leader = new SimpleObjectProperty<>(leader);
		memberCount.set(0);
		employees.addListener((InvalidationListener) observable -> memberCount.set(employees.size()));
		Log.info("Department " + getName() + " created and have ID " + getID());
	}
	
	/**
	 * Get the number of members in this department.
	 *
	 * @return The number of members.
	 */
	public int getMemberCount()
	{
		return memberCount.get();
	}
	
	/**
	 * Get the number of members property.
	 *
	 * @return The number of members property.
	 */
	public SimpleIntegerProperty memberCountProperty()
	{
		return memberCount;
	}
	
	/**
	 * Add an employee to the department.
	 *
	 * @param employee The employee to add.
	 */
	public void addEmployee(E employee)
	{
		if(employee != null && !employees.contains(employee)) //Avoid duplicate employees
		{
			employees.add(employee);
			company.addEmployee(employee);
			Log.info("Employee " + employee + " added to the department " + this);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Department && ID == ((Department) obj).getID();
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
	
	/**
	 * Get the name of the department.
	 *
	 * @return Its name.
	 */
	public String getName()
	{
		return nameProperty().get();
	}
	
	/**
	 * Get the name property.
	 *
	 * @return The name property.
	 */
	public SimpleStringProperty nameProperty()
	{
		return name;
	}
	
	/**
	 * Get the unique ID of the department.
	 *
	 * @return Its ID.
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Remove an employee from the department.
	 *
	 * @param employee The employee to remove.
	 */
	public void removeEmployee(E employee)
	{
		this.employees.remove(employee);
	}
	
	/**
	 * Used to know if the department have this employee.
	 *
	 * @param employee The employee to check.
	 *
	 * @return true if in the department, false else.
	 */
	public boolean hasEmployee(E employee)
	{
		return getEmployees().contains(employee);
	}
	
	/**
	 * Get the employees of the department.
	 *
	 * @return A list of the employees.
	 */
	public ObservableList<E> getEmployees()
	{
		return employees;
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
		oos.writeInt(getID());
		oos.writeObject(getName());
		oos.writeObject(getLeader());
		oos.writeObject(company);
		oos.writeInt(getEmployees().size());
		for(Employee employee : getEmployees())
			oos.writeObject(employee);
	}
	
	/**
	 * Get the leader.
	 *
	 * @return The leader.
	 */
	public B getLeader()
	{
		return leaderProperty().get();
	}
	
	/**
	 * Set the leaser.
	 *
	 * @param leader The leader to set.
	 */
	protected void setLeader(B leader)
	{
		this.leader.set(leader);
		Log.log(leader == null ? Level.WARNING : Level.INFO, "Leader of " + this + " is now " + leader);
	}
	
	/**
	 * Get the leader property.
	 *
	 * @return The leader property.
	 */
	public SimpleObjectProperty<B> leaderProperty()
	{
		return leader;
	}
	
	/**
	 * Deserialize an object.
	 *
	 * @param ois The object stream.
	 *
	 * @throws IOException            If the deserialization failed.
	 * @throws ClassNotFoundException If the file doesn't represent the correct class.
	 * @throws ClassCastException     If the leader class isn't correct.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, ClassCastException
	{
		ID = ois.readInt();
		NEXT_ID = Math.max(ID + 1, NEXT_ID); // Don't forget to change the next ID to avoid duplicate IDs.
		name = new SimpleStringProperty((String) ois.readObject());
		//noinspection unchecked
		leader = new SimpleObjectProperty<>((B) ois.readObject());
		company = (Company) ois.readObject();
		
		employees = FXCollections.observableArrayList();
		int empCount = ois.readInt();
		for(int i = 0; i < empCount; i++)
			//noinspection unchecked
			employees.add((E) ois.readObject());
		memberCount = new SimpleIntegerProperty(employees.size());
		employees.addListener((InvalidationListener) observable -> memberCount.set(employees.size()));
	}
	
	/**
	 * Get the company of the department.
	 *
	 * @return The company.
	 */
	public Company getCompany()
	{
		return company;
	}
}
