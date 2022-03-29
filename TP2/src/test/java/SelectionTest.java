import Implementations.Selection.EliteSelection;
import entities.Individual;
import entities.Item;
import interfaces.Selection;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class SelectionTest {

    @Test
    public void eliteSectionTest() {

        List<Item> items = Arrays.asList(
                new Item(10, 4),
                new Item(5, 8),
                new Item(20, 6),
                new Item(12, 10),
                new Item(25, 2)
        );

        List<Individual> individuals = Arrays.asList(
                new Individual(new boolean[]{false, false, true, false, true}),  // benefit: 8 weight: 45
                new Individual(new boolean[]{true, false, true, false, true}),   // benefit: 12 weight: 55
                new Individual(new boolean[]{false, true, false, false, true}),  // benefit: 10 weight: 30
                new Individual(new boolean[]{false, false, true, true, true}),   // benefit: 18 weight: 57
                new Individual(new boolean[]{true, false, false, false, true}),  // benefit: 6 weight: 35
                new Individual(new boolean[]{false, false, true, false, false})  // benefit: 6 weight: 20
        );

        Selection selection = new EliteSelection();

        List<Individual> newGeneration = selection.select(individuals);

        List<Individual> expectedGeneration = Arrays.asList(
                new Individual(new boolean[]{false, true, false, false, true}),
                new Individual(new boolean[]{false, false, true, false, false}),
                new Individual(new boolean[]{true, false, false, false, true})
        );

        Assert.assertEquals(newGeneration, expectedGeneration);
    }
}
