package eisbw.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eis.iilang.Action;
import eis.iilang.Identifier;
import eis.iilang.Numeral;
import eis.iilang.Parameter;
import jnibwapi.JNIBWAPI;
import jnibwapi.Position;
import jnibwapi.Unit;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class MoveTest {

  private Move action;
  private LinkedList<Parameter> params;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Action act;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    action = new Move(bwapi);
    
    params = new LinkedList<>();
    params.add(new Numeral(1));
    params.add(new Numeral(2));
    
    when(unit.isBeingConstructed()).thenReturn(false);
    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    assertTrue(action.isValid(act));
    params.set(0, new Identifier("Not Working"));
    assertFalse(action.isValid(act));
    params.set(0, new Numeral(1));
    params.set(1, new Identifier("Not Working"));
    assertFalse(action.isValid(act));
    params.set(1, new Numeral(2));
    params.add(new Numeral(10));
    assertFalse(action.isValid(act));
  }
  
  @Test
  public void canExecute_test() {
    when(unitType.isCanMove()).thenReturn(true);
    assertTrue(action.canExecute(unit, act));
  }
  
  @Test
  public void execute_test() {
    action.execute(unit, act);
    verify(unit).move(new Position(1, 2, Position.PosType.BUILD),false);
  }
  
  @Test
  public void toString_test() {
    assertEquals("move(x, y)", action.toString());
  }

}
