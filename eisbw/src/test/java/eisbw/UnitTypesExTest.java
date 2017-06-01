package eisbw;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import jnibwapi.types.UnitType;
import jnibwapi.types.UnitType.UnitTypes;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UnitTypesExTest {

  @Mock
  private UnitType unitType;
  
  @Before
  public void start() {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void test() {
    when(unitType.getName()).thenReturn("none");
    assertFalse(UnitTypesEx.isMineralField(unitType));
    assertFalse(UnitTypesEx.isResourceType(unitType));
    assertFalse(UnitTypesEx.isVespeneGeyser(unitType));
  }
  
  @Test
  public void mineralfield_test() {
    when(unitType.getID()).thenReturn(UnitTypes.Resource_Mineral_Field.getID());
    assertTrue(UnitTypesEx.isMineralField(unitType));
    when(unitType.getID()).thenReturn(UnitTypes.Resource_Mineral_Field_Type_2.getID());
    assertTrue(UnitTypesEx.isMineralField(unitType));
    when(unitType.getID()).thenReturn(UnitTypes.Resource_Mineral_Field_Type_3.getID());
    assertTrue(UnitTypesEx.isMineralField(unitType));
  }
  
  @Test
  public void resourceType_test() {
    when(unitType.isRefinery()).thenReturn(true);
    assertTrue(UnitTypesEx.isResourceType(unitType));
    when(unitType.isRefinery()).thenReturn(false);
    when(unitType.getID()).thenReturn(UnitTypes.Resource_Mineral_Field.getID());
    assertTrue(UnitTypesEx.isResourceType(unitType));
    when(unitType.getID()).thenReturn(UnitTypes.Resource_Vespene_Geyser.getID());
    assertTrue(UnitTypesEx.isResourceType(unitType));
  }

}
