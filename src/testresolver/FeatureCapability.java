package testresolver;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.namespace.ExecutionEnvironmentNamespace;
import org.osgi.resource.Capability;
import org.osgi.resource.Resource;

public class FeatureCapability implements Capability {

	private Resource resource;
	
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	private Map<String, String> directives = new HashMap<String, String>();

	
	public FeatureCapability(Resource resource, String name) {
		this.resource= resource;
		attributes.put(getNamespace(), name);
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

	

}
