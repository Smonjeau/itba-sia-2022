import Implementations.Pairing.SimplePairing;
import Implementations.Pairing.UniformPairing;
import entities.Individual;
import entities.Item;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class PairingTest {

    @Test
    public void simplePairing() {

        List<Item> items = Arrays.asList(
            new Item(10, 4),
            new Item(5, 8),
            new Item(20, 6),
            new Item(12, 10),
            new Item(25, 2)
        );

        Individual.setMutationChance(0.1);
        Individual i1 = new Individual(new boolean[]{false, false, true, true, false}, items);
        Individual i2 = new Individual(new boolean[]{true, false, false, true, true}, items);

        Individual[] newIndividual = new SimplePairing().matchIndividuals(i1, i2);

        for (int i = 0; i < i1.getBag().length; i++) {
            Assert.assertTrue((newIndividual[0].getBag()[i] == i1.getBag()[i] ^ newIndividual[0].getBag()[i] == i2.getBag()[i]) || i1.getBag()[i]== i2.getBag()[i]);
            Assert.assertTrue((newIndividual[1].getBag()[i] == i1.getBag()[i] ^ newIndividual[1].getBag()[i] == i2.getBag()[i]) || i1.getBag()[i]== i2.getBag()[i]);
        }
    }

    @Test
    public void uniformPairing() {

        List<Item> items = Arrays.asList(
                new Item(10, 4),
                new Item(5, 8),
                new Item(20, 6),
                new Item(12, 10),
                new Item(25, 2)
        );

        Individual.setMutationChance(0.1);
        Individual i1 = new Individual(new boolean[]{false, false, true, true, false}, items);
        Individual i2 = new Individual(new boolean[]{true, false, false, true, true}, items);

        Individual[] newIndividual = new UniformPairing().matchIndividuals(i1, i2);

        for (int i = 0; i < i1.getBag().length; i++) {
            Assert.assertTrue(newIndividual[0].getBag()[i] == i1.getBag()[i] || newIndividual[1].getBag()[i] == i1.getBag()[i]);
            Assert.assertTrue(newIndividual[0].getBag()[i] == i2.getBag()[i] || newIndividual[1].getBag()[i] == i2.getBag()[i]);
        }
    }
}
