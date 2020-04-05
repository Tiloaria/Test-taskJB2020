package ru.savon.jetbrains.testtask;

import ru.savon.jetbrains.testtask.exceptions.IncorrectDataException;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class PotentialVariants {
    private static class EndVariant {
        int myEndTime;
        int myAmountOfMoney;

        public EndVariant(int endTime, int amountOfMoney) {
            myEndTime = endTime;
            myAmountOfMoney = amountOfMoney;
        }
    }

    static class Train {
        int myTrainNum;
        int myStartTime;
        int myOffloadPeriod;
        int myAmountOfMoney;

        public Train(int trainNum, int startTime, int offloadPeriod, int amountOfMoney) throws IncorrectDataException {
            myTrainNum = trainNum;
            myStartTime = startTime;
            myOffloadPeriod = offloadPeriod;
            myAmountOfMoney = amountOfMoney;
            if (myTrainNum <= 0 || myStartTime <= 0 || myOffloadPeriod <= 0 || myAmountOfMoney <= 0) {
                throw new IncorrectDataException("Incorrect format of data. Expected all numbers to be positive.");
            }
        }

        public int getEndTime() {
            return myStartTime + myOffloadPeriod;
        }
    }

    TreeSet<EndVariant> bestIncomeToTime = new TreeSet<>(Comparator.comparingInt(c -> c.myEndTime));

    /**
     * Create structure which count information about the best salary for every train
     * it sorts list of trains and adds zero element for dynamic base
     * in bestFinish it keeps the rule,
     * that every element is better that the other one by the endTime or by the amountOfMoney
     *
     * @param trains list of incoming trains
     */
    public PotentialVariants(List<Train> trains) {
        bestIncomeToTime.add(new EndVariant(0, 0));
        trains.sort(Comparator.comparingInt(c -> c.myStartTime));
        for (Train train : trains) {
            addPeriodWithSum(train);
        }
    }

    /**
     * Adds new train to the system, updates information about best results
     * removes variants from bestIncomeToTime if they do not satisfy the rule any more
     *
     * @param train information about new train
     */
    void addPeriodWithSum(Train train) {
        //Will find the best result which can be continued with this current train
        EndVariant candidateForPrevious = bestIncomeToTime.floor(new EndVariant(train.myStartTime, 0));
        //candidateForPrevious must exist because there is (0, 0) as Base
        assert candidateForPrevious != null;
        EndVariant curRes = new EndVariant(train.getEndTime(), train.myAmountOfMoney + candidateForPrevious.myAmountOfMoney);
        EndVariant previousEnd = bestIncomeToTime.floor(curRes);
        assert previousEnd != null;
        if (previousEnd.myAmountOfMoney < curRes.myAmountOfMoney) {
            EndVariant nextRes = bestIncomeToTime.ceiling(curRes);
            while (nextRes != null && nextRes.myAmountOfMoney < curRes.myAmountOfMoney) {
                bestIncomeToTime.remove(nextRes);
                nextRes = bestIncomeToTime.ceiling(curRes);
            }
            bestIncomeToTime.add(curRes);
        }
    }

    public int getBestSalary() {
        EndVariant bestVariant = bestIncomeToTime.last();
        return bestVariant.myAmountOfMoney;
    }
}
