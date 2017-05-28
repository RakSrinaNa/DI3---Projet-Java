package fr.polytech.projectjava.utils.jfx;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.StringConverter;
import java.util.function.Function;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class ObjectComboBoxTableCell<S, T> extends ComboBoxTableCell<S, T>
{
	public ObjectComboBoxTableCell(StringConverter<T> converter, ObservableList<T> items, Function<T, Property> bindProperty)
	{
		super(converter, items);
		InvalidationListener nameChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null)
				bindProperty.apply(oldValue).removeListener(nameChangedListener);
			if(newValue != null)
				bindProperty.apply(newValue).addListener(nameChangedListener);
		});
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
