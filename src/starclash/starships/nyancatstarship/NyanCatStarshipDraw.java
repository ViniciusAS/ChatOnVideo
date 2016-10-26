package starclash.starships.nyancatstarship;

import starclash.gui.DrawAdaptor;
import starclash.gui.components.Component;
import starclash.starships.StarshipComponents;
import starclash.starships.StarshipDraw;
import starclash.starships.StarshipFactory;

/**
 *
 * @author samuel
 */
public class NyanCatStarshipDraw implements StarshipDraw{

    private final StarshipComponents components;
    private final StarshipFactory starship;
    
    public NyanCatStarshipDraw(StarshipFactory starship, StarshipComponents components) {
        this.starship = starship;
        this.components = components;
    }
    
    @Override
    public void draw(DrawAdaptor drawAdaptor) {
        if(!starship.isEnemy()){
            Component[] cps = components.getComponents();
            for (Component component : cps) drawAdaptor.drawComponent(component);             
        }else{
            Component[] cps = components.getComponents();
            for (Component component : cps) drawAdaptor.setRotate(component,components);    
        }
        
    }
    
}
