package application;

import javafx.scene.Node;
import javafx.scene.layout.Region;

import java.util.List;

public abstract class Sprite extends Region {

    private static int idCounter;

    Vector2D location;
    Vector2D velocity;

    Node view;

    int id;

    // view dimensions
    double width;
    double height;
    double centerX;
    double centerY;
    double radius;


    double angle;

    Layer layer = null;

    public Sprite(Layer layer, Vector2D location, Vector2D velocity, double width, double height) {

        this.id = idCounter++;

        this.layer = layer;

        this.location = location;
        this.velocity = velocity;
        this.width = width;
        this.height = height;
        this.centerX = width / 2;
        this.centerY = height / 2;

        this.view = createView();

        setPrefSize(width, height);

        // add view to this node
        getChildren().add( view);

        // add this node to layer
        layer.getChildren().add( this);

    }

    public abstract Node createView();
    public abstract void updateVelocity(List<Boid> allBoids);

    public void applyForce(Vector2D force) {
        //acceleration.add(force);
    }

    public void move() {

        // set velocity depending on acceleration
        //velocity.add(acceleration);
        //velocity.add(Settings.SPRITE_SPEED, Settings.SPRITE_SPEED);

        // limit velocity to max speed
        //velocity.limit(Settings.SPRITE_SPEED);

        // change location depending on velocity
        location.add(velocity);

        // angle: towards velocity (ie target)
        angle = velocity.heading2D();

        if (this.location.x > this.layer.getWidth()) {
            this.location.x = 0;
        }
        if (this.location.x < 0) {
            this.location.x = this.layer.getWidth();
        }
        if (this.location.y > this.layer.getHeight()) {
            this.location.y = 0;
        }
        if (this.location.y < 0) {
            this.location.y = this.layer.getHeight();
        }

        // clear acceleration
        //acceleration.multiply(0);
    }

    /**
     * Move sprite towards target
     */
    /*public void seek(Vector2D target) {

        Vector2D desired = Vector2D.subtract(target, location);

        // The distance is the magnitude of the vector pointing from location to target.

        double d = desired.magnitude();
        desired.normalize();

        // If we are closer than 100 pixels...
        if (d < Settings.SPRITE_SLOW_DOWN_DISTANCE) {

            // ...set the magnitude according to how close we are.
            double m = Utils.map(d, 0, Settings.SPRITE_SLOW_DOWN_DISTANCE, 0, Settings.SPRITE_SPEED);
            desired.multiply(m);

        }
        // Otherwise, proceed at maximum speed.
        else {
            desired.multiply(Settings.SPRITE_SPEED);
        }

        // The usual steering = desired - velocity
        Vector2D steer = Vector2D.subtract(desired, velocity);
        steer.limit(Settings.SPRITE_MAX_FORCE);

        applyForce(steer);

    }*/

    /**
     * Update node position
     */
    public void display() {

        relocate(location.x - centerX, location.y - centerY);

        setRotate(Math.toDegrees( angle));

    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation( double x, double y) {
        location.x = x;
        location.y = y;
    }

    public void setLocationOffset( double x, double y) {
        location.x += x;
        location.y += y;
    }

}