import com.launchdarkly.sdk.*;
import com.launchdarkly.sdk.server.*;

public class Hello {

  // Set SDK_KEY to your LaunchDarkly SDK key.
  static final String SDK_KEY = "";

    // Set FEATURE_FLAG_KEY to the feature flag key you want to evaluate.
    static final String FEATURE_FLAG_KEY = "my_first_flag";

    public static void main(String... args) throws Exception {
        if (SDK_KEY.equals("")) {
            showMessage("Please edit Hello.java to set SDK_KEY to your LaunchDarkly SDK key first");
            System.exit(1);
        }

        LDConfig config = new LDConfig.Builder()
                .events(Components.sendEvents())
                .build();

        LDClient client = new LDClient(SDK_KEY, config);

        if (client.isInitialized()) {
            showMessage("SDK successfully initialized!");
        } else {
            showMessage("SDK failed to initialize");
            System.exit(1);
        }

        // Set up the evaluation context. This context should appear on your LaunchDarkly contexts
        // dashboard soon after you run the demo.
        LDUser emptyKeyUser = new LDUser("");

        // key must not null, otherwise it will be recognized as a null user, thus will always get default value(false)
        LDContext emptyKeyContext = LDContext.builder("")
                .name("I am a empty context")
                .anonymous(true)
                .build();

        boolean flagValueForEmptyKeyUser = client.boolVariation(FEATURE_FLAG_KEY, emptyKeyUser, false);

        showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValueForEmptyKeyUser + " for user: " + emptyKeyUser.getName());

        boolean flagValueForEmptyKeyContext = client.boolVariation(FEATURE_FLAG_KEY, emptyKeyContext, false);

        showMessage("Feature flag '" + FEATURE_FLAG_KEY + "' is " + flagValueForEmptyKeyContext + " for context: " + emptyKeyContext.getName());

        // Here we ensure that the SDK shuts down cleanly and has a chance to deliver analytics
        // events to LaunchDarkly before the program exits. If analytics events are not delivered,
        // the context attributes and flag usage statistics will not appear on your dashboard. In
        // a normal long-running application, the SDK would continue running and events would be
        // delivered automatically in the background.
        client.close();
    }

    private static void showMessage(String s) {
        System.out.println("*** " + s);
        System.out.println();
    }
}
