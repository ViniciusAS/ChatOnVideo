package starclash.starships.workaroundstarship;


import starclash.gui.DrawAdaptor;
import starclash.gui.components.Component;
import starclash.starships.StarshipComponents;
import starclash.starships.StarshipDraw;
import starclash.starships.StarshipFactory;

/**
 * @author Vinicius Santos
 */
public class WorkAroundStarshipDraw implements StarshipDraw {

    private final StarshipComponents components;
    private final StarshipFactory starship;

    public WorkAroundStarshipDraw(StarshipComponents components, StarshipFactory starship) {
        this.components = components;
        this.starship = starship;
    }

    @Override
    public void draw(DrawAdaptor drawAdaptor) {
        Component[] cps = components.getComponents();
        for (Component component : cps) {
            drawAdaptor.drawComponent(component);
        }
    }

}
