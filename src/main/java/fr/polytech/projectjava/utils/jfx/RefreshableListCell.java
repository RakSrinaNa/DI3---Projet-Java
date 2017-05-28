package fr.polytech.projectjava.utils.jfx;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.scene.control.ListCell;
import java.util.function.Function;
/**
 * Represent a ListCell that refresh itself when a property is updated.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class RefreshableListCell<T> extends ListCell<T>
{
	/**
	 * Constructor.
	 * @param bindProperty A function giving the property to watch from the item.
	 */
	public RefreshableListCell(Function<T, Property> bindProperty)
	{
		super();
		InvalidationListener itemChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null)
				bindProperty.apply(oldValue).removeListener(itemChangedListener);
			if(newValue != null)
				bindProperty.apply(newValue).addListener(itemChangedListener);
		});
	}

	@Override
	protected void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && item != null)
			setText(item.toString());
		else
			setText(null);
	}
}
