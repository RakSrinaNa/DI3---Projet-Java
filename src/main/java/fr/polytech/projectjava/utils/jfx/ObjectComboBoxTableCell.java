package fr.polytech.projectjava.utils.jfx;

import javafx.beans.InvalidationListener;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.util.function.Function;

/**
 * Represent an editable cell in a TableView that update its content when a property is modified and allow changes with a ComboBox.
 * A lot of its content is based ojn {@link javafx.scene.control.cell.ComboBoxTableCell}
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class ObjectComboBoxTableCell<S, T> extends ObjectTableCell<S, T>
{
	private final static StringConverter<?> defaultStringConverter = new StringConverter<Object>()
	{
		@Override
		public String toString(Object t)
		{
			return t == null ? null : t.toString();
		}

		@Override
		public Object fromString(String string)
		{
			return string;
		}
	};
	private final ObservableList<T> items;
	private final StringConverter<T> converter;
	private ComboBox<T> comboBox;

	/**
	 * Constructor.
	 *
	 * @param converter The converter used for the combo to display the items.
	 * @param items The items available to select from the combobox.
	 * @param propertyFunction A function giving the property to watch from the item.
	 */
	public ObjectComboBoxTableCell(StringConverter<T> converter, ObservableList<T> items, Function<T, Property> propertyFunction)
	{
		super(propertyFunction);
		InvalidationListener nameChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null)
				propertyFunction.apply(oldValue).removeListener(nameChangedListener);
			if(newValue != null)
				propertyFunction.apply(newValue).addListener(nameChangedListener);
		});
		this.items = items;
		//noinspection unchecked
		this.converter = converter == null ? (StringConverter<T>) defaultStringConverter : converter;
	}

	@Override
	public void startEdit()
	{
		if(!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable())
			return;

		if(comboBox == null)
			comboBox = createComboBox(this, items, converter);

		comboBox.getSelectionModel().select(getItem()); //Select the item in the cell

		super.startEdit();
		setText(null);
		setGraphic(comboBox);
	}

	/**
	 * Create the combobox for the selection.
	 *
	 * @param cell The cell where the combobox will be in.
	 * @param items The items available for the selection.
	 * @param converter The converter to display the items.
	 * @return The created combobox.
	 */
	private ComboBox<T> createComboBox(final Cell<T> cell, final ObservableList<T> items, final StringConverter<T> converter)
	{
		ComboBox<T> comboBox = new ComboBox<>(items);
		comboBox.setConverter(converter);
		Callback<ListView<T>, ListCell<T>> cellFactory = param -> new RefreshableListCell<>(getPropertyFunction());
		comboBox.setButtonCell(cellFactory.call(null));
		comboBox.setCellFactory(cellFactory);
		comboBox.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
			if(cell.isEditing())
				cell.commitEdit(newValue);
		});
		comboBox.setMaxWidth(Double.MAX_VALUE);
		comboBox.setEditable(false);
		return comboBox;
	}

	@Override
	public void cancelEdit()
	{
		super.cancelEdit();

		setText(converter.toString(getItem()));
		setGraphic(null);
	}

	@Override
	public void updateItem(T item, boolean empty)
	{
		super.updateItem(item, empty);
		if(isEmpty())
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			if(isEditing())
			{
				if(comboBox != null)
					comboBox.getSelectionModel().select(getItem());
				setText(null);
				setGraphic(comboBox);
			}
			else
			{
				setText(converter.toString(getItem()));
				setGraphic(null);
			}
		}
	}
}
