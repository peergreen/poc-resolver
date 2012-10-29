package testresolver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.resolver.Logger;
import org.apache.felix.resolver.ResolverImpl;
import org.osgi.framework.namespace.PackageNamespace;
import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;
import org.osgi.resource.Wire;
import org.osgi.resource.Wiring;
import org.osgi.service.resolver.ResolutionException;
import org.osgi.service.resolver.Resolver;

public class TestResolver {

	
	
	public static void main(String[] args) throws ResolutionException {
        Resolver resolver = new ResolverImpl(new Logger(Logger.LOG_DEBUG));

        Map<Resource, Wiring> wirings = new HashMap<Resource, Wiring>();

        
		Feature jdbcFeature = new Feature("jdbc");
		FeatureCapability jdbcServiceCapability = new FeatureCapability(jdbcFeature, "jdbc-service");
		jdbcFeature.addCapability(jdbcServiceCapability);
		jdbcFeature.addCapability(new VersionCapability(jdbcFeature, "3.0"));
		
		
		Feature tomcatFeature = new Feature("tomcat");
		Capability httpServiceCapability = new FeatureCapability(tomcatFeature, "http-service");
		Capability servletServiceCapability = new FeatureCapability(tomcatFeature, "servlet-service");
		tomcatFeature.addCapability(httpServiceCapability); 
		tomcatFeature.addCapability(servletServiceCapability);
	
		Requirement jdbcFeatureRequirement = new FeatureRequirement(tomcatFeature, "jdbc-service");
		tomcatFeature.addRequirement(jdbcFeatureRequirement);
		

		Feature jettyFeature = new Feature("jetty");
		Requirement jdbcFeatureRequirement2 = new FeatureRequirement(jettyFeature, "jdbc-service");
		jettyFeature.addRequirement(jdbcFeatureRequirement2);
		jettyFeature.addRequirement(new VersionRequirement(jettyFeature, "2.0"));
		//jettyFeature.addRequirement(new FeatureRequirement(jettyFeature, "security-service"));

		
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(jdbcFeature);
		resources.add(tomcatFeature);
		resources.add(jettyFeature);
		
		List<Resource> mandatoryResources = new ArrayList<Resource>();
		mandatoryResources.add(jdbcFeature);
		mandatoryResources.add(tomcatFeature);
		mandatoryResources.add(jettyFeature);
		
		List<Resource> optionalResources = new ArrayList<Resource>();
		//optionalResources.add(jettyFeature);

		
		 ResolveContextImpl rci = new ResolveContextImpl(resources, wirings, mandatoryResources, optionalResources);
	        Map<Resource, List<Wire>> wireMap = resolver.resolve(rci);
	        System.out.println("RESULT " + wireMap);
	        
	        List<Wire> requirements = wireMap.get(tomcatFeature);
	        if (requirements.size() > 0 ) {
	        Wire wire = requirements.get(0); 
	        
	        
	        System.out.println("Wire for Tomcat. For requirement " + wire.getRequirement() + " it is provided by " + wire.getProvider() + "" );
	        }

				
		
	}
}
