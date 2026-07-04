package xy177.extradelightlegacy.common.content;

import xy177.extradelightlegacy.common.config.EDLConfig;
import xy177.extradelightlegacy.common.module.EDLModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EDLContentEntry {
    private final EDLModule module;
    private final String id;
    private final String configName;
    private final boolean defaultEnabled;
    private final List<String> dependencies;
    private boolean registered;

    EDLContentEntry(EDLModule module, String id, String configName, boolean defaultEnabled, List<String> dependencies) {
        this.module = module;
        this.id = id;
        this.configName = configName;
        this.defaultEnabled = defaultEnabled;
        this.dependencies = Collections.unmodifiableList(new ArrayList<>(dependencies));
    }

    public EDLModule getModule() {
        return module;
    }

    public String getId() {
        return id;
    }

    public String getConfigName() {
        return configName;
    }

    public boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void markRegistered() {
        registered = true;
    }

    public boolean canRegister() {
        return EDLConfig.isModuleEnabled(module)
            && EDLConfig.isEntryEnabled(this)
            && EDLContentRegistry.areDependenciesRegistered(this);
    }
}
