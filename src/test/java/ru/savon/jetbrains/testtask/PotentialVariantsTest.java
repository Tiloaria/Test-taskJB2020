package ru.savon.jetbrains.testtask;

import org.junit.Test;
import ru.savon.jetbrains.testtask.exceptions.IncorrectDataException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static ru.savon.jetbrains.testtask.PotentialVariants.Train;

public class PotentialVariantsTest {
    @Test
    public void addZeroElements() {
        List<Train> list = new ArrayList<>();
        PotentialVariants variants = new PotentialVariants(list);
        assertEquals(0, variants.getBestSalary());
    }

    @Test
    public void addOneElement() throws IncorrectDataException {
        List<Train> list = new ArrayList<>();
        list.add(new Train(1, 1, 1, 1));
        PotentialVariants variants = new PotentialVariants(list);
        assertEquals(1, variants.getBestSalary());
    }

    @Test
    public void threeParallelTrains() throws IncorrectDataException {
        List<Train> list = new ArrayList<>();
        list.add(new Train(1, 1, 3, 1));
        list.add(new Train(2, 2, 2, 2));
        list.add(new Train(3, 3, 1, 3));
        PotentialVariants variants = new PotentialVariants(list);
        assertEquals(3, variants.getBestSalary());
    }

    @Test
    public void shuffledStartTimes() throws IncorrectDataException {
        List<Train> list = new ArrayList<>();
        list.add(new Train(1, 4, 3, 3));
        list.add(new Train(2, 2, 2, 2));
        list.add(new Train(3, 1, 2, 3));
        list.add(new Train(4, 3, 2, 2));
        PotentialVariants variants = new PotentialVariants(list);
        assertEquals(6, variants.getBestSalary());
    }

    @Test(expected = IncorrectDataException.class)
    public void incorrectTrainData() throws IncorrectDataException {
        new Train(1, -1, 3, 3);
    }
}