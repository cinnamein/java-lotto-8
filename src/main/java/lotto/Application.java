package lotto;

import lotto.orchestration.Orchestrator;
import lotto.view.ConsoleInput;

public class Application {

    public static void main(String[] args) {
        Orchestrator orchestrator = new Orchestrator(
                new ConsoleInput()
        );
        orchestrator.run();
    }
}
