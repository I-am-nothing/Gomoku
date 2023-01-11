package com.example.gomoku.service.data;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Gomoku {
    private final List<String> step = new ArrayList<>();

    public Color getCurrentColor() {
        if (step.size() % 2 == 0) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public Color getLastColor() {
        if (step.size() % 2 == 1) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }

    public Status addChess(int x, int y) {
        if (!step.contains(x + "-" + y) && (step.size() == 0 || !step.get(step.size() - 1).equals("end"))) {
            step.add(x + "-" + y);
            return checkStatus(x, y);
        }
        return Status.INVALID;
    }

    public void surrender() {
        step.add("end");
    }

    public Status checkStatus(int x, int y) {
        List<String> playerStep = IntStream.range(0, step.size())
                .filter(i -> i % 2 == (step.size() - 1) % 2)
                .mapToObj(step::get)
                .toList();
        int max = maxLineCount(playerStep, x, y);
        if (max < 5) {
            return Status.STILL;
        } else if (max > 5) {
            step.add("end");
            return Status.LOSS;
        } else {
            step.add("end");
            return Status.WIN;
        }
    }
    private int maxLineCount(List<String> playerStep, int x, int y) {
        return Arrays.stream(new int[]{
                maxLineCount(playerStep, x + 1, y, x, y),
                maxLineCount(playerStep, x - 1, y, x, y),
                maxLineCount(playerStep, x, y + 1, x, y),
                maxLineCount(playerStep, x, y - 1, x, y),
                maxLineCount(playerStep, x + 1, y + 1, x, y),
                maxLineCount(playerStep, x - 1, y + 1, x, y),
                maxLineCount(playerStep, x - 1, y - 1, x, y),
                maxLineCount(playerStep, x + 1, y - 1, x, y)
        }).max().getAsInt();
    }

    private int maxLineCount(List<String> playerStep, int newX, int newY, int x, int y) {
        if (newX < 0 || newX > 18 || newY < 0 || newY > 18 || !playerStep.contains(x + "-" + y)) {
            return 0;
        }
        return 1 + maxLineCount(playerStep, newX * 2 - x, newY * 2 - y, newX, newY);
    }
}
