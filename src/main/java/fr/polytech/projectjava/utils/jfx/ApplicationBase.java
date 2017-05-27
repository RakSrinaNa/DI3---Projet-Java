package fr.polytech.projectjava.utils.jfx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Basic scheme for a JavaFX application.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public abstract class ApplicationBase extends Application
{
	private Stage stage;
	
	/**
	 * Starting method of JavaFX.
	 *
	 * @param stage The stage to build.
	 *
	 * @throws Exception If an error occurred.
	 */
	public void start(Stage stage) throws Exception
	{
		this.stage = stage;
		preInit();
		Scene scene = buildScene(stage);
		stage.setTitle(this.getFrameTitle());
		stage.setScene(scene);
		stage.sizeToScene();
		if(getIcon() != null)
			setIcon(getIcon());
		if(getStageHandler() != null)
			this.getStageHandler().accept(stage);
		stage.show();
		if(getOnStageDisplayed() != null)
			this.getOnStageDisplayed().accept(stage);
	}
	
	/**
	 * Method called before everything.
	 *
	 * @throws Exception If an error occurred.
	 */
	public void preInit() throws Exception{}
	
	/**
	 * Build the scene for the stage.
	 *
	 * @param stage The stage of the scene.
	 *
	 * @return The built scene.
	 */
	public Scene buildScene(Stage stage)
	{
		return new Scene(createContent(stage));
	}
	
	/**
	 * Get the title of the window.
	 *
	 * @return The window title.
	 */
	public abstract String getFrameTitle();
	
	/**
	 * Get the icon of the window.
	 *
	 * @return The window icon, null if none.
	 */
	@SuppressWarnings("SameReturnValue")
	public Image getIcon()
	{
		return null;
	}
	
	/**
	 * Set the window icon.
	 *
	 * @param icon The icon to set.
	 */
	private void setIcon(Image icon)
	{
		this.stage.getIcons().clear();
		this.stage.getIcons().add(icon);
	}
	
	/**
	 * Method called before displaying the frame.
	 *
	 * @return The consumer to call.
	 */
	public abstract Consumer<Stage> getStageHandler();
	
	/**
	 * Method called when the stage has been displayed.
	 *
	 * @return The consumer to call.
	 *
	 * @throws Exception If an error occurred.
	 */
	@SuppressWarnings("RedundantThrows")
	public abstract Consumer<Stage> getOnStageDisplayed() throws Exception;
	
	/**
	 * Create the content to put in the scene.
	 *
	 * @param stage The stage of the scene.
	 *
	 * @return The root element of the scene.
	 */
	public abstract Parent createContent(Stage stage);
	
	/**
	 * Get the stage of this application.
	 *
	 * @return The stage.
	 */
	public Stage getStage()
	{
		return stage;
	}
}
