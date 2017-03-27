package fr.polytech.projectjava.company.staff;

/**
 * Represent a manager in the company.
 * A manager can only manage less or equal than one department.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Manager extends Employee
{
	private boolean managing;
	
	/**
	 * Construct a manager with his/her name and his affected department.
	 *
	 * @param lastName          His/her last name.
	 * @param firstName         His/her first name.
	 */
	public Manager(String lastName, String firstName)
	{
		super(lastName, firstName);
		managing = false;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + (isManaging() ? "\nManager" : "");
	}
	
	/**
	 * Return the managing status of the manager.
	 *
	 * @return True he/she is managing a department, false otherwise.
	 */
	public boolean isManaging()
	{
		return managing;
	}
	
	/**
	 * Set the managing status of the manager.
	 *
	 * @param managing The status to set.
	 */
	public void setManaging(boolean managing)
	{
		this.managing = managing;
	}
}
