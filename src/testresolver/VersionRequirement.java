package testresolver;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.namespace.BundleNamespace;
import org.osgi.framework.namespace.ExecutionEnvironmentNamespace;
import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;

public class VersionRequirement implements Requirement {

	private Resource resource;
	private Map<String, String> directives = new HashMap<String, String>();
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	private final String name;
	
	public VersionRequirement(Resource resource, String name) {
		this.name = name;
		this.resource= resource;
		attributes.put(getNamespace(), name);
		directives.put(
				ExecutionEnvironmentNamespace.REQUIREMENT_FILTER_DIRECTIVE,
                "(" + ExecutionEnvironmentNamespace.CAPABILITY_VERSION_ATTRIBUTE + ">=" + name + ")");

		
		
	}

	@Override
	public String getNamespace() {
		return ExecutionEnvironmentNamespace.EXECUTION_ENVIRONMENT_NAMESPACE;
	}

	@Override
	public Map<String, String> getDirectives() {
		return directives;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Resource getResource() {
		return resource;
	}
	
	

	public String toString() {
		return "VersionRequirement[".concat(name).concat("]");
	}
}
