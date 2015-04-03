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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Base scene actor.
 * Abstract class of the scene actor.
 * Inherit your own actors from this one.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public abstract class Actor extends Transform implements Entity {
    /**
     * Actor tag.
     * Group actors type by the tag.
     */
    private ActorTag tag;
    
    /**
     * Actor ID on the scene.
     * Should be unique ID.
     */
    public final int id;
    
    /**
     * Active actor can be updated and rendered by the actors group.
     */
    public boolean active = true;
    
    /**
     * Visible actor can be rendered by the actors group.
     * To render visible actor they must be also active.
     */
    public boolean visible = true;
    
    /**
     * Actor constructor.
     * @param id Unique ID of the actor.
     */
    public Actor(int id) {
        this(id, null);
    }
    
    /**
     * Actor constructor.
     * @param id Unique ID of the actor.
     * @param tag Grouping tag of the actor.
     */
    public Actor(int id, ActorTag tag) {
        this.id = id;
        this.tag = tag;
    }
    
    /**
     * Setter for the actor's tag.
     * Removes actor reference from the older tag and adds to the new one.
     * @param newTag New actor tag.
     * @return Assigned tag with the actor.
     */
    public ActorTag setTag(ActorTag newTag) {
        if(this.tag != newTag) {
            // remove actor from old tag
            if(this.tag != null) {
                this.tag.actors.removeValue(this, true);
            }
            
            // assign actor with the new tag
            this.tag = newTag;
            if(this.tag != null) {
                this.tag.actors.add(this);
            }
        }
        
        return this.tag;
    }
    
    /**
     * Retrieve tag assigned with this actor.
     * @return The assigned tag.
     */
    public ActorTag getTag() {
        return this.tag;
    }
    
    /**
     * Unassign destroyed actor with the tag.
     * Remove actor from the tag when destroy event occurs.
     */
    @Override
    public void destroy() {
        if(this.tag != null) {
            this.tag.actors.removeValue(this, true);
        }
    }
    
    /**
     * Render actor coords.
     * @param gizmos Enabled gizmo.
     */
    @Override
    public void debug(ShapeRenderer gizmos) {
        gizmos.setTransformMatrix(this.world());
        gizmos.begin(ShapeRenderer.ShapeType.Line);
        gizmos.setColor(Color.WHITE);
        gizmos.rect(-.5f, -.5f, .5f, .5f);
        gizmos.setColor(Color.RED);
        gizmos.line(0.f, 0.f, 1.f, 0.f);
        gizmos.setColor(Color.BLUE);
        gizmos.line(0.f, 0.f, 0.f, 1.f);
        gizmos.end();
    }
}
