package logicgame.service;

import com.usefulsoft.terver.service.Randomizer;
import logicgame.model.Hook;
import logicgame.model.SubjectGroup;
import logicgame.model.SubjectParameters;
import logicgame.model.generalquest.GeneralQuestAnswer;
import logicgame.model.generalquest.GeneralQuestParams;
import logicgame.model.generalquest.GeneralQuestScenario;
import logicgame.model.miniquest.MiniQuest;
import logicgame.service.miniquest.MiniQuestGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class GameService {
    private final SubjectsParametersGenerator subjectsParametersGenerator = new SubjectsParametersGenerator();
    private final Demo1GeneralQuestService demo1GeneralQuestService = new Demo1GeneralQuestService();
    private final Demo2FilterQuestService filterQuestService = new Demo2FilterQuestService();
    private final MiniQuestGenerator miniQuestGenerator = new MiniQuestGenerator();
    private final Complexity complexity = new Complexity();
    private final ComplexityValidator complexityValidator = new ComplexityValidator();
    private static final Randomizer randomizer = new Randomizer();
    private final String bridge = "бридж";
    private final String preference = "преферанс";
    private Set<Integer> usedHooksKeys = new HashSet<>();
    private final Scanner sc = new Scanner(System.in);

    public void start(int subjectsNum) {
        int groups = 2;

        SubjectParameters subjectsParams = subjectsParametersGenerator.generate(subjectsNum);

        // Generate "Filter quest" scenarios.
        GeneralQuestParams filterQuestParams = filterQuestService.generateScenario(subjectsParams);

        // Generate "Main quests" scenarios.
        Map<Integer, GeneralQuestParams> generalQuestParamsByGroupId = new HashMap<>(groups);
        Map<Integer, List<GeneralQuestScenario>> scenariosByGroupId = new HashMap<>(groups);
        for (int i = 0; i < groups; i++) {
            GeneralQuestParams generalQuestParams = demo1GeneralQuestService.generateScenario(subjectsParams, i);
            List<GeneralQuestScenario> scenarios = generalQuestParams.getGeneralQuestScenarios();
            generalQuestParamsByGroupId.put(i, generalQuestParams);
            scenariosByGroupId.put(i, scenarios);
        }

        // Valid scenarios on errors.
        complexityValidator.isValid(subjectsParams, complexity, getGeneralQuestScenarios(groups, subjectsParams, filterQuestParams), miniQuestGenerator);

        // Print starting information.
        System.out.println("\nПримечание: нации не связаны с предрассудками и реальными либо вымышленными особенностями. Всё генерится исключительно рандомно и не имеет отношения к действительности. Все совпадения случайны.");
        System.out.println("\nИмеется несколько возможных подозреваемых: " + getSubjectNames(subjectsParams, SubjectGroup.NATIONALITY) + ".");
        System.out.println("На месте преступления нашли окурок от " + getSoughtSmokeName(subjectsParams) + ". Надо найти того, кто его оставил.");
        System.out.println();
        System.out.println("О подозреваемых известно, что в тот вечер все они пили разные напитки: " +
                getSubjectNames(subjectsParams, SubjectGroup.DRINKS) +
                ". И курили разные марки сигарет: " +
                getSubjectNames(subjectsParams, SubjectGroup.SMOKES) +
                ".");

        System.out.println("Те, кто играли в бридж курили " +
                getSubjectNames(subjectsParams, SubjectGroup.SMOKES, 0, 2) +
                ". А те, кто играли в преферанс - " +
                getSubjectNames(subjectsParams, SubjectGroup.SMOKES, 3, 5) +
                ".");
        System.out.println("Удалось выяснить, что " +
                getSubjectNameRaw(SubjectGroup.NATIONALITY, 1, subjectsParams) +
                " играл в " + bridge +
                ". А " + getSubjectNameRaw(SubjectGroup.NATIONALITY, 4, subjectsParams) +
                " и " + getSubjectNameRaw(SubjectGroup.NATIONALITY, 5, subjectsParams) + " играли за одним столом.");

        // Create hooks.
        Map<Integer, Hook> openHooksById = new HashMap<>();

        for (int i = 0; i < filterQuestParams.getGeneralQuestScenarios().size(); i++) {
            if (i <= 1) {
                GeneralQuestScenario generalQuestScenario = filterQuestParams.getGeneralQuestScenarios().get(i);
                Hook hook = createHook(generalQuestScenario);
                openHooksById.put(hook.key, hook);
            }
        }

        for (int ig = 0; ig < groups; ig++) {
            List<GeneralQuestScenario> scenarios = scenariosByGroupId.get(ig);
            GeneralQuestScenario generalQuestScenario = scenarios.get(0);
            Hook hook = createHook(generalQuestScenario);
            openHooksById.put(hook.key, hook);
        }

        // Add additional hooks.
        Hook infoHook = createInfoHook(
                "Выяснить что пили играющие за столом в бридж",
                "У стюарда вам удалось узнать, что за столом в бридж пили: " + getSubjectNames(subjectsParams, SubjectGroup.DRINKS, 0, 2));
        openHooksById.put(infoHook.key, infoHook);
        infoHook = createInfoHook(
                "Выяснить что пили играющие за столом в преферанс",
                "У крупье вам удалось узнать, что за столом в преферанс пили: " + getSubjectNames(subjectsParams, SubjectGroup.DRINKS, 3, 5));
        openHooksById.put(infoHook.key, infoHook);

        // Print hooks.
        System.out.println();
        System.out.println(printOpenHooks(openHooksById));

        // Game process.
        int counter = 1;
        int complexityCounter = 0;
        while (true) {
            System.out.println("\nВведите номер зацепки: ");
            String hookIdString = sc.next();
            if (hookIdString == null || hookIdString.trim().isEmpty()) {
                continue;
            }

            if (hookIdString.equalsIgnoreCase("end")) {
                break;
            }

            int hookId;
            try {
                hookId = Integer.valueOf(hookIdString);
            } catch (NumberFormatException e) {
                continue;
            }

            Hook hook = openHooksById.remove(hookId);
            if (hook == null) {
                System.out.println("Зацепка не найдена.");
                continue;
            }

            System.out.println("\nОпрос #" + counter + ":");
            String miniQuestAnswer = "N/A";

            if (hook.type == Hook.Type.INFO) {
                System.out.println(hook.info);
            } else if (hook.type == Hook.Type.QUEST) {
                // Generate mini-quests.
                MiniQuest miniQuest = createMiniQuest(subjectsParams, hook.generalQuestScenario, complexityCounter);
                complexityCounter++;

                // Print mini-quest
                for (int i = 0; i < miniQuest.statements.size(); i++) {
                    System.out.println("Опрашиваемый " + (i + 1) + ": \"" + miniQuest.statements.get(i) + "\".");
                }
                System.out.println(miniQuest.askQuestion);
                miniQuestAnswer = miniQuest.answer;

                // Discover 1 hook.
                if (hook.generalQuestScenario.getFollowingScenrio() != null) {
                    Hook nextHook = createHook(hook.generalQuestScenario.getFollowingScenrio()); //todo false
                    openHooksById.put(nextHook.key, nextHook);
                    System.out.println();
                    System.out.println("Появилась новая зацепка! Помощник капитана сообщает, что кто-то видел кое-что интересное." +
                            " Теперь вы можете " + nextHook.ask);
                }
            } else {
                throw new IllegalStateException("Unknown hook.type=" + hook.type);
            }

            System.out.println();
            System.out.println(printOpenHooks(openHooksById));

            sc.nextLine();
            sc.nextLine();

            System.out.println("Answer is: " + miniQuestAnswer);

            counter++;
        }

        System.out.println("\nОтветы:");
        for (int i = 0; i < groups; i++) {
            GeneralQuestParams generalQuestParams = generalQuestParamsByGroupId.get(i);
            printAnswersLine(generalQuestParams.getGeneralQuestAnswers());
        }

        System.out.println("\nГруппы:");
        for (int i = 0; i < groups; i++) {
            String subjectNames = getSubjectNames(subjectsParams, SubjectGroup.NATIONALITY, 0 + (i * 3), 2 + (i * 3));
            System.out.println(subjectNames);
        }
    }

    private List<GeneralQuestScenario> getGeneralQuestScenarios(int groups, SubjectParameters subjectsParams, GeneralQuestParams filterQuestParams) {
        List<GeneralQuestScenario> scenarios = new ArrayList<>(filterQuestParams.getGeneralQuestScenarios());
        for (int i = 0; i < groups; i++) {
            GeneralQuestParams generalQuestParams = demo1GeneralQuestService.generateScenario(subjectsParams, i);
            scenarios.addAll(generalQuestParams.getGeneralQuestScenarios());
        }
        return scenarios;
    }

    private String printOpenHooks(Map<Integer, Hook> openHooksById) {
        StringBuilder sb  =new StringBuilder();
        sb.append("Зацепки:");
        for (Hook hook : openHooksById.values()) {
            sb.append("\n - ");
            sb.append(hook.ask);
        }
        return sb.toString();
    }

    private Hook createHook(GeneralQuestScenario generalQuestScenario) {
        int key = generateHookKey();
        String subjectName = getSubjectName(generalQuestScenario.subjectGroup, generalQuestScenario.subjectIdx);
        String ask;
        if (generalQuestScenario.questType == GeneralQuestScenario.QuestType.FILTER) {
            ask = "Расспросить в игорном зале во что играл " + subjectName + " [" + key + "]";
        } else if (generalQuestScenario.questType == GeneralQuestScenario.QuestType.GLOBAL) {
            if (generalQuestScenario.subjectGroup.isAlive()) {
                ask = String.format("Собрать сведения %s о том, что мог %s '%s' [%d]",
                        getRandomPlace(), ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup), subjectName, key);
            } else {
                ask = String.format("Собрать сведения %s о том, что мог %s тот, кто %s '%s' [%d]",
                        getRandomPlace(), ParamsDictionary.getSubjectVerb(generalQuestScenario.variantGroup),
                        ParamsDictionary.getSubjectVerb2(generalQuestScenario.subjectGroup),
                        subjectName, key);
            }
        } else {
            throw new IllegalStateException("Unknown questType, generalQuestScenario=" + generalQuestScenario);
        }

        return new Hook(key, Hook.Type.QUEST, generalQuestScenario, ask);
    }

    private Hook createInfoHook(String ask, String info) {
        int key = generateHookKey();
        return new Hook(key, Hook.Type.INFO, ask + " [" + key + "]", info);
    }

    private int generateHookKey() {
        int key;
        do {
            key = randomizer.generateFromRange(100, 999);
        } while (usedHooksKeys.contains(key));
        usedHooksKeys.add(key);
        return key;
    }

    private String getRandomPlace() {
        List<String> places = Arrays.asList(
                "на верхней палубе",
                "в кают-компании",
                "в баре",
                "на смотровой площадке",
                "в понорамной зоне отдыха",
                "в танцевальном зале",
                "в библиотеке",
                "на корте для игры в сквош",
                "у пассажиров",
                "у персонала",
                "у команды"
        );

        return places.get(randomizer.generateFromRange(0, places.size() - 1));
    }

    private void printAnswersLine(List<GeneralQuestAnswer> generalQuestAnswers) {
        for (int i = 0; i < generalQuestAnswers.size(); i++) {
            String answer = getAnswer(generalQuestAnswers.get(i).getAnswerBySubjectIdxs());
            System.out.println(answer);
        }
    }

    private MiniQuest createMiniQuest(SubjectParameters subjectsParams, GeneralQuestScenario scenario, int complexityCounter) {
        complexityCounter = complexity.normalizeComplexity(complexityCounter);
        MiniQuest miniQuest = miniQuestGenerator.generateStatements(
                scenario, subjectsParams,
                complexity.getStatementsNum(complexityCounter),
                complexity.getRepeatsLimit(complexityCounter),
                complexity.getContradictionsAllow(complexityCounter));
        miniQuest.setAnswer(getSubjectName(scenario.variantGroup, scenario.rightVariantIdx));
        return miniQuest;
    }

    private String getSoughtSmokeName(SubjectParameters subjectsParams) {
        Integer paramIdx = subjectsParams.getParamsIdxs(SubjectGroup.SMOKES).get(demo1GeneralQuestService.getMainClueIdx());
        return ParamsDictionary.getSubjectParams(SubjectGroup.SMOKES)[paramIdx];
    }

    private String getSubjectNames(SubjectParameters subjectsParams, SubjectGroup subjectGroup) {
        return getSubjectNames(subjectsParams, subjectGroup, 0, subjectsParams.getParamsIdxs(subjectGroup).size() - 1);
    }

    private String getSubjectNames(SubjectParameters subjectsParams, SubjectGroup subjectGroup, int startIndex, int lastIndex) {
        List<Integer> paramsIdxs = new ArrayList<>(subjectsParams.getParamsIdxs(subjectGroup).subList(startIndex, lastIndex + 1));
        randomizer.shuffle(paramsIdxs);
        return paramsIdxs
                .stream()
                .map(i -> ParamsDictionary.getSubjectParams(subjectGroup)[i])
                .collect(Collectors.joining(", "));
    }

    private String getSubjectNameRaw(SubjectGroup subjectGroup, int paramIdx, SubjectParameters subjectsParams) {
        Integer idx = subjectsParams.getSubjectParamsByGroup().get(subjectGroup).get(paramIdx);
        return ParamsDictionary.getSubjectParams(subjectGroup)[idx];
    }

    private String getSubjectName(SubjectGroup subjectGroup, int paramIdx) {
        return ParamsDictionary.getSubjectParams(subjectGroup)[paramIdx];
    }

    private String getAnswer(int[] paramsIdxs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paramsIdxs.length; i++) {
            sb.append(ParamsDictionary.getSubjectParams(SubjectGroup.by(i))[paramsIdxs[i]]);
            if (i <= paramsIdxs.length - 2) {
                sb.append(" = ");
            }
        }

        return sb.toString();
    }

}
