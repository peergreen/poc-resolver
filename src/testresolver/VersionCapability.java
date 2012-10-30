package testresolver;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.namespace.ExecutionEnvironmentNamespace;
import org.osgi.resource.Capability;
import org.osgi.resource.Resource;

public class VersionCapability implements Capability {

	private Resource resource;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	private Map<String, String> directives = new HashMap<String, String>();

	
	
	private String name;
	
	
	public VersionCapability(Resource resource, String name) {
		this.resource= resource;
		this.name = name;
		attributes.put(ExecutionEnvironmentNamespace.CAPABILITY_VERSION_ATTRIBUTE, name);
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
		return "VersionCapability[".concat(name).concat("]");
	}

	

}
