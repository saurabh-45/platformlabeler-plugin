package org.jvnet.hudson.plugins.platformlabeler;

import hudson.Extension;
import hudson.slaves.ComputerListener;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 * Allows to configure which labels should be generate for the node when no node specific
 * configuration is used.
 */
@Extension
public class PlatformLabelerGlobalConfiguration extends GlobalConfiguration {

  private LabelConfig labelConfig;

  public PlatformLabelerGlobalConfiguration() {
    load();
    if (labelConfig == null) {
      labelConfig = new LabelConfig();
    }
  }

  @Override
  public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
    boolean result = super.configure(req, json);
    NodeLabelCache nlc = ComputerListener.all().get(NodeLabelCache.class);
    if (nlc != null) {
      nlc.onConfigurationChange();
    }
    return result;
  }

  public LabelConfig getLabelConfig() {
    return labelConfig;
  }

  public void setLabelConfig(LabelConfig labelConfig) {
    this.labelConfig = labelConfig;
    save();
  }
}