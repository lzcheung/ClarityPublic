package test.martin;

import clarity.graphics.entity.Entity;
import clarity.graphics.entity.Projectile;
import clarity.graphics.entity.particle.Particle;
import clarity.state.Level;
import clarity.state.State;
import clarity.state.StateManager;
import java.util.ArrayList;
import org.junit.Test;

public class TestState {

  @Test
  public void testConstructor() {
    StateManager stateManager = new StateManager();
    State currentState = new Level(stateManager);

    assert (currentState instanceof Level);
  }

  @Test
  public void testGetEntities() {
    ArrayList<Entity> entities = null;
    entities = State.getEntities();

    assert (entities != null);
  }

  @Test
  public void testGetProjectiles() {
    ArrayList<Projectile> projectiles = null;
    projectiles = State.getProjectiles();

    assert (projectiles != null);
  }

  @Test
  public void testGetParticles() {
    ArrayList<Particle> particles = null;
    particles = State.getParticles();

    assert (particles != null);
  }


}
