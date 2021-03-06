package starclash.gui.swing;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import starclash.gui.KeysListenerAdaptor;
import static starclash.gui.KeysListenerAdaptor.KEY_PRESSED_PROCCESS_DELAY_MS;


public class SwingKeysListener implements KeysListenerAdaptor{
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private final List<KeyListener> keyObservers = new ArrayList<>();
    
    @Override
    public void addKeyListener(Key key, KeyListener keyListener) {
        keyListener.setKey(key);
        addKeyListener(keyListener);
    }
    
    @Override
    public void addKeyListener(KeyListener keyListener) {
        keyObservers.add(keyListener);
    }    

    @Override
    public void removeKeyListener(KeyListener keyListener) {
        keyObservers.remove(keyListener);
    }

    @Override
    public void clearListeners() {
        keyObservers.clear();
    }

    @Override
    public void notifyObservers(Key key) {
        for (KeyListener keyListener : keyObservers) {
            if ( keyListener.getKey() == key ){
                keyListener.pressed();
            }
        }
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    public class SwingKeyListener 
//        extends TimerTask
        implements java.awt.event.KeyListener, Runnable
    {

//        private final Map<Key,Timer> timers = new HashMap<>();

//        private final Timer timer = new Timer();
        
        private final Thread thread;
        private long time = 0;
        
        private final Set<Key> keys = new HashSet<>();
        
        public SwingKeyListener(){
//            timer.scheduleAtFixedRate(this, 1, KEY_PRESSED_PROCCESS_DELAY_MS);
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while (true){
                if (System.currentTimeMillis()-time >= KEY_PRESSED_PROCCESS_DELAY_MS) {
                    time = System.currentTimeMillis();
                    synchronized (keys){
                        for (Key key : keys) {
                            notifyObservers(key);
                        }
                    }
                }
            }
        }
        
//        private void startTimer(Key key) {
//            if ( timers.get(key) != null )
//                return;
//            Timer timer = new Timer();
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
//                    notifyObservers( key );
//                }
//            };
//            task.run();
//            timer.scheduleAtFixedRate(task, 1, KEY_PRESSED_PROCCESS_DELAY_MS);
//            timers.put(key, timer);            
//        }
        
//        private void endTimer(Key key){
//            Timer timer = timers.get(key);
//            if ( timer != null )
//                timer.cancel();
//            timers.remove(key);
//        }
        
        private Key swingEventToKey(KeyEvent ke){
            switch ( ke.getKeyCode() ){
                case KeyEvent.VK_LEFT:  return Key.KEY_LEFT;
                case KeyEvent.VK_RIGHT: return Key.KEY_RIGHT;
                case KeyEvent.VK_UP:    return Key.KEY_UP;
                case KeyEvent.VK_DOWN:  return Key.KEY_DOWN;
                   
                case KeyEvent.VK_W:     return Key.KEY_W;
                case KeyEvent.VK_A:     return Key.KEY_A;
                case KeyEvent.VK_S:     return Key.KEY_S;
                case KeyEvent.VK_D:     return Key.KEY_D;
                
                case KeyEvent.VK_G:     return Key.KEY_G;
                case KeyEvent.VK_T:     return Key.KEY_T;
                   
                case KeyEvent.VK_SPACE: return Key.KEY_SPACE;
                case KeyEvent.VK_ENTER: return Key.KEY_ENTER;
            }
            return null;
        }
        
        @Override public void keyTyped(KeyEvent ke) {}
        
        @Override public void keyReleased(KeyEvent ke)
        {
            Key key = swingEventToKey(ke);

            if ( key == null ){
                return;
            }

            // key pressed listener
            synchronized (keys){
                keys.remove(key);
            }
            
//            endTimer( swingEventToKey(ke) );

            // clicked listener
            for (int i = 0; i < keyObservers.size(); i++) {
                if ( keyObservers.get(i).getKey() == key ){
                    keyObservers.get(i).clicked();
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent ke) {
            Key k = swingEventToKey(ke);
            if ( k != null )
                synchronized (keys){
                    keys.add(k);
                }
//                startTimer( k );
        }
        
    }
    
    
}
