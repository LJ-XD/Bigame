package com.luckj.games.number;

import com.luckj.games.number.bo.NumGameBo;

import java.util.Random;
import java.util.Scanner;

public class NumGame {

    private static int goalNum;

    public static void main(String[] args) {
        goalNum = new Random().nextInt(100);
        Scanner scanner = new Scanner(System.in);
        NumGameBo nubGameBo = new NumGameBo();
        nubGameBo.setGoalNum(goalNum);
        while (true) {
            playNumGame(nubGameBo);
        }
    }

    private static void playNumGame(NumGameBo bo) {
        while (true) {
            int flag = Integer.compare(bo.getGuessNum(), bo.getGoalNum());
            if (flag > 0) {
                return;
            } else if (flag < 0) {
                return;
            } else {
                System.out.println();
            }
        }
    }

    private static void playNumGameWithBot(int goalNum, int guessNum) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int flag = Integer.compare(scanner.nextInt(), goalNum);
            if (flag > 0) {
                return;
            } else if (flag < 0) {
                return;
            } else {
                System.out.println();
            }
        }
    }
}
