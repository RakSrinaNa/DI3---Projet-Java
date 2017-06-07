package fr.polytech.projectjava.utils.jfx;

import javafx.css.PseudoClass;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;
import java.util.function.Function;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class StringTextFieldTableCell<T> extends TextFieldTableCell<T, String>
{
	private final Function<T, Boolean> validateFunction;

	/**
	 * Constructor.
	 *
	 * @param validateFunction The function that tell if the item is in a valid state.
	 */
	public StringTextFieldTableCell(Function<T, Boolean> validateFunction)
	{
		super(new DefaultStringConverter());
		this.validateFunction = validateFunction;
	}

	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item != null ? item.trim() : null, empty);
		//noinspection unchecked
		getTableRow().pseudoClassStateChanged(PseudoClass.getPseudoClass("invalidState"), !empty && getTableRow().getItem() != null && !validateFunction.apply((T) getTableRow().getItem()));
	}
}
