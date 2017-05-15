package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Manager;
import java.io.Serializable;

/**
 * Represent the management department.
 * This one contains every manager of the company and the manager is the boss.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class ManagementDepartment extends Department<Boss, Manager> implements Serializable
{
	private static final long serialVersionUID = 1742389161895659199L;
	
	/**
	 * Construct the department with the boss.
	 *
	 * @param company The company the department is in.
	 * @param boss The boss of the company.
	 */
	public ManagementDepartment(Company company, Boss boss)
	{
		super(company, "Management department", boss);
	}
}
