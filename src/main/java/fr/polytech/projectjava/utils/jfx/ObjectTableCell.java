package fr.polytech.projectjava.utils.jfx;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.scene.control.TableCell;
import java.util.function.Function;

/**
 * Represent a cell of a TableView that updates when a property is modified.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class ObjectTableCell<S, T> extends TableCell<S, T>
{
	private final Function<T, Property> propertyFunction;

	/**
	 * Constructor.
	 *
	 * @param propertyFunction A function giving the property to watch from the item.
	 */
	public ObjectTableCell(Function<T, Property> propertyFunction)
	{
		super();
		this.propertyFunction = propertyFunction;
		InvalidationListener nameChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null)
				propertyFunction.apply(oldValue).removeListener(nameChangedListener);
			if(newValue != null)
				propertyFunction.apply(newValue).addListener(nameChangedListener);
		});
	}

	/**
	 * Get the function giving the property from an item.
	 *
	 * @return The function.
	 */
	protected Function<T, Property> getPropertyFunction()
	{
		return propertyFunction;
	}

	@Override
	public void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && item != null)
			setText(item.toString());
		else
			setText(null);
	}
}
