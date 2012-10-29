package testresolver;

import java.util.ArrayList;
import java.util.List;

import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;

public class Feature implements Resource {

	private final List<Capability> capabilities;
	private final List<Requirement> requirements;
	private final String name;
	
	public Feature(String name) {
		this.name = name;
		capabilities = new ArrayList<Capability>();
		capabilities.add(0, new IdentityCapability(this, name));
		requirements = new ArrayList<Requirement>();
	}

	public void addCapability(Capability cap) {
		capabilities.add(cap);
	}

	public List<Capability> getCapabilities(String namespace) {
		List<Capability> result = capabilities;
		if (namespace != null) {
			result = new ArrayList<Capability>();
			for (Capability cap : capabilities) {
				if (cap.getNamespace().equals(namespace)) {
					result.add(cap);
				}
			}
		}
		return result;
	}

	public void addRequirement(Requirement req) {
		requirements.add(req);
	}

	public List<Requirement> getRequirements(String namespace) {
		List<Requirement> result = requirements;
		if (namespace != null) {
			result = new ArrayList<Requirement>();
			for (Requirement req : requirements) {
				if (req.getNamespace().equals(namespace)) {
					result.add(req);
				}
			}
		}
		return result;
	}
	
	public String toString() {
		return "Feature[".concat(name).concat("]");
	}

}
