/*
 * The MIT License
 *
 * Copyright 2015 Konrad Nowakowski https://github.com/konrad92.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package vault.clockwork;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import vault.clockwork.screens.GameScreen;
import vault.clockwork.screens.LoaderScreen;
import vault.clockwork.screens.PreviewScreen;
import vault.clockwork.systems.PhysicsWorldSystem;
import vault.clockwork.systems.SceneRendererSystem;
import vault.clockwork.systems.SceneWorldSystem;

/**
 * Game main controller.
 * @author Konrad Nowakowski [http://github.com/konrad92]
 */
public class Game extends com.badlogic.gdx.Game {
    /**
     * Game main assets manager.
     * Manage game resources such as textures, sounds, musics etc. globally.
     */
    static public AssetManager assets;
    
    /**
     * Scene world system.
     */
    static public SceneWorldSystem world;
    
    /**
     * Scene renderer system.
     */
    static public SceneRendererSystem renderer;
    
    /**
     * Physics world system.
     * Simulates physics world.
     */
    static public PhysicsWorldSystem physics;
    
    /**
     * Main camera transformable object.
     */
    static public Camera mainCamera;
    
    /**
     * Initialize loader for the new game screen.
     * Automate the loader initialization for next game screen.
     * @param next Screen to go.
     */
    static public void setGameScreen(GameScreen next) {
        ((Game)Gdx.app.getApplicationListener()).setScreen(new LoaderScreen(next));
    }
    
    /**
     * Perform systems actions.
     * Update and render entities, update physics world.
     * @param delta Delta time to update the entities.
     */
    static public void performSystemsJob(float delta) {
        // update frame
        Game.physics.update(delta);
        Game.world.update(delta);
        
        // render frame
        Game.renderer.prerender();
        Game.renderer.render();
        Game.renderer.debug();
        Game.physics.debug();
    }
    
    /**
     * Performed after application succeed creation.
     * Initialize game global resources, loaders and scenes.
     */
    @Override
    public void create() {
        // create asset manager
        assets = new AssetManager();
        
        // create scene systems
        world = new SceneWorldSystem();
        renderer = new SceneRendererSystem(world.root);
        physics = new PhysicsWorldSystem();
        
        // fetch for main camera
        mainCamera = renderer.camera;
        
        // prepare startup screen
        //this.setScreen();
        this.setScreen(new LoaderScreen(new PreviewScreen()));
    }
    
    /**
     * Perform game screen rendering.
     */
    @Override
    public void render() {
        super.render();
    }
    
    /**
     * Dispose resources.
     * Release all game resources and assets.
     */
    @Override
    public void dispose() {
        super.dispose();
        
        assets.dispose();
    }
}
