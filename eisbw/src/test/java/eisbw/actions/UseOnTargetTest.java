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
import jnibwapi.Unit;
import jnibwapi.types.TechType;
import jnibwapi.types.UnitType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;

public class UseOnTargetTest {

  private UseOnTarget action;
  private LinkedList<Parameter> params;
  private String techType;

  @Mock
  private JNIBWAPI bwapi;
  @Mock
  private Action act;
  @Mock
  private Unit unit;
  @Mock
  private UnitType unitType;
  @Mock
  private TechType tech;

  /**
   * Initialize mocks.
   */
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
    action = new UseOnTarget(bwapi);

    techType = "Stim Packs";

    params = new LinkedList<>();
    params.add(new Identifier("Stim Packs"));
    params.add(new Numeral(2));

    when(act.getParameters()).thenReturn(params);
    when(unit.getType()).thenReturn(unitType);
  }

  @Test
  public void isValid_test() {
    StarcraftAction spyAction = Mockito.spy(action);

    when(spyAction.getTechType(techType)).thenReturn(tech);

    assertTrue(spyAction.isValid(act));
    params.set(1, new Identifier("Bad"));
    assertFalse(spyAction.isValid(act));

    params.add(new Identifier("string"));
    assertFalse(action.isValid(act));
    params.remove(1);
    assertFalse(action.isValid(act));
    params.set(0, new Numeral(1));
    assertFalse(action.isValid(act));
    params.set(0, new Identifier("Hero Mojo"));
    assertFalse(action.isValid(act));
  }

  @Test
  public void canExecute_test() {
    StarcraftAction spyAction = Mockito.spy(action);

    when(spyAction.getTechType(techType)).thenReturn(tech);

    params.add(new Numeral(2));
    assertFalse(spyAction.canExecute(unit, act));
  }

  @Test
  public void execute_test() {
    params.set(0, new Identifier("null"));
    params.set(1, new Numeral(1));
    params.add(new Numeral(2));
    action.execute(unit, act);
    verify(unit).useTech(null, (Unit) null);
  }

  @Test
  public void toString_test() {
    assertEquals("abilityOnTarget(TechType, TargetId)", action.toString());
  }

}
