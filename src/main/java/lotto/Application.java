package lotto;

import lotto.orchestration.Orchestrator;
import lotto.view.ConsoleInput;
import lotto.view.ConsoleOutput;

public class Application {

    public static void main(String[] args) {
        Orchestrator orchestrator = new Orchestrator(
                new ConsoleInput(),
                new ConsoleOutput()
        );
        orchestrator.run();
    }
}
