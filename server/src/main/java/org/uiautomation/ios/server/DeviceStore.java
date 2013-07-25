package org.uiautomation.ios.server;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public class DeviceStore {

  private static final Logger log = Logger.getLogger(DeviceStore.class.getName());
  private final List<RealDevice> reals = new CopyOnWriteArrayList<RealDevice>();
  private final List<SimulatorDevice> sims = new CopyOnWriteArrayList<SimulatorDevice>();

  /**
   * @return immutable copy of the currently available devices.
   */
  public List<Device> getDevices() {
    List<Device> all = new ArrayList<Device>();
    all.addAll(reals);
    all.addAll(sims);
    return ImmutableList.copyOf(all);
  }

  public List<RealDevice> getRealDevices() {
    return reals;
  }

  public List<SimulatorDevice> getSimulatorDevices() {
    return sims;
  }

  public void add(RealDevice realDevice) {
    reals.add(realDevice);
  }

  public void add(SimulatorDevice simulatorDevice) {
    sims.add(simulatorDevice);
  }


  public void remove(String uuid) {
    for (RealDevice d : reals) {
      if (d.getUuid().equals(uuid)) {
        reals.remove(d);
      }
    }
  }
}
