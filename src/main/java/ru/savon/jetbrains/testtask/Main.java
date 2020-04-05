package ru.savon.jetbrains.testtask;

import ru.savon.jetbrains.testtask.exceptions.IncorrectDataException;

import java.util.*;

import static ru.savon.jetbrains.testtask.PotentialVariants.Train;

public class Main {
    public void main(String[] args) {
        List<Train> trains = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            try {
                int trainNum = scanner.nextInt();
                int startTime = scanner.nextInt();
                int offloadPeriod = scanner.nextInt();
                int amountOfMoney = scanner.nextInt();
                Train newTrain = new Train(trainNum, startTime, offloadPeriod, amountOfMoney);
                trains.add(newTrain);
            } catch (IncorrectDataException e) {
                e.printStackTrace();
            }
        }
        System.out.println(new PotentialVariants(trains).getBestSalary());
    }
}
