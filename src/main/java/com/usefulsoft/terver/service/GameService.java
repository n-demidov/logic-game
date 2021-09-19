package com.usefulsoft.terver.service;

import com.usefulsoft.terver.Consts;
import com.usefulsoft.terver.model.FiguresData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameService {
    private static final Randomizer randomizer = new Randomizer();

    public void generate(int figuresGroupsNum) {
        Map<Integer, FiguresData> numByFigureIdx = generateFigures(figuresGroupsNum);
        int allFiguresNum = numByFigureIdx.values().stream().map(v -> v.num).reduce(0, Integer::sum);

        int plusFiguresNum = getPlusFiguresNum(numByFigureIdx.size());
        Set<Integer> plusFiguresIdxs = generatePlusFigures(plusFiguresNum, figuresGroupsNum);
        int plusFiguresCount = 0;

        TempFiguresData lastPlusPair = null;
        for (int i = 0; i < figuresGroupsNum; i++) {
            TempFiguresData pair;
            if (plusFiguresIdxs.contains(i)) {
                pair = generateRandomOffersAndEvents(numByFigureIdx, i, plusFiguresCount++, allFiguresNum, lastPlusPair, true);
                lastPlusPair = pair;
            } else {
                pair = generateRandomOffersAndEvents(numByFigureIdx, i, plusFiguresCount++, allFiguresNum, lastPlusPair, false);
            }
            FiguresData figuresData = numByFigureIdx.get(i);
            figuresData.price = pair.price;
            figuresData.event = pair.event;
            figuresData.statEv = pair.ev;
            figuresData.statDisp = pair.disp;
        }

        // Print
        StringBuilder sb  = new StringBuilder();
        for (Map.Entry<Integer, FiguresData> entry : numByFigureIdx.entrySet()) {
            sb.append("\n");
            sb.append(entry.getKey());
            sb.append(") ");
            sb.append(entry.getValue());
        }
        System.out.println(sb);
        System.out.println("all=" + allFiguresNum);
    }

    private Map<Integer, FiguresData> generateFigures(int figuresGroupsNum) {
        Map<Integer, FiguresData> result = new HashMap<>();
        for (int i = 0; i < figuresGroupsNum; i++) {
            int figuresInGroup = randomizer.generateFromRange(1, Consts.MAX_FIGURES_IN_GROUP);
            result.put(i, new FiguresData(figuresInGroup));
        }
        return result;
    }

    private int getPlusFiguresNum(int allFiguresNum) {
        int plusFiguresNum = (int) Math.ceil((double) allFiguresNum / Consts.PLUS_FIGURES_GROUPS_PERCENT);
        if (plusFiguresNum < 1) {
            plusFiguresNum = 1;
        }
        return plusFiguresNum;
    }

    private Set<Integer> generatePlusFigures(int plusFiguresNum, int figuresGroupsNum) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < plusFiguresNum; i++) {
            int randomGroupIdx;
            do {
                randomGroupIdx = randomizer.generateFromRange(0, figuresGroupsNum - 1);
            } while (result.contains(randomGroupIdx));
            result.add(randomGroupIdx);
        }
        return result;
    }

    private TempFiguresData generateRandomOffersAndEvents(Map<Integer, FiguresData> numByFigureIdx,
                                                          int figureIdx, int plusFiguresCount,
                                                          int allFiguresNum, TempFiguresData prev, boolean plus) {
        plusFiguresCount += 1 + randomizer.generateFromRange(0, 1);

        int price;
        int event;
        double mX;
        double disp;
        while (true) {
            if (prev == null) {
                price = randomizer.generateFromRange(1, plusFiguresCount + 1);
            } else {
                price = randomizer.generateFromRange(1, prev.price + 2);
            }
            event = randomizer.generateFromRange(1, price * 300);

            mX = (event - price) * (double) numByFigureIdx.get(figureIdx).num / allFiguresNum - price * ((double) allFiguresNum - numByFigureIdx.get(figureIdx).num) / allFiguresNum;
            double mX2 = Math.pow(event - price, 2) * (double) numByFigureIdx.get(figureIdx).num / allFiguresNum + Math.pow(price, 2) * ((double) allFiguresNum - numByFigureIdx.get(figureIdx).num) / allFiguresNum;
            disp = mX2 - Math.pow(mX, 2);

            if (!plus && mX <= 0) {
                break;
            }

            if (plus && mX > 0) {
                if (prev == null) {
                    if (Double.compare(mX, 0.5) < 0) {
                        break;
                    } else {
                        continue;
                    }
                }

                /* 1) МО > предыдущего, но не более, чем в 2 раза;
                   2) Disp > предыдущего; */
                if (Double.compare(mX, prev.ev) > 0 && Double.compare(mX, prev.ev * 2) < 0 && Double.compare(disp, prev.disp) > 0) {
                    break;
                }
            }
        }

        return new TempFiguresData(price, event, mX, disp);
    }

    private static class TempFiguresData {
        int price;
        int event;
        double ev;
        double disp;

        public TempFiguresData(int price, int event, double ev, double disp) {
            this.price = price;
            this.event = event;
            this.ev = ev;
            this.disp = disp;
        }
    }

}
