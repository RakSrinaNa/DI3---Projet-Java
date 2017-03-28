package fr.polytech.projectjava.utils;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Basic scheme for a JavaFX application.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public abstract class ApplicationBase extends Application
{
	private Stage stage;
	
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
		if(shouldDisplayAtStart())
		{
			stage.show();
			if(getOnStageDisplayed() != null)
				this.getOnStageDisplayed().accept(stage);
		}
	}
	
	private void setIcon(Image icon)
	{
		this.stage.getIcons().clear();
		this.stage.getIcons().add(icon);
		if(isMac())
			com.apple.eawt.Application.getApplication().setDockIconImage(SwingFXUtils.fromFXImage(icon, null));
	}
	
	private boolean isMac()
	{
		return System.getProperty("os.name").toLowerCase().contains("mac");
	}
	
	public void preInit() throws Exception{}
	
	public Image getIcon()
	{
		return null;
	}
	
	public boolean shouldDisplayAtStart()
	{
		return true;
	}
	
	public Scene buildScene(Stage stage)
	{
		return new Scene(createContent(stage));
	}
	
	public abstract String getFrameTitle();
	
	public abstract Consumer<Stage> getStageHandler();
	
	public abstract Consumer<Stage> getOnStageDisplayed() throws Exception;
	
	public abstract Parent createContent(Stage stage);
	
	public Stage getStage()
	{
		return stage;
	}
}
