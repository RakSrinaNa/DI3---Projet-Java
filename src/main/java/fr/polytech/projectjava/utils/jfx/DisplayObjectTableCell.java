package fr.polytech.projectjava.utils.jfx;

import javafx.beans.property.Property;
import java.util.function.Function;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class DisplayObjectTableCell<S, T> extends ObjectTableCell<S, T>
{
	private final Function<T, ?> displayFunction;

	/**
	 * Constructor.
	 *
	 * @param propertyFunction A function giving the property to watch from the item.
	 * @param displayFunction A function giving the value to display from the item.
	 */
	public DisplayObjectTableCell(Function<T, Property> propertyFunction, Function<T, ?> displayFunction)
	{
		super(propertyFunction);
		this.displayFunction = displayFunction;
	}

	@Override
	public void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && item != null)
			setText(displayFunction.apply(item).toString());
	}
}
