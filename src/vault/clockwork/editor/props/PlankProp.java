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
package vault.clockwork.editor.props;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import vault.clockwork.actors.PlankActor;
import vault.clockwork.editor.PropActor;
import vault.clockwork.editor.PropSerialized;

/**
 * General plank editor prop.
 * @author Agnieszka Makowska https://github.com/Migemiley
 */
public class PlankProp extends PropSerialized {
	/**
	 * Szerokosc i wysokosc planka.
	 * Dla konstruktora {@link PlankActor#PlankActor(int, float, float)}, gdzie
	 * x/y to odpowiednio width/height.
	 */
	public float width = 50, height = 256;
	
	/**
	 * Obrot aktora.
	 */
	public float angle = 0;
	
	/**
	 * Szybkosc obrotu aktora.
	 */
	public float rotate_speed = 0;
	
	/**
	 * Wysokosc poruszania sie deski.
	 */
	public float move_height = 200;
	
	/**
	 * Kierunek poruszania sie deski.
	 */
	public float move_direction_x = 0, move_direction_y = 1;
	
	/**
	 * Ctor.
	 */
	public PlankProp() {
		this.layer = 2;
	}
	
	/**
	 * @param gizmo 
	 */
	@Override
	public void draw(ShapeRenderer gizmo) {
		Matrix4 transform = new Matrix4();
		transform.translate(position.x, position.y, 0);
		transform.rotate(0, 0, 1, angle);
		
		gizmo.setTransformMatrix(transform);
		gizmo.rect(-width, -height, width*2, height*2);
		gizmo.setTransformMatrix(new Matrix4());
		gizmo.setColor(Color.CYAN);
		gizmo.line(position, position.cpy().add(
			new Vector2(move_direction_x, move_direction_y).nor().scl(64.f)
		));
		gizmo.setColor(Color.YELLOW);
	}

	/**
	 * PlankActor class.
	 * @return 
	 */
	@Override
	public Class<? extends PropActor> getActorClass() {
		return PlankActor.class;
	}
}
