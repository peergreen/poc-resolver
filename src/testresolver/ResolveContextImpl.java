/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package testresolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.namespace.IdentityNamespace;
import org.osgi.resource.Capability;
import org.osgi.resource.Requirement;
import org.osgi.resource.Resource;
import org.osgi.resource.Wiring;
import org.osgi.service.resolver.HostedCapability;
import org.osgi.service.resolver.ResolveContext;

class ResolveContextImpl extends ResolveContext
{
	private final List<Resource> resources;
	private final Map<Resource, Wiring> m_wirings;
    private final Collection<Resource> m_mandatory;
    private final Collection<Resource> m_optional;

    public ResolveContextImpl(
    		List<Resource> resources,
        Map<Resource, Wiring> wirings,
        Collection<Resource> mandatory, Collection<Resource> optional)
    {
    	this.resources = resources;
        m_wirings = wirings;
        m_mandatory = mandatory;
        m_optional = optional;
    }

    @Override
    public Collection<Resource> getMandatoryResources()
    {
        return new ArrayList<Resource>(m_mandatory);
    }

    @Override
    public Collection<Resource> getOptionalResources()
    {
        return new ArrayList<Resource>(m_optional);
    }

    @Override
    public List<Capability> findProviders(Requirement requirement)
    {
		ArrayList<Capability> capabilities = new ArrayList<Capability>();
		for (Resource resource : resources)
			for (Capability capability : resource
					.getCapabilities(requirement.getNamespace()))
				if (matches(requirement, capability))
					capabilities.add(capability);
		/*if (capabilities.isEmpty()) {
			capabilities.add(new MissingCapability(requirement));
		}*/
		capabilities.trimToSize();
		return capabilities;
    }

    @Override
    public int insertHostedCapability(List<Capability> capabilities, HostedCapability hostedCapability)
    {
        int idx = 0;
        capabilities.add(idx, hostedCapability);
        return idx;
    }

    @Override
    public boolean isEffective(Requirement requirement)
    {
        return true;
    }

    @Override
    public Map<Resource, Wiring> getWirings()
    {
        return m_wirings;
    }
    
    
    
    
	public static boolean matches(Requirement requirement, Capability capability) {
//		if (logger.isDebugEnabled())
//			logger.debug(LOG_ENTRY, "matches", new Object[]{requirement, capability});
		boolean result = false;
		if (requirement == null && capability == null)
			result = true;
		else if (requirement == null || capability == null) 
			result = false;
		else if (!capability.getNamespace().equals(requirement.getNamespace())) 
			result = false;
		else {
			String filterStr = requirement.getDirectives().get(Constants.FILTER_DIRECTIVE);
			if (filterStr == null)
				result = true;
			else {
				try {
					if (FrameworkUtil.createFilter(filterStr).matches(capability.getAttributes()))
						result = true;
				}
				catch (InvalidSyntaxException e) {
					//logger.debug("Requirement had invalid filter string: " + requirement, e);
					result = false;
				}
			}
		}
		// TODO Check directives.
//		logger.debug(LOG_EXIT, "matches", result);
		return result;
}
	
	
	
	
	
	
}