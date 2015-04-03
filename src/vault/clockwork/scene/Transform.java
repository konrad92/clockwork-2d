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

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/**
 * Base transformation information class.
 * Have grouping functionality for other transform instances.
 * Transform children can be iterable through from the transform instance.
 * @author Konrad Nowakowski https://github.com/konrad92
 */
public abstract class Transform implements Iterable<Transform> {
    /**
     * Parent of this transformable scene node.
     */
    private Transform parent;
    
    /**
     * Local matrix transform of this node.
     */
    public final Matrix4 local;
    
    /**
     * Children transform nodes.
     * Provide transformable instances grouping.
     */
    public final Array<Transform> children;
    
    /**
     * Transform constructor.
     */
    public Transform() {
        this(null);
    }
    
    /**
     * Transform constructor.
     * @param parent Parent transform to assign with.
     */
    public Transform(Transform parent) {
        this.local = new Matrix4();
        this.children = new Array<>();
        this.setParent(parent);
    }
    
    /**
     * Combined world matrix with the parents.
     * An absolute transform matrix to the world. Opposite of local one.
     * @return Combined matrix with the parents.
     */
    public Matrix4 world() {
        if(this.parent == null) {
            return this.local;
        }
        
        return this.local.cpy().mulLeft(parent.world());
    }
    
    /**
     * Bind new parent transform to this one.
     * @param newParent New parent transform.
     * @return Assigned parent transform.
     */
    public final Transform setParent(Transform newParent) {
        if(this != newParent && this.parent != newParent) {
            // remove child from old parent
            if(this.parent != null) {
                this.parent.children.removeValue(this, true);
            }
            
            // add instance to the new parent
            this.parent = newParent;
            if(this.parent != null) {
                this.parent.children.add(this);
            }
        }
        
        return this.parent;
    }
    
    /**
     * Retrieve parent in use.
     * @return The parent transform.
     */
    public final Transform getParent() {
        return this.parent;
    }
    
    /**
     * Transform children iterator.
     * @return Array iterator of the children set.
     */
    @Override
    public final Iterator<Transform> iterator() {
        return children.iterator();
    }
}
