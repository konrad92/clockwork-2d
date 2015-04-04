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
package vault.clockwork.scene;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Entity common interface.
 * Interface that provides update and render abstract methods.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public interface Entity extends Disposable {
    /**
     * Entity on create event dispatcher.
     * Performed event on entity creation.
     * Must be called manually by the entity manager system.
     */
    public void create();
    
    /**
     * Update the entity by given delta time.
     * @param delta Delta time between the frames.
     */
    public void update(float delta);
    
    /**
     * Render the entity.
     * @param batch Renderer batching interface provided by the engine.
     */
    public void render(Batch batch);
    
    /**
     * Entity on destroy event dispatcher.
     * Performed event on entity destroy.
     * Must be called manually by the entity manager system.
     */
    public default void destroy() {
        // dummy method
    }
    
    /**
     * Render debug information about the entity.
     * Use it to render debug information such as bboxes or coords.
     * @param gizmos Shape rendering interface for gizmos.
     */
    public default void debug(ShapeRenderer gizmos) {
        // dummy method
    }
}
