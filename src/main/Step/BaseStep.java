package Step;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;

import java.util.UUID;

public class BaseStep {
    // Timeout pour la valeur des éléments testés
    protected static final int WAIT_FOR_VALUE_TIMEOUT_IN_SEC = 15;

    public static void step(String name, Runnable runnable) {
        String uuid = UUID.randomUUID().toString();
        StepResult result = new StepResult().setName(name);
        Allure.getLifecycle().startStep(uuid, result);
        try {
            runnable.run();
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(Status.PASSED));
        } catch (AssertionError e) {
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(ResultsUtils.getStatus(e).orElse(Status.FAILED)));
            Allure.getLifecycle().updateTestCase(s -> s.setStatus(Status.FAILED));

        } catch (Exception e) {
            Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(ResultsUtils.getStatus(e).orElse(Status.BROKEN)));

            Allure.getLifecycle().updateTestCase(s -> s.setStatus(Status.FAILED));
        } finally {
            Allure.getLifecycle().stopStep(uuid);
        }
    }
}
