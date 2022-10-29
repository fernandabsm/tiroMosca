package com.tiromosca.network.singleplayer;

import com.tiromosca.network.game.util.GameMatchManager;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import static java.lang.String.valueOf;

@Getter
@Setter
public class SmartGame {

    private int firstDigit = 0;
    private int secondDigit = 1;
    private int thirdDigit = 9;

    private String rightFirstDigit;
    private String rightSecondDigit;
    private String rightThirdDigit;

    private String actualShotsResult;
    private String actualFliesResult;

    private String myLastAttempt;
    private String myLastShots;
    private String myLastFlies;
    private String myValue;
    private String valueOfPlayer;
    private String playerUserName;
    private String playerLastAttempt;
    private int numOfVictoriesOfPC;
    private int numOfVictoriesOfPlayer;
    private boolean isPlayerTheActualChampion;
    private boolean isPlayerTime;
    private boolean hasFly = false;
    private boolean foundNumber1 = false;
    private boolean foundNumber2 = false;
    private boolean firstPlayToFoundFirstDigit = true;
    private boolean firstPlayToFoundSecondDigit = true;
    private boolean firstPlayToFoundThirdDigit = true;
    private int numOfFlies = 0;

    public List<Integer> startSmartGame() {
        if (firstPlayToFoundFirstDigit) {
            var value = firstDigit + valueOf(secondDigit) + thirdDigit;
            var result = GameMatchManager.getResultOfAMatch(value, valueOfPlayer);
            numOfFlies = result.get(1);
            myLastAttempt = value;
            firstPlayToFoundFirstDigit = false;
            return result;
        } else if (!foundNumber1) {
            firstDigit++;
            if (firstDigit == 1) {
                secondDigit = 0;
            } else if (firstDigit == 9) {
                thirdDigit = 1;
            }
            var value = firstDigit + valueOf(secondDigit) + thirdDigit;
            var result = GameMatchManager.getResultOfAMatch(value, valueOfPlayer);
            myLastAttempt = value;
            if (numOfFlies > result.get(1)) {
                firstDigit--;
                foundNumber1 = true;
                if (firstDigit == secondDigit) {
                    secondDigit = 1;
                } else if (firstDigit == thirdDigit) {
                    thirdDigit = 0;
                }
                numOfFlies = 1;
            } else if (numOfFlies < result.get(1)) {
                foundNumber1 = true;
                if (firstDigit == secondDigit) {
                    secondDigit = 1;
                } else if (firstDigit == thirdDigit) {
                    thirdDigit = 0;
                }
                numOfFlies = 1;
            }
            return result;
        } else if (!foundNumber2) {
            if (firstPlayToFoundSecondDigit) {
                var value = firstDigit + valueOf(secondDigit) + thirdDigit;
                var result = GameMatchManager.getResultOfAMatch(value, valueOfPlayer);
                numOfFlies = result.get(1);
                myLastAttempt = value;
                secondDigit++;
                if (secondDigit == firstDigit) {
                    secondDigit++;
                }
                firstPlayToFoundSecondDigit = false;
                return result;
            }
            while (secondDigit == firstDigit) {
                secondDigit++;
            }
            if (secondDigit == thirdDigit) {
                thirdDigit = 0;
                while (thirdDigit == firstDigit || thirdDigit == secondDigit) {
                    thirdDigit++;
                }
            }
            var value = firstDigit + valueOf(secondDigit) + thirdDigit;
            var result = GameMatchManager.getResultOfAMatch(value, valueOfPlayer);
            myLastAttempt = value;
            if (numOfFlies > result.get(1)) {
                secondDigit--;
                if (secondDigit == firstDigit) {
                    secondDigit--;
                }
                thirdDigit = 0;
                foundNumber2 = true;
                numOfFlies = 2;
            } else if (numOfFlies < result.get(1)) {
                thirdDigit = 0;
                foundNumber2 = true;
                numOfFlies = 2;
            } else {
                secondDigit++;
            }
            return result;
        } else {
            while (thirdDigit == firstDigit || thirdDigit == secondDigit) {
                thirdDigit++;
            }
            var value = firstDigit + valueOf(secondDigit) + thirdDigit;
            var result = GameMatchManager.getResultOfAMatch(value, valueOfPlayer);
            if (result.get(1) != 3){
                thirdDigit++;
            }
            myLastAttempt = value;
            return result;
        }
    }

    public void chooseValue() {
        var number = getThreeDifferentDigits();

        var firstDigit = valueOf(number.get(0));
        var secondDigit = valueOf(number.get(1));
        var thirdDigit = valueOf(number.get(2));


        myValue = firstDigit + secondDigit + thirdDigit;
    }

    private List<Integer> getThreeDifferentDigits() {
        var firstDigit = (int) (Math.random() * 10);
        var secondDigit = firstDigit;
        var thirdDigit = firstDigit;

        while (firstDigit == 0) {
            firstDigit = (int) (Math.random() * 10);
        }

        while (secondDigit == firstDigit) {
            secondDigit = (int) (Math.random() * 10);
        }

        while (thirdDigit == firstDigit || thirdDigit == secondDigit) {
            thirdDigit = (int) (Math.random() * 10);
        }

        return List.of(firstDigit, secondDigit, thirdDigit);
    }
}
