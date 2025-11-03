package lotto;

import lotto.orchestration.Orchestrator;

public class Application {

    public static void main(String[] args) {
        Orchestrator.create().run();
    }
}
