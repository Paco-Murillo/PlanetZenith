package mx.jfml;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Contacto implements ContactListener {


    public boolean personajeSuelo=false;



    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getUserData() != null && fa.getUserData().equals("sensorPie")) {
            personajeSuelo = true;

        }
        if(fb.getUserData() != null && fb.getUserData().equals("sensorPie")) {
            personajeSuelo = true;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("sensorEnemigo")) {
            fa.getBody().setAwake(true);

        }
        if(fb.getUserData() != null && fb.getUserData().equals("sensorEnemigo")) {
            fb.getBody().setAwake(true);
        }


    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if(fa.getUserData() != null && fa.getUserData().equals("sensorPie")) {
            personajeSuelo = false;
        }
        if(fb.getUserData() != null && fb.getUserData().equals("sensorPie")) {
            personajeSuelo = false;
        }

        if(fa.getUserData() != null && fa.getUserData().equals("sensorEnemigo")) {

            fa.getBody().setAwake(false);
            fa.getBody().setLinearVelocity(0,0);

        }
        if(fb.getUserData() != null && fb.getUserData().equals("sensorEnemigo")) {
            fb.getBody().setAwake(false);
            fb.getBody().setLinearVelocity(0,0);
        }


    }

    public boolean isPersonajeSuelo() { return personajeSuelo; }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
