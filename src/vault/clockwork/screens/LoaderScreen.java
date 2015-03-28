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
package vault.clockwork.screens;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.Gdx.gl;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;

/**
 * Loading process screen.
 * Screen that performs async assets loading.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public class LoaderScreen implements Screen {
    /**
     * Sprite batch.
     */
    private SpriteBatch spriteBatch;
    
    /**
     * Single machine texture.
     */
    private Texture machineTexture;
    
    /**
     * Gears texture regions.
     */
    private TextureRegion[] gears;
    
    /**
     * Tick frame.
     */
    private float frame = 0.f;
    
    /**
     * Performed on screen change at this one.
     * Load loader resources for single session.
     */
    @Override
    public void show() {
        // create the spritebatch
        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(new Matrix4().setToOrtho2D(0.f, 0.f, 400.f, 300.f));
        
        // load texture from file
        machineTexture = new Texture(Gdx.files.internal("assets/machine.png"));
        
        // create sprite texture regions
        gears = new TextureRegion[]{
            new TextureRegion(machineTexture,  0, 0, 41, 41),
            new TextureRegion(machineTexture, 41, 0, 27, 27),
        };
    }

    /**
     * Frame update.
     * Update asset manager and render some loader screen stuff.
     * @param delta 
     */
    @Override
    public void render(float delta) {
        // clear target buffer
        gl.glClearColor(0.f, 0.f, 0.f, 1.f);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        // calculate screen center offset
        float centerx = (float)(200 - machineTexture.getWidth()/2);
        
        // draw machine sprite
        spriteBatch.begin();
        spriteBatch.draw(gears[0], centerx, 64.f, 20.5f, 20.5f, 41.f, 41.f, 1.f, 1.f, frame);
        spriteBatch.draw(gears[1], centerx+28.f, 48.f, 13.5f, 13.5f, 27.f, 27.f, 1.f, 1.f, -frame);
        spriteBatch.end();
        
        // tick the frame
        frame += 100.f*delta;
    }

    /**
     * Handle window resizing.
     * Performed when window client resized.
     * @param width
     * @param height 
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Handle pause event.
     * Performed on window focus lost.
     */
    @Override
    public void pause() {
    }

    /**
     * Handle resume event.
     * Performed on window focus gain.
     */
    @Override
    public void resume() {
    }

    /**
     * Performed when screen were changed.
     * Dispose all resources of the loader.
     */
    @Override
    public void hide() {
        this.dispose();
    }

    /**
     * Dispsoe resources.
     * Release all unused resources to bypass the memoryleaks.
     */
    @Override
    public void dispose() {
        machineTexture.dispose();
        spriteBatch.dispose();
    }
}
